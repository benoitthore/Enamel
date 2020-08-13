package com.benoitthore.enamel.processing

import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.figures.line.ELine
import com.benoitthore.enamel.geometry.figures.oval.EOval
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.primitives.size.ESize
import processing.core.PApplet

inline fun <reified T : PApplet> runApplet(vararg args: String) {
    PApplet.main(T::class.java, *args)
}

// TODO Fix this
//fun testPApplet(size: ESize = E.Size(800, 800), onDraw: KotlinPApplet.() -> Unit) {
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

open class KotlinPApplet : PApplet() {

    fun <T : ERect> T.draw() = apply {
        draw(this)

    }

    fun <T : ECircle> T.draw() = apply {
        draw(this)

    }

    fun <T : EOval> T.draw() = apply {
        draw(this)
    }

    fun <T : ELine> T.draw() = apply {
        draw(this)
    }
}