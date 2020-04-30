package com.benoitthore.enamel.geometry.alignement

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.geometry.figures.ESize
import com.benoitthore.enamel.geometry.interfaces.*

fun <T : CanSetBounds> T.alignOutside(
    rect: ERect, alignment: EAlignment, spacing: Number = 0,
    target: ERectMutable = E.RectMutable()
): T {
    val spacing = spacing.f

    val anchor = alignment.flipped.namedPoint
    val spacingSign = alignment.flipped.spacingSign

    val positionX = rect.pointAtAnchorX(alignment.namedPoint.x) + spacingSign.x * spacing
    val positionY = rect.pointAtAnchorY(alignment.namedPoint.y) + spacingSign.y * spacing

    this.x = positionX - width * anchor.x
    this.y = positionY - height * anchor.y

    return this
}

fun <T : CanSetBounds> T.alignInside(
    rect: ERect, alignment: EAlignment, spacing: Number = 0
): T {
    val spacing = spacing.f

    val anchor = alignment.namedPoint
    val spacingSign = alignment.spacingSign

    val positionX = rect.pointAtAnchorX(alignment.namedPoint.x) + spacingSign.x * spacing
    val positionY = rect.pointAtAnchorY(alignment.namedPoint.y) + spacingSign.y * spacing

    this.x = positionX - width * anchor.x
    this.y = positionY - height * anchor.y

    return this
}

fun ERect.rectAlignedInside(
    alignment: EAlignment,
    size: ESize,
    spacing: Number = 0,
    target: ERectMutable = E.RectMutable()
): ERectMutable = target.apply { this.size.set(size) }.alignInside(this, alignment, spacing)


fun ERect.rectAlignedOutside(
    alignment: EAlignment,
    size: ESize,
    spacing: Number = 0,
    target: ERectMutable = E.RectMutable()
): ERectMutable  = target.apply { this.size.set(size) }.alignOutside(this, alignment, spacing)