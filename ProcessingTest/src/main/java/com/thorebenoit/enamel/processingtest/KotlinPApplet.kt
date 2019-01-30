package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.core.f
import com.thorebenoit.enamel.kotlin.core.i
import com.thorebenoit.enamel.kotlin.geometry.figures.*
import com.thorebenoit.enamel.kotlin.threading.coroutine
import com.thorebenoit.enamel.kotlin.core.tryCatch
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.point
import processing.core.PApplet
import processing.event.KeyEvent
import processing.event.MouseEvent

private typealias MouseEventListener = (MouseEvent) -> Unit
private typealias KeyEventListener = (KeyEvent) -> Unit


abstract class KotlinPApplet : PApplet() {

    val mousePosition get() = mouseX point mouseY

    val eframe get() = ERect(size = esize)
    var esize: ESize
        get() = width size height
        set(value) {
            tryCatch({
                size(value.width.i, value.height.i)
            }) {
                coroutine {
                    kotlinx.coroutines.delay(10)
                    esize = value
                }
            }
        }
    val ecenter get() = eframe.center(EPoint())

    fun ECircle.draw(): ECircle {
        ellipse(x, y, radius * 2, radius * 2)
        return this
    }

    fun ERect.draw(): ERect {
        rect(x, y, width, height)
        return this
    }

    override fun settings() {
        size(400, 400)
    }





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