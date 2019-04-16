package com.thorebenoit.enamel.processingtest.kotlinapplet.applet

import com.thorebenoit.enamel.core.fromJsonSafe
import com.thorebenoit.enamel.core.math.*
import com.thorebenoit.enamel.core.toJson
import com.thorebenoit.enamel.geometry.primitives.*
import com.thorebenoit.enamel.geometry.alignement.EAlignment
import com.thorebenoit.enamel.geometry.*
import com.thorebenoit.enamel.geometry.figures.*
import com.thorebenoit.enamel.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayoutLeaf
import com.thorebenoit.enamel.geometry.primitives.EPointMutable
import com.thorebenoit.enamel.geometry.primitives.EPoint
import com.thorebenoit.enamel.geometry.toCircle
import processing.core.PApplet
import processing.core.PConstants
import processing.event.KeyEvent
import processing.event.MouseEvent
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

private typealias MouseEventListener = (MouseEvent) -> Unit
private typealias KeyEventListener = (KeyEvent) -> Unit


abstract class KotlinPApplet : PApplet() {
    // TODO Move to library or delete if unused
    inline infix fun <T, R> T.then(block: T.() -> R) = block()

    init {
        appletQueue += this
    }

    companion object {
        var defaultSize: ESize = 200 size 200
        val appletQueue: BlockingQueue<KotlinPApplet> = LinkedBlockingQueue()

        inline fun <reified T : KotlinPApplet> createApplet(width: Number, height: Number) =
            createApplet<T>(width size height)

        inline fun <reified T : KotlinPApplet> createApplet(size: ESize = defaultSize): T {
            PApplet.main(T::class.java, size.toJson())
            return appletQueue.poll() as T
        }
    }

    override fun settings() {
        args?.first()?.fromJsonSafe<ESizeMutable>()?.let {
            esize = it
        }
    }

    val displayFrame get() = allocate { ERect(size = displayWidth size displayHeight) }
    val eframe get() = allocate { ERect(size = esize) }
    var esize: ESizeMutable
        get() = allocate { width size height }
        set(value) {
            surface?.apply {
                setSize(value.width.i, value.height.i)
            } ?: kotlin.run {
                size(value.width.i, value.height.i)
            }
        }
    var windowLocation: EPoint = EPoint.inv
        get() {
            if (field == EPoint.inv) { // Because lateinit isn't possible
                field = allocate { displayFrame.rectAlignedInside(aligned = EAlignment.middle, size = esize).center() }
            }
            return field
        }
        set(value) {
            field = value
            surface.setLocation(value.x.i, value.y.i)
        }


    val center get() = allocate { ERect(size = esize).center(EPointMutable()) }

    private val mousePositionBuffer = allocate { EPointMutable() }

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

    open fun <T : EPoint> T.draw(radius: Number = 5): T {
        allocate { toCircle(radius).draw() }
        return this
    }


    open fun <T : ECircle> T.draw(): T {
        ellipse(x, y, radius * 2, radius * 2)
        return this
    }

    open fun <T : ERectType> T.draw(): T {
        rectMode(PConstants.CORNER)
        rect(x, y, width, height)
        return this
    }

    open fun <T : ELine> T.draw(): T {
        line(x1, y1, x2, y2)
        return this
    }

    open fun <T : ELinearFunction> T.draw(): T {
        val x1 = 0f
        val x2 = width.f
        line(x1, this[x1], x2, this[x2])
        return this
    }

    protected open fun <T : ELayoutLeaf> T.draw(): T {

        fill(color)

        val frame = frame.toMutable()
        if (frame.height < 1f) {
            frame.height = 1f
        }
        if (frame.width < 1f) {
            frame.width = 1f
        }
        frame.draw()

        return this
    }

    open fun <T : ELayout> T.draw(): T {
        childLayouts.forEach {
            (it as? ELayoutLeaf)?.draw()
            it.draw()
        }
        return this
    }

    open fun <T : ELayout> List<T>.draw(): List<T> {
        forEach { it.draw() }
        return this
    }

    fun <T : ELayout> T.arrangeAndDraw(frame: ERectType = eframe): T {
        arrange(frame)
        draw()
        return this
    }

    open fun <E : EPoint> List<E>.draw(closed: Boolean = true): List<E> {
        beginShape()

        forEach {
            vertex(it.x, it.y)
        }
        if (closed) {
            endShape(PConstants.CLOSE)
        } else {
            endShape(PConstants.OPEN)
        }

        return this
    }

    fun stroke(r: Number, g: Number, b: Number) = stroke(r.f, g.f, b.f)
    fun fill(r: Number, g: Number, b: Number) = fill(r.f, g.f, b.f)

}

