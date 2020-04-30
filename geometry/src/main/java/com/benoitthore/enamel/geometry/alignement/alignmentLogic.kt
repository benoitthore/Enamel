package com.benoitthore.enamel.geometry.alignement

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.geometry.figures.ESize
import com.benoitthore.enamel.geometry.interfaces.*

fun <T : CanSetBounds> T.selfAlign(
    frame: HasBounds,
    alignment: EAlignment,
    isOutside: Boolean,
    spacing: Number = 0
): T {
    val anchorSpacingAlignment = if (isOutside) alignment.flipped else alignment

    val spacing = spacing.f

    val anchor = anchorSpacingAlignment.namedPoint
    val spacingSign = anchorSpacingAlignment.spacingSign

    val positionX = frame.pointAtAnchorX(alignment.namedPoint.x) + spacingSign.x * spacing
    val positionY = frame.pointAtAnchorY(alignment.namedPoint.y) + spacingSign.y * spacing

    this.originX = positionX - width * anchor.x
    this.originY = positionY - height * anchor.y

    return this
}

fun <T : CanSetBounds> T.selfAlignOutside(
    frame : HasBounds, alignment: EAlignment, spacing: Number = 0
): T = selfAlign(frame, alignment, true, spacing)

fun <T : CanSetBounds> T.selfAlignInside(
    frame : HasBounds, alignment: EAlignment, spacing: Number = 0
): T = selfAlign(frame, alignment, false, spacing)

fun ERect.rectAlignedInside(
    alignment: EAlignment,
    size: ESize,
    spacing: Number = 0,
    target: ERectMutable = E.RectMutable()
): ERectMutable = target.setSize(size).selfAlignInside(this, alignment, spacing)

fun ERect.rectAlignedOutside(
    alignment: EAlignment,
    size: ESize,
    spacing: Number = 0,
    target: ERectMutable = E.RectMutable()
): ERectMutable = target.setSize(size).selfAlignOutside(this, alignment, spacing)

