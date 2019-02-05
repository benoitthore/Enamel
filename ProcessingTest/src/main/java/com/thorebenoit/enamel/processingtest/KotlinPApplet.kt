package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.core.f
import com.thorebenoit.enamel.kotlin.core.i
import com.thorebenoit.enamel.kotlin.geometry.figures.*
import com.thorebenoit.enamel.kotlin.threading.coroutine
import com.thorebenoit.enamel.kotlin.core.tryCatch
import com.thorebenoit.enamel.kotlin.geometry.allocate
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointImmutable
import com.thorebenoit.enamel.kotlin.geometry.primitives.point
import com.thorebenoit.enamel.kotlin.geometry.toCircle
import processing.core.PApplet
import processing.event.KeyEvent
import processing.event.MouseEvent

private typealias MouseEventListener = (MouseEvent) -> Unit
private typealias KeyEventListener = (KeyEvent) -> Unit


abstract class KotlinPApplet : PApplet() {
    override fun settings() {
        size(800, 800)
    }


    val mousePosition get() = allocate { mouseX point mouseY }

    val center get() = allocate { ERect(size = esize).center(EPoint()) }
    val eframe get() = allocate { ERect(size = esize) }
    var esize: ESize
        get() = allocate { width size height }
        set(value) {
            size(value.width.i, value.height.i)
        }
    val ecenter get() = allocate { eframe.center(EPoint()) }

    fun <T : EPointImmutable> T.draw(): T {
//        point(x, y)
        toCircle(6).draw()
        return this
    }


    fun <T : ECircleImmutable> T.draw(): T {
        ellipse(x, y, radius * 2, radius * 2)
        return this
    }

    fun <T : ERectImmutable> T.draw(): T {
        rect(x, y, width, height)
        return this
    }

    suspend fun _delay(n: Number) = kotlinx.coroutines.delay(n.toLong())


    private val mouseClickListeners = mutableListOf<MouseEventListener>()
    private val mouseReleaseListeners = mutableListOf<MouseEventListener>()
    private val mouseDraggedListeners = mutableListOf<MouseEventListener>()
    private val mouseMovedListeners = mutableListOf<MouseEventListener>()
    private val mousePressedListeners = mutableListOf<MouseEventListener>()

    private val keyPressedListeners = mutableListOf<KeyEventListener>()

    fun onMouseClicked(block: MouseEventListener) {
        mouseClickListeners += block
    }

    fun onMouseReleased(block: MouseEventListener) {
        mouseReleaseListeners += block
    }

    fun onMouseMoved(block: MouseEventListener) {
        mouseMovedListeners += block
    }

    fun onMouseDragged(block: MouseEventListener) {
        mouseDraggedListeners += block
    }

    fun onMousePressed(block: MouseEventListener) {
        mousePressedListeners += block
    }

    fun onKeyPressed(block: KeyEventListener) {
        keyPressedListeners += block
    }

    override fun mouseClicked(event: MouseEvent) {
        mouseClickListeners.forEach { it(event) }
    }

    override fun mouseReleased(event: MouseEvent) {
        mouseReleaseListeners.forEach { it(event) }
    }

    override fun mouseMoved(event: MouseEvent) {
        mouseMovedListeners.forEach { it(event) }
    }

    override fun mouseDragged(event: MouseEvent) {
        mouseDraggedListeners.forEach { it(event) }
    }

    override fun mousePressed(event: MouseEvent) {
        mousePressedListeners.forEach { it(event) }
    }

    override fun keyPressed(event: KeyEvent) {
        keyPressedListeners.forEach { it(event) }
    }

    fun stroke(r: Int, g: Int, b: Int) = stroke(r.f, g.f, b.f)
    fun fill(r: Int, g: Int, b: Int) = fill(r.f, g.f, b.f)

}