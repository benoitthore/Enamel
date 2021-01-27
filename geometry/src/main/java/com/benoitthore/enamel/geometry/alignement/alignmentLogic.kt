package com.benoitthore.enamel.geometry.alignement

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.builders.*
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.figures.rect.ERectMutable
import com.benoitthore.enamel.geometry.functions.*
import com.benoitthore.enamel.geometry.primitives.size.ESize

fun <I, M> EShape<I, M>.copy(): I where  M : EShapeMutable<I, M>, I : EShape<I, M> = _copy() as I
fun <I, M> EShapeMutable<I, M>.copy(): M where  M : EShapeMutable<I, M>, I : EShape<I, M> =
    _copy() as M


//fun <I, M> M.copy(): I
//        where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> = _copy() as I


fun <T : EShapeMutable<*, *>> T.selfAlign(
    frame: EShape<*, *>,
    alignment: EAlignment,
    isOutside: Boolean,
    spacing: Number = 0
) = apply {
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
}


fun <I, M> I.alignedInside(
    frame: EShape<*, *>,
    alignment: EAlignment,
    spacing: Number = 0,
    target: EShapeMutable<I, M> = toMutable()
) where M : EShapeMutable<I, M>, I : EShape<I, M> =
    target.setBounds(this).selfAlignInside(frame, alignment, spacing)

fun <I, M> I.alignedOutside(
    frame: EShape<*, *>,
    alignment: EAlignment,
    spacing: Number = 0,
    target: EShapeMutable<I, M> = toMutable()
) where M : EShapeMutable<I, M>, I : EShape<I, M> =
    target.setBounds(this).selfAlignOutside(frame, alignment, spacing)


fun <T : EShapeMutable<*, *>> T.selfAlignOutside(
    frame: EShape<*, *>, alignment: EAlignment, spacing: Number = 0
) = selfAlign(frame, alignment, true, spacing)

fun <T : EShapeMutable<*, *>> T.selfAlignInside(
    frame: EShape<*, *>, alignment: EAlignment, spacing: Number = 0
) = selfAlign(frame, alignment, false, spacing)

fun ERect.rectAlignedInside(
    alignment: EAlignment,
    size: ESize,
    spacing: Number = 0,
    target: ERectMutable = MutableRect()
): ERect = target.setSize(size).selfAlignInside(this, alignment, spacing)

fun ERect.rectAlignedOutside(
    alignment: EAlignment,
    size: ESize,
    spacing: Number = 0,
    target: ERectMutable = MutableRect()
): ERect = target.setSize(size).selfAlignOutside(this, alignment, spacing)

