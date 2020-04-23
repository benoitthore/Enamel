package com.benoitthore.enamel.layout.android.extract

import android.graphics.*
import com.benoitthore.enamel.geometry.figures.ECircle
import com.benoitthore.enamel.geometry.figures.ELine
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.primitives.EPoint

fun ELine.toLinearGradient(
    colors: List<Int>,
    positions: List<Float>? = null,
    shaderMode: Shader.TileMode = Shader.TileMode.CLAMP
) =
    LinearGradient(
        start.x,
        start.y,
        end.x,
        end.y,
        colors.toIntArray(),
        positions?.toFloatArray(),
        shaderMode
    )

fun ECircle.toRadialGradient(
    colors: List<Int>,
    stops: List<Float>? = null,
    shaderMode: Shader.TileMode = Shader.TileMode.CLAMP
) = RadialGradient(
    center.x,
    center.y,
    radius,
    colors.toIntArray(),
    stops?.toFloatArray(),
    shaderMode
)

////
////
////

fun Rect.set(rect: ERect) = apply {
    left = rect.left.toInt()
    top = rect.top.toInt()
    right = rect.right.toInt()
    bottom = rect.bottom.toInt()
}

fun RectF.set(rect: ERect) = apply {
    left = rect.left
    top = rect.top
    right = rect.right
    bottom = rect.bottom
}

private val targetRectF = RectF()

////
////
////
fun Canvas.drawCircles(circles: List<ECircle>, paint: Paint) =
    circles.forEach { draw(it, paint) }

fun Canvas.draw(circle: ECircle, paint: Paint) {
    drawCircle(circle.x, circle.y, circle.radius, paint)
}

fun Canvas.draw(line: ELine, paint: Paint) = draw(line.start, line.end, paint)

fun Canvas.draw(start: EPoint, end: EPoint, paint: Paint) {
    drawLine(start.x, start.y, end.x, end.y, paint)
}

fun Canvas.drawRects(rects: List<ERect>, paint: Paint) = rects.forEach { draw(it, paint) }
fun Canvas.draw(rect: ERect, paint: Paint) {
    synchronized(targetRectF) {
        drawRect(targetRectF.set(rect), paint)
    }
}

fun Canvas.drawRoundRects(rects: List<ERect>, rx: Number, ry: Number, paint: Paint) =
    rects.forEach { draw(it, rx, ry, paint) }

fun Canvas.draw(rect: ERect, rx: Number, ry: Number, paint: Paint) {
    synchronized(targetRectF) {
        drawRoundRect(
            targetRectF.set(rect),
            rx.toFloat(),
            ry.toFloat(),
            paint
        )
    }
}