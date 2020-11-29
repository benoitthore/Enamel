package com.benoitthore.enamel.processing

import com.benoitthore.enamel.core.ColorScale
import com.benoitthore.enamel.core.blue
import com.benoitthore.enamel.core.green
import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.core.red
import com.benoitthore.enamel.geometry.builders.*
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.primitives.angle.EAngle
import com.benoitthore.enamel.geometry.primitives.angle.degrees
import com.benoitthore.enamel.geometry.primitives.point.point
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.size.size
import processing.core.PApplet

fun PApplet.radialGradient(frame: ECircle, colors: List<Int>, resolution: Int = 1) {
    pushPop {
        strokeWeight(1f)
        val scale = ColorScale(colors)
        val nbCircles = frame.width.toInt() / resolution
        val circle = frame._copy()
        (nbCircles downTo 0).forEach { i ->
            val progress = 1f - (i / nbCircles.f)
            val color = scale[progress]
            fill(color.red.f, color.green.f, color.blue.f)
            stroke(color.red.f, color.green.f, color.blue.f)
            circle.radius = i.toFloat()
            draw(circle)
        }
    }
}

fun PApplet.linearGradient(
    frame: ERect,
    colors: List<Int>,
    resolution: Int = 1,
    rotation: EAngle = 0.degrees()
) {
    pushPop {
        rotate(rotation, frame.getCenter())
        translate(frame.origin)

        strokeWeight(1f)
        val nbLines = frame.width.toInt() / resolution

        val rect = Rect(origin = 0 point 0, size = resolution size frame.height)
        val scale = ColorScale(colors)

        for (i in 0 until nbLines) {
            rect.originX = i.f * resolution

            val progress = i / nbLines.f
            val color = scale[progress]
            fill(color.red.f, color.green.f, color.blue.f)
            stroke(color.red.f, color.green.f, color.blue.f)

            draw(rect)
        }
    }
}

inline fun <reified T : PApplet> runApplet(vararg args: String) {
    PApplet.main(T::class.java, *args)
}

// TODO Fix this
//fun testPApplet(size: ESize = Size(800, 800), onDraw: KotlinPApplet.() -> Unit) {
//    val applet = TestApplet.create()
//    applet.size = size
//    applet.onDraw = onDraw
//    applet.unblock()
//}

private class TestApplet : KotlinPApplet() {

    companion object {

        private var instance: TestApplet? = null
        private val lock = java.lang.Object()

        fun create(): TestApplet {
            runApplet<TestApplet>()
            synchronized(lock) {
                lock.wait()
            }
            return instance!!
        }
    }

    init {
        instance = this
        synchronized(lock) {
            lock.notify()
        }
    }

    fun unblock() {
        synchronized(lock) {
            lock.notify()
        }
    }

    var size: ESize? = null
    var onDraw: KotlinPApplet.() -> Unit = {}

    override fun settings() {
        synchronized(lock) {
            lock.wait()
        }

        size?.let { size ->
            size(size.width.toInt(), size.height.toInt())
        }
    }

    override fun draw() {
        onDraw()
    }
}

