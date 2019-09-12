package com.benoitthore.enamel.android.extract

import com.benoitthore.enamel.core.math.*


fun colorFromGray(gray: Int): Int {
    val gray = gray.constrain(0, 255).i
    return -0x1000000 or (gray shl 16) or (gray shl 8) or gray
}

val Number.color: Int get() = (0xFF_00_00_00.i or i)


fun argb(alpha: Float, red: Float, green: Float, blue: Float): Int {
    return (alpha * 255.0f + 0.5f).toInt() shl 24 or
            ((red * 255.0f + 0.5f).toInt() shl 16) or
            ((green * 255.0f + 0.5f).toInt() shl 8) or
            (blue * 255.0f + 0.5f).toInt()
}

val Int.alpha get() = this shr 24 and 0xFF
val Int.red get() = this shr 16 and 0xFF
val Int.green get() = this shr 8 and 0xFF
val Int.blue get() = this and 0xFF

fun Int.withAlpha(percentage: Number): Int {
    val alpha = (0xFF * percentage.toDouble()).i

    return (alpha shl 24) or (red shl 16) or (green shl 8) or blue
}

fun randomColor() = colorHSL(Math.random().toFloat())

fun colorHSL(h: Number) = HSLToColor(floatArrayOf(h.f * 360, 1f, .5f)) or 0xFF000000.toInt()

private fun HSLToColor(hsl: FloatArray): Int {
    val h = hsl[0]
    val s = hsl[1]
    val l = hsl[2]

    val c = (1f - Math.abs(2 * l - 1f)) * s
    val m = l - 0.5f * c
    val x = c * (1f - Math.abs(h / 60f % 2f - 1f))

    val hueSegment = h.toInt() / 60

    var r = 0
    var g = 0
    var b = 0

    when (hueSegment) {
        0 -> {
            r = Math.round(255 * (c + m))
            g = Math.round(255 * (x + m))
            b = Math.round(255 * m)
        }
        1 -> {
            r = Math.round(255 * (x + m))
            g = Math.round(255 * (c + m))
            b = Math.round(255 * m)
        }
        2 -> {
            r = Math.round(255 * m)
            g = Math.round(255 * (c + m))
            b = Math.round(255 * (x + m))
        }
        3 -> {
            r = Math.round(255 * m)
            g = Math.round(255 * (x + m))
            b = Math.round(255 * (c + m))
        }
        4 -> {
            r = Math.round(255 * (x + m))
            g = Math.round(255 * m)
            b = Math.round(255 * (c + m))
        }
        5, 6 -> {
            r = Math.round(255 * (c + m))
            g = Math.round(255 * m)
            b = Math.round(255 * (x + m))
        }
    }

    r = r.constrain(0, 255).i
    g = g.constrain(0, 255).i
    b = b.constrain(0, 255).i

    return rgb(r, g, b)
}

private fun rgb(
    red: Int,
    green: Int,
    blue: Int
): Int {
    return -0x1000000 or (red shl 16) or (green shl 8) or blue
}

fun Number.argbEvaluate(startValue: Int, endValue: Int): Int = ARGB_evaluate(toFloat(), startValue, endValue)

fun ARGB_evaluate(fraction: Float, startValue: Int, endValue: Int): Int {
    val startInt = startValue
    val startA = (startInt shr 24 and 0xff) / 255.0f
    var startR = (startInt shr 16 and 0xff) / 255.0f
    var startG = (startInt shr 8 and 0xff) / 255.0f
    var startB = (startInt and 0xff) / 255.0f

    val endInt = endValue
    val endA = (endInt shr 24 and 0xff) / 255.0f
    var endR = (endInt shr 16 and 0xff) / 255.0f
    var endG = (endInt shr 8 and 0xff) / 255.0f
    var endB = (endInt and 0xff) / 255.0f

    // convert from sRGB to linear
    startR = Math.pow(startR.toDouble(), 2.2).toFloat()
    startG = Math.pow(startG.toDouble(), 2.2).toFloat()
    startB = Math.pow(startB.toDouble(), 2.2).toFloat()

    endR = Math.pow(endR.toDouble(), 2.2).toFloat()
    endG = Math.pow(endG.toDouble(), 2.2).toFloat()
    endB = Math.pow(endB.toDouble(), 2.2).toFloat()

    // compute the interpolated color in linear space
    var a = startA + fraction * (endA - startA)
    var r = startR + fraction * (endR - startR)
    var g = startG + fraction * (endG - startG)
    var b = startB + fraction * (endB - startB)

    // convert back to sRGB in the [0..255] range
    a *= 255.0f
    r = Math.pow(r.toDouble(), 1.0 / 2.2).toFloat() * 255.0f
    g = Math.pow(g.toDouble(), 1.0 / 2.2).toFloat() * 255.0f
    b = Math.pow(b.toDouble(), 1.0 / 2.2).toFloat() * 255.0f

    return Math.round(a) shl 24 or (Math.round(r) shl 16) or (Math.round(g) shl 8) or Math.round(b)
}

class ColorScale(val colors: List<Int>) {

    operator fun get(fraction: Number): Int {
        val fraction = fraction.constrain(0, 1)

        val max = colors.size - 1

        val from = (max * fraction).toInt()
        val to = (from + 1).constrain(0, max).toInt()

        val subFraction = max * fraction - from
        return ARGB_evaluate(subFraction, colors[from], colors[to])
    }

}