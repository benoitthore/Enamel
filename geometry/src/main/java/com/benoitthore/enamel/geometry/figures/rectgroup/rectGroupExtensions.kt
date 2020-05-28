package com.benoitthore.enamel.geometry.figures.rectgroup

import com.benoitthore.enamel.core.math.d
import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.alignement.EAlignment
import com.benoitthore.enamel.geometry.alignement.rectAlignedOutside
import com.benoitthore.enamel.geometry.alignement.selfAlignInside
import com.benoitthore.enamel.geometry.allocate
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.figures.rect.ERectMutable
import com.benoitthore.enamel.geometry.figures.rect.union
import com.benoitthore.enamel.geometry.interfaces.bounds.CanSetBounds
import com.benoitthore.enamel.geometry.interfaces.bounds.HasBounds
import com.benoitthore.enamel.geometry.interfaces.bounds.selfExpand
import com.benoitthore.enamel.geometry.primitives.offset.EOffset
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.size.ESizeMutable

fun <T : CanSetBounds> List<T>.toRectGroup(): ERectGroupMutable<T> =
    ERectGroupMutable.ERectGroupImpl(this)

fun List<ESize>.rectGroup(
    alignment: EAlignment,
    anchor: EPoint = E.Point.zero,
    position: EPoint = E.Point.zero,
    spacing: Number = 0
): ERectGroupMutable<ERectMutable> {


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

    val rectGroup = ERectGroupMutable.ERectGroupImpl(rects)

    rectGroup.aligned(anchor, position)

    return rectGroup
}


fun List<ESize>.rectGroupJustified(
    alignment: EAlignment,
    toFit: Number,
    anchor: EPoint = E.Point.zero,
    position: EPoint = E.Point.zero
): ERectGroupMutable<*,*,*> {
    val pack = rectGroup(alignment)
    val packedSpace = if (alignment.isHorizontal) pack.width else pack.height
    val spacing = if (pack.size > 1) (toFit.f - packedSpace) / (pack.size - 1) else 0f
    return rectGroup(
        alignment = alignment,
        anchor = anchor,
        position = position,
        spacing = spacing
    )
}


fun List<Number>.rectGroupWeights(
    alignment: EAlignment,
    toFit: ESize,
    anchor: EPoint = E.Point.zero,
    position: EPoint = E.Point.zero,
    spacing: Number = 0
): ERectGroupMutable<ERectMutable> {

    if (isEmpty()) {
        return ERectGroupMutable.ERectGroupImpl(emptyList())
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
//        padding = padding,
        spacing = spacing
    )
}