package com.thorebenoit.enamel.kotlin.geometry.figures

import com.thorebenoit.enamel.kotlin.geometry.GeomertyBufferProvider
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.primitives.EOffset
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType

class ERectGroup(val rects: List<ERect>, sizeBuffer: ESize, originBuffer: EPoint) {
    fun frame(buffer: ERect) = buffer.set(origin = origin, size = size)

    val size: ESize
    // TODO Setting the origin doesn't offset the other rectangles
    val origin: EPoint


    init {
        // TODO Refactor classes so that GeomertyBufferProvider returns TempPoint, TempRect etc
        // PointType -> TempPoint -> Point
        /*
        That way, ImmutablePoint only has a set of getters, TempPoint has everything but only Point is used in Rects, Circle, Etc
         */
        // TODO Dupplicate all of the modification functions like so :
        /*
        selfOffset
        selfScale

        selfInset

        Self functions should be in point, other should be in immutable
         */

        val frameTmp = rects.union(GeomertyBufferProvider.rect())
        size = frameTmp.size.copy(sizeBuffer)
        origin = frameTmp.origin.copy(originBuffer)
    }

    fun aligned(anchor: EPointType, position: EPointType) {
        val pointAtAnchor =
            frame(GeomertyBufferProvider.rect()).pointAtAnchor(anchor, buffer = GeomertyBufferProvider.point())

        val offsetX = position.x - pointAtAnchor.x
        val offsetY = position.y - pointAtAnchor.y

        origin.offset(offsetX, offsetY, buffer = origin)
        // TODO remove this
        rects.forEach { it.offset(offsetX,offsetY) }
    }
}

fun List<ESize>.rectGroup(
    alignement: EAlignment,
    anchor: EPointType = EPointType.zero,
    position: EPointType = EPointType.zero,
    padding: EOffset = EOffset.zero,
    spacing: Number = 0,
    buffer: ERectGroup
): ERectGroup {
    if (buffer.rects.size < size) {
        throw Exception("Buffer size too small: ${buffer.rects} but needs at least $size")
    }


    var prev = GeomertyBufferProvider.rect()
    val rects = mapIndexed { i, size ->
        prev = prev.rectAlignedOutside(
            aligned = alignement,
            size = size,
            spacing = if (prev.size == ESizeImmutable.zero) 0 else spacing,
            buffer = buffer.rects[i]
        )
        prev
    }

    val rectGroup = ERectGroup(rects, buffer.size, buffer.origin)
    rectGroup.aligned(anchor, position)

    return rectGroup
}