package com.benoitthore.enamel.geometry.alignement

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.interfaces.bounds.*


fun <T : EShape<T>> T.selfAlign(
    frame: EShape<*>,
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

    val left = positionX - width * anchor.x
    val top = positionY - height * anchor.y
    val bottom = top + height
    val right = left + width

    _setBounds(
        left = left,
        top = top,
        bottom = bottom,
        right = right
    )


    return this
}

fun <T : EShape<T>> T.alignedInside(
    frame: EShape<*>,
    alignment: EAlignment,
    spacing: Number = 0,
    target: T = copy()
): T = target.set(this).selfAlignInside(frame, alignment, spacing)

fun <T : EShape<T>> T.alignedOutside(
    frame: EShape<*>,
    alignment: EAlignment,
    spacing: Number = 0,
    target: T = copy()
): T = target.set(this).selfAlignOutside(frame, alignment, spacing)


fun <T : EShape<T>> T.selfAlignOutside(
    frame: EShape<*>, alignment: EAlignment, spacing: Number = 0
): T = selfAlign(frame, alignment, true, spacing)

fun <T : EShape<T>> T.selfAlignInside(
    frame: EShape<*>, alignment: EAlignment, spacing: Number = 0
): T = selfAlign(frame, alignment, false, spacing)

fun ERect.rectAlignedInside(
    alignment: EAlignment,
    size: ESize,
    spacing: Number = 0,
    target: ERect = E.Rect()
): ERect = target.setSize(size).selfAlignInside(this, alignment, spacing)

fun ERect.rectAlignedOutside(
    alignment: EAlignment,
    size: ESize,
    spacing: Number = 0,
    target: ERect = E.Rect()
): ERect = target.setSize(size).selfAlignOutside(this, alignment, spacing)

