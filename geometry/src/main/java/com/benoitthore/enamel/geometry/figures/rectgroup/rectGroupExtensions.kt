package com.benoitthore.enamel.geometry.figures.rectgroup

import com.benoitthore.enamel.core.math.d
import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.alignement.EAlignment
import com.benoitthore.enamel.geometry.alignement.selfAlignOutside
import com.benoitthore.enamel.geometry.allocate
import com.benoitthore.enamel.geometry.builders.*
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.functions.EShape
import com.benoitthore.enamel.geometry.functions.setSize
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.size.ESize

//fun  List<T>.toRectGroup(): ERectGroup<T> =
//    ERectGroup.ERectGroupImpl(this)
//
//
//fun List<ESize>.rectGroup(
//    alignment: EAlignment,
//    anchor: EPoint = Point.Zero(),
//    position: EPoint = Point.Zero(),
//    spacing: Number = 0
//): ERectGroup<ERect> {
//
//    var prev = allocate { Rect() }
//    val rects = mapIndexed { i, size ->
//        prev = allocate {
//
////            prev.alignedOutside(
////                alignment = alignment,
////                size = size,
////                spacing = if (prev.size == Size.zero()) 0 else spacing
////            )
//
//            TODO("Check if this works")
//            MutableRect(size).selfAlignOutside(
//                frame = prev,
//                alignment = alignment,
//                spacing = if (prev.size == Size.Zero()) 0 else spacing
//            ).setSize(size)
//
//        }
//        prev
//    }
//
//    val rectGroup = ERectGroup.ERectGroupImpl(rects)
//
//    rectGroup.aligned(anchor, position)
//
//    return rectGroup
//}
//
//
//fun List<ESize>.rectGroupJustified(
//    alignment: EAlignment,
//    toFit: Number,
//    anchor: EPoint = Point.Zero(),
//    position: EPoint = Point.Zero()
//): ERectGroup<ERect> {
//    val pack = rectGroup(alignment)
//    val packedSpace = if (alignment.isHorizontal) pack.width else pack.height
//    val spacing = if (pack.rects.size > 1) (toFit.f - packedSpace) / (pack.rects.size - 1) else 0f
//    return rectGroup(
//        alignment = alignment,
//        anchor = anchor,
//        position = position,
//        spacing = spacing
//    )
//}
//
//
//fun List<Number>.rectGroupWeights(
//    alignment: EAlignment,
//    toFit: ESize,
//    anchor: EPoint = Point.Zero(),
//    position: EPoint = Point.Zero(),
//    spacing: Number = 0
//): ERectGroup<ERect> {
//
//    if (isEmpty()) {
//        return ERectGroup.ERectGroupImpl(emptyList())
//    }
//
//    val spacing = spacing.toFloat()
//
//    val actualWidth =
//        if (alignment.isHorizontal) toFit.width - (spacing * (size - 1)) else toFit.width
//    val actualHeight =
//        if (alignment.isVertical) toFit.height - (spacing * (size - 1)) else toFit.height
//
//    val totalWeight = sumByDouble { it.d }.f
//
//    val sizes: List<ESize> = if (alignment.isHorizontal) {
//        map { Size((actualWidth) * it.toFloat() / totalWeight, toFit.height) }
//    } else {
//        map { Size(toFit.width, (actualHeight) * it.toFloat() / totalWeight) }
//    }
//
//    return sizes.rectGroup(
//        alignment = alignment,
//        anchor = anchor,
//        position = position,
////        padding = padding,
//        spacing = spacing
//    )
//}