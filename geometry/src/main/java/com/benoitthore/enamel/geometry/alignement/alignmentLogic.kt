package com.benoitthore.enamel.geometry.alignement

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.CanSetBounds
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.geometry.figures.ESize

fun ERect.rectAlignedInside(
    alignment: EAlignment,
    size: ESize,
    spacing: Number = 0,
    target: ERectMutable = E.RectMutable()
): ERectMutable {
    target.set(this)
    val spacing = spacing.f

    val anchor = alignment.namedPoint
    val spacingSign = alignment.spacingSign

    val position = pointAtAnchor(alignment.namedPoint, target = target.origin)
        .offset(spacingSign.x * spacing, spacingSign.y * spacing, target = target.origin)

    target.size.set(size)

    return E.RectMutableAnchorPos(
        anchor = anchor,
        position = position,
        size = target.size,
        target = target
    )
}


fun ERect.rectAlignedOutside(
    alignment: EAlignment,
    size: ESize,
    spacing: Number = 0,
    target: ERectMutable = E.RectMutable()
): ERectMutable {
    target.set(this)
    val spacing = spacing.f

    val anchor = alignment.flipped.namedPoint
    val spacingSign = alignment.flipped.spacingSign

    val position = pointAtAnchor(alignment.namedPoint, target = target.origin)
        .offset(spacingSign.x * spacing, spacingSign.y * spacing, target = target.origin)

    target.size.set(size)

    return E.RectMutableAnchorPos(
        anchor = anchor,
        position = position,
        size = target.size,
        target = target
    )
}