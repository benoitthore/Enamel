package com.thorebenoit.enamel.processingtest.kotlinapplet.applet

import com.thorebenoit.enamel.kotlin.core.data.fromJsonSafe
import com.thorebenoit.enamel.kotlin.core.math.f
import com.thorebenoit.enamel.kotlin.core.math.functions.ELinearFunction
import com.thorebenoit.enamel.kotlin.core.math.i
import com.thorebenoit.enamel.kotlin.core.data.toJson
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.figures.*
import com.thorebenoit.enamel.kotlin.geometry.allocate
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.EPlaceHolderLayout
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType
import com.thorebenoit.enamel.kotlin.geometry.toCircle
import processing.core.PApplet
import processing.core.PConstants
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
        var defaultSize: ESizeType = 200 size 200
        val appletQueue: BlockingQueue<KotlinPApplet> = LinkedBlockingQueue()

        inline fun <reified T : KotlinPApplet> createApplet(width: Number, height: Number) =
            createApplet<T>(width size height)

        inline fun <reified T : KotlinPApplet> createApplet(size: ESizeType = defaultSize): T {
            PApplet.main(T::class.java, size.toJson())
            return appletQueue.poll() as T
        }
    }

    override fun settings() {
        args?.first()?.fromJsonSafe<ESize>()?.let {
            esize = it
        }
    }

    val displayFrame get() = allocate { ERect(size = displayWidth size displayHeight) }
    val eframe get() = allocate { ERect(size = esize) }
    var esize: ESize
        get() = allocate { width size height }
        set(value) {
            surface?.apply {
                setSize(value.width.i, value.height.i)
            } ?: kotlin.run {
                size(value.width.i, value.height.i)
            }
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

    open fun <T : ELineType> T.draw(): T {
        line(x1, y1, x2, y2)
        return this
    }

    open fun <T : ELinearFunction> T.draw(): T {
        val x1 = 0f
        val x2 = width.f
        line(x1, this[x1], x2, this[x2])
        return this
    }

    open fun <T : EPlaceHolderLayout> T.draw(): T {
        fill(color)
        this.frame!!.draw()
        return this
    }

    open fun <T : ELayout> T.draw(): T {
        childLayouts.forEach {
            (it as? EPlaceHolderLayout)?.draw()
            it.draw()
        }
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
            endShape(PConstants.OPEN)
        }

        return this
    }

    fun stroke(r: Number, g: Number, b: Number) = stroke(r.f, g.f, b.f)
    fun fill(r: Number, g: Number, b: Number) = fill(r.f, g.f, b.f)

}

