package com.benoitthore.enamel.geometry.figures

import com.benoitthore.enamel.core.math.*
import com.benoitthore.enamel.geometry.alignement.EAlignment
import com.benoitthore.enamel.geometry.alignement.rectAlignedOutside
import com.benoitthore.enamel.geometry.allocate
import com.benoitthore.enamel.geometry.primitives.EOffset
import com.benoitthore.enamel.geometry.primitives.EPoint
import com.benoitthore.enamel.geometry.builders.E

class ERectGroup(private val _rects: List<ERectMutable>, overrideFrame: ERect? = null) :
    Iterable<ERect> by _rects {

    val frame: ERect get() = _frame
    val size: ESize get() = _frame.size
    val origin: EPoint get() = _frame.origin
    val rects: List<ERect> get() = _rects

    val count get() = _rects.size

    private val _frame = E.RectMutable()
//    private val _origin: EPointMutable
//    private val _size: ESizeMutable

    init {
        val frameTmp = overrideFrame ?: _rects.union()
        _frame.size.set(frameTmp.size)
        _frame.origin.set(frameTmp.origin)
    }

    fun offsetOrigin(x: Number, y: Number) {
        _frame.origin.selfOffset(x, y)
        _rects.forEach { it.selfOffset(x, y) }
    }

    fun scaledAnchor(factor: Number, anchor: EPoint) {
        val factor = factor.f
        _rects.forEach { it.selfScaleAnchor(factor, anchor) }
        _frame.selfScaleAnchor(factor, anchor)
    }

    fun scaledRelative(factor: Number, point: EPoint) {
        val factor = factor.f
        _rects.forEach { it.selfScaleRelative(factor, point) }
        _frame.selfScaleRelative(factor, point)
    }

    fun aligned(anchor: EPoint, position: EPoint) {
        val pointAtAnchor = frame.pointAtAnchor(anchor)

        val offsetX = position.x - pointAtAnchor.x
        val offsetY = position.y - pointAtAnchor.y

        offsetOrigin(offsetX, offsetY)
    }
}

// Allocates because this is essentially a constructor
fun List<ESize>.rectGroup(
    alignment: EAlignment,
    anchor: EPoint = E.Point.zero,
    position: EPoint = E.Point.zero,
    padding: EOffset = E.Offset.zero,
    spacing: Number = 0
): ERectGroup {


    var prev = allocate { E.RectMutable() }
    val rects = mapIndexed { i, size ->
        prev = allocate {
            prev.rectAlignedOutside(
                alignment = alignment,
                size = size,
                spacing = if (prev.size == E.Size.zero) 0 else spacing
            )
        }
        prev
    }

    val rectGroup = allocate { ERectGroup(rects, rects.union().selfExpand(padding)) }
    rectGroup.aligned(anchor, position)

    return rectGroup
}


fun List<ESize>.rectGroupJustified(
    alignment: EAlignment,
    toFit: Number,
    anchor: EPoint = E.Point.zero,
    position: EPoint = E.Point.zero,
    padding: EOffset = E.Offset.zero
): ERectGroup {
    val pack = rectGroup(alignment)
    val packedSpace = if (alignment.isHorizontal) pack.size.width else pack.size.height
    val spacing = if (pack.count > 1) (toFit.f - packedSpace) / (pack.count - 1) else 0f
    return rectGroup(
        alignment = alignment,
        anchor = anchor,
        position = position,
        padding = padding,
        spacing = spacing
    )
}


fun List<Number>.rectGroupWeights(
    alignment: EAlignment,
    toFit: ESize,
    anchor: EPoint = E.Point.zero,
    position: EPoint = E.Point.zero,
    padding: EOffset = E.Offset.zero,
    spacing: Number = 0
): ERectGroup {

    if (isEmpty()) {
        return ERectGroup(emptyList())
    }

    val spacing = spacing.toFloat()

    val actualWidth =
        if (alignment.isHorizontal) toFit.width - (spacing * (size - 1)) else toFit.width
    val actualHeight =
        if (alignment.isVertical) toFit.height - (spacing * (size - 1)) else toFit.height

    val totalWeight = sumByDouble { it.d }.f

    val sizes: List<ESizeMutable> = if (alignment.isHorizontal) {
        map { E.SizeMutable((actualWidth) * it.toFloat() / totalWeight, toFit.height) }
    } else {
        map { E.SizeMutable(toFit.width, (actualHeight) * it.toFloat() / totalWeight) }
    }

    return sizes.rectGroup(
        alignment = alignment,
        anchor = anchor,
        position = position,
        padding = padding,
        spacing = spacing
    )
}