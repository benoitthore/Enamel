package com.thorebenoit.enamel.processingtest.kotlinapplet.applet

import com.thorebenoit.enamel.kotlin.core.math.f
import com.thorebenoit.enamel.kotlin.core.math.i
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.figures.*
import com.thorebenoit.enamel.kotlin.geometry.allocate
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType
import com.thorebenoit.enamel.kotlin.geometry.toCircle
import processing.core.PApplet
import processing.core.PConstants
import processing.core.PGraphics
import processing.event.KeyEvent
import processing.event.MouseEvent
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

private typealias MouseEventListener = (MouseEvent) -> Unit
private typealias KeyEventListener = (KeyEvent) -> Unit


abstract class KotlinPApplet : PApplet() {

    init {
        appletQueue += this
    }

    companion object {
        val appletQueue: BlockingQueue<KotlinPApplet> = LinkedBlockingQueue()
        inline fun <reified T : KotlinPApplet> createApplet(): T {
            PApplet.main(T::class.java)
            return appletQueue.poll() as T
        }
    }

    override fun settings() {
        size(800, 800)
    }

    val displayFrame get() = allocate { ERect(size = displayWidth size displayHeight) }
    val eframe get() = allocate { ERect(size = esize) }
    var esize: ESize
        get() = allocate { width size height }
        set(value) {
            size(value.width.i, value.height.i)
        }
    var windowLocation: EPointType = EPointType.inv
        get() {
            if (field == EPointType.inv) { // Because lateinit isn't possible
                field = allocate { displayFrame.rectAlignedInside(aligned = EAlignment.middle, size = esize).center() }
            }
            return field
        }
        set(value) {
            field = value
            surface.setLocation(value.x.i, value.y.i)
        }


    val PGraphics.eframe get() = allocate { ERect(size = esize) }
    var PGraphics.esize: ESize
        get() = allocate { width size height }
        set(value) {
            size(value.width.i, value.height.i)
        }

    val center get() = allocate { ERect(size = esize).center(EPoint()) }

    private val mousePositionBuffer = allocate { EPoint() }

    val mousePosition get() = mousePositionBuffer.set(mouseX, mouseY)
    val mousePositionOnScreen get() = mousePosition.selfOffset(windowLocation)


    private val mouseClickListeners = mutableListOf<MouseEventListener>()
    private val mouseReleaseListeners = mutableListOf<MouseEventListener>()
    private val mouseDraggedListeners = mutableListOf<MouseEventListener>()
    private val mouseMovedListeners = mutableListOf<MouseEventListener>()
    private val mousePressedListeners = mutableListOf<MouseEventListener>()
    private val mouseExitedListeners = mutableListOf<MouseEventListener>()
    private val mouseWheelListeners = mutableListOf<MouseEventListener>()

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

    fun onMouseExited(block: MouseEventListener) {
        mouseExitedListeners += block
    }

    fun onMouseWheel(block: MouseEventListener) {
        mouseWheelListeners += block
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

    override fun mouseExited(event: MouseEvent) {
        mouseExitedListeners.forEach { it(event) }
    }

    override fun mouseWheel(event: MouseEvent) {
        mouseWheelListeners.forEach { it(event) }
    }

    override fun keyPressed(event: KeyEvent) {
        keyPressedListeners.forEach { it(event) }
    }


    // Size change
    override fun size(width: Int, height: Int) {
        super.size(width, height)
        internalOnSizeChanged()
    }

    override fun size(width: Int, height: Int, renderer: String?) {
        super.size(width, height, renderer)
        internalOnSizeChanged()
    }

    override fun size(width: Int, height: Int, renderer: String?, path: String?) {
        super.size(width, height, renderer, path)
        internalOnSizeChanged()
    }

    override fun setSize(width: Int, height: Int) {
        super.setSize(width, height)
        internalOnSizeChanged()
    }

    private val onSizeChangedListeners = mutableListOf<() -> Unit>()

    fun onSizeChanged(block: () -> Unit) {
        onSizeChangedListeners += block
    }

    private fun internalOnSizeChanged() {
        onSizeChangedListeners.forEach { it() }
    }


    // Draw helper

    open fun <T : EPointType> T.draw(radius: Number = 5): T {
        allocate { toCircle(radius).draw() }
        return this
    }


    open fun <T : ECircleType> T.draw(): T {
        ellipse(x, y, radius * 2, radius * 2)
        return this
    }

    open fun <T : ERectType> T.draw(): T {
        rect(x, y, width, height)
        return this
    }

    open fun <E : EPointType> List<E>.draw(closed: Boolean = true): List<E> {
        beginShape()

        forEach {
            vertex(it.x, it.y)
        }
        if (closed) {
            endShape(PConstants.CLOSE)
        } else {
            endShape()
        }

        return this
    }


    fun stroke(r: Number, g: Number, b: Number) = stroke(r.f, g.f, b.f)
    fun fill(r: Number, g: Number, b: Number) = fill(r.f, g.f, b.f)

}

