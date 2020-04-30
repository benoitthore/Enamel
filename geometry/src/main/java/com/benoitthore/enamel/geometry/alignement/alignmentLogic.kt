package com.benoitthore.enamel.geometry.alignement

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.geometry.figures.ESize
import com.benoitthore.enamel.geometry.interfaces.*

//fun <T : CanSetBounds> T.alignOutside(
//    rect: ERect, alignment: EAlignment, spacing: Number = 0,
//    target: ERectMutable = E.RectMutable()
//): T {
//    getBounds(target) // request the size of the target rectangle
//    rect.rectAlignedOutside(
//        alignment = alignment,
//        size = target.size,
//        spacing = spacing,
//        target = target
//    )
//    return this
//}
//
//fun <T : CanSetBounds> T.alignInside(
//    rect: ERect, alignment: EAlignment, spacing: Number = 0
//
//): T {
//    val spacing = spacing.f
//
//    val anchor = alignment.namedPoint
//    val spacingSign = alignment.spacingSign
//
//    val positionX = rect.pointAtAnchorX(alignment.namedPoint.x) + spacingSign.x * spacing
//    val positionY = rect.pointAtAnchorY(alignment.namedPoint.y) + spacingSign.y * spacing
//
//    val x = positionX - rect.size.width * anchor.x
//    val y = positionY - rect.size.height * anchor.y
//    setBounds()
////     E.RectMutableAnchorPos(
////        anchor = anchor,
////        position = position,
////        size = target.size,
////        target = target
////    )
//
//    return this
//}

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