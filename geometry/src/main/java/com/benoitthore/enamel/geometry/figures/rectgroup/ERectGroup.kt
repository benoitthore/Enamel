package com.benoitthore.enamel.geometry.figures.rectgroup

import com.benoitthore.enamel.core.math.*
import com.benoitthore.enamel.geometry.alignement.EAlignment
import com.benoitthore.enamel.geometry.alignement.rectAlignedOutside
import com.benoitthore.enamel.geometry.allocate
import com.benoitthore.enamel.geometry.primitives.offset.EOffset
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.size.ESizeMutable
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.figures.rect.ERectMutable
import com.benoitthore.enamel.geometry.figures.rect.union
import com.benoitthore.enamel.geometry.interfaces.bounds.*
import javax.swing.text.html.CSS

interface ERectGroup : HasBounds {
    val frame: ERect
    val rects: List<HasBounds>
    val size get() = rects.size

    override val left: Float
        get() = frame.left
    override val top: Float
        get() = frame.top
    override val right: Float
        get() = frame.right
    override val bottom: Float
        get() = frame.bottom
    override val centerX: Float
        get() = frame.centerX
    override val centerY: Float
        get() = frame.centerY
}

interface ERectGroupMutable : ERectGroup, CanSetBounds {
    fun updateFrame()

    override val rects: List<CanSetBounds>

    // TODO Test
    override fun setCenter(x: Number, y: Number) {
        val xOff = x.f - frame.centerX
        val yOff = y.f - frame.centerY
        offset(xOff, yOff)
    }

    fun aligned(anchor: EPoint, position: EPoint) {
        val pointAtAnchor = frame.pointAtAnchor(anchor)

        val offsetX = position.x - pointAtAnchor.x
        val offsetY = position.y - pointAtAnchor.y

        selfOffset(offsetX, offsetY)
    }
}

class ERectGroupImpl(override val rects: List<CanSetBounds>) : ERectGroupMutable {

    private val _frame = E.RectMutable()

    override val frame: ERect
        get() = _frame

    init {
        updateFrame()
    }

    override fun updateFrame() {
        rects.union(target = _frame)
    }

    override fun setBounds(left: Number, top: Number, right: Number, bottom: Number) {
        val fromX = frame.originX
        val fromY = frame.originY
        val fromWidth = frame.width
        val fromHeight = frame.height

        _frame.setSides(left, top, right, bottom)

        val toX = frame.originX
        val toY = frame.originY
        val toWidth = frame.width
        val toHeight = frame.height

        rects.forEach { rect ->
            rect.selfMap(
                fromX = fromX,
                fromY = fromY,
                fromWidth = fromWidth,
                fromHeight = fromHeight,
                toX = toX,
                toY = toY,
                toWidth = toWidth,
                toHeight = toHeight
            )
        }
    }
}


//class ERectGroup(private val _rects: List<ERectMutable>, overrideFrame: ERect? = null) :
//    Iterable<ERect> by _rects {
//
//    val frame: ERect get() = _frame
//    val size: ESize get() = _frame.size
//    val origin: EPoint get() = _frame.origin
//    val rects: List<ERect> get() = _rects
//
//    val count get() = _rects.size
//
//    private val _frame = E.RectMutable()
//
//    init {
//        val frameTmp = overrideFrame ?: _rects.union()
//        _frame.size.set(frameTmp.size)
//        _frame.origin.set(frameTmp.origin)
//    }
//
//    fun offsetOrigin(x: Number, y: Number) {
//        _frame.origin.selfOffset(x, y)
//        _rects.forEach { it.selfOffset(x, y) }
//    }
//
//    fun scaledAnchor(factor: Number, anchor: EPoint) {
//        val factor = factor.f
//        _rects.forEach { it.selfScaleAnchor(factor, anchor) }
//        _frame.selfScaleAnchor(factor, anchor)
//    }
//
//    fun scaledRelative(factor: Number, point: EPoint) {
//        val factor = factor.f
//        _rects.forEach { it.selfScaleRelative(factor, point) }
//        _frame.selfScaleRelative(factor, point)
//    }
//
//    fun aligned(anchor: EPoint, position: EPoint) {
//        val pointAtAnchor = frame.pointAtAnchor(anchor)
//
//        val offsetX = position.x - pointAtAnchor.x
//        val offsetY = position.y - pointAtAnchor.y
//
//        offsetOrigin(offsetX, offsetY)
//    }
//}

