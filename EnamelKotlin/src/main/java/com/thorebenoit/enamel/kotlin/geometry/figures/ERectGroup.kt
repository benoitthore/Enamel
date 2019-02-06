package com.thorebenoit.enamel.kotlin.geometry.figures

import com.thorebenoit.enamel.kotlin.geometry.GeometryBufferProvider
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
        val frameTmp = rects.union(GeometryBufferProvider.rect())
        size = frameTmp.size.copy(sizeBuffer)
        origin = frameTmp.origin.copy(originBuffer)
    }

    fun aligned(anchor: EPointType, position: EPointType) {
        val pointAtAnchor =
            frame(GeometryBufferProvider.rect()).pointAtAnchor(anchor, buffer = GeometryBufferProvider.point())

        val offsetX = position.x - pointAtAnchor.x
        val offsetY = position.y - pointAtAnchor.y

        origin.selfOffset(offsetX, offsetY)
        // TODO remove this
        rects.forEach { it.offset(offsetX, offsetY) }
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


    var prev = GeometryBufferProvider.rect()
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