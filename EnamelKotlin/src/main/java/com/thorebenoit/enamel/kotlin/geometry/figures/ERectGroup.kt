package com.thorebenoit.enamel.kotlin.geometry.figures

import com.thorebenoit.enamel.kotlin.core.f
import com.thorebenoit.enamel.kotlin.geometry.GeometryBufferProvider
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.allocate
import com.thorebenoit.enamel.kotlin.geometry.primitives.EOffset
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType

class ERectGroup(private val _rects: List<ERect>, overrideFrame: ERectType? = null) {
    fun frame(buffer: ERect = ERect()) = buffer.set(origin = origin, size = _size)

    val size: ESizeImmutable get() = _size
    val origin: EPointType get() = _origin
    val rects: List<ERectType> get() = _rects

    private val _origin: EPoint
    private val _size: ESize


    init {
        val frameTmp = overrideFrame ?: _rects.union(GeometryBufferProvider.rect())
        _size = allocate { frameTmp.size.copy() }
        _origin = allocate { frameTmp.origin.copy() }
    }

    fun offsetOrigin(x: Number, y: Number) {
        _origin.selfOffset(x, y)
        _rects.forEach { it.selfOffset(x, y) }
    }

    fun scaled(factor: Number, anchor: EPointType) {
        // TODO
//        val factor = factor.f
//        val frameTmp = frame(GeometryBufferProvider.rect())
    }

    fun aligned(anchor: EPointType, position: EPointType) {
        val pointAtAnchor =
            frame(GeometryBufferProvider.rect()).pointAtAnchor(anchor, buffer = GeometryBufferProvider.point())

        val offsetX = position.x - pointAtAnchor.x
        val offsetY = position.y - pointAtAnchor.y

        offsetOrigin(offsetX, offsetY)
    }
}

// Allocates because this is essentially a constructor
fun List<ESize>.rectGroup(
    alignment: EAlignment,
    anchor: EPointType = EPointType.zero,
    position: EPointType = EPointType.zero,
    padding: EOffset = EOffset.zero,
    spacing: Number = 0
): ERectGroup {


    var prev = allocate { ERect() }
    val rects = mapIndexed { i, size ->
        prev = allocate {
            prev.rectAlignedOutside(
                aligned = alignment,
                size = size,
                spacing = if (prev.size == ESizeImmutable.zero) 0 else spacing
            )
        }
        prev
    }

    val rectGroup = allocate { ERectGroup(rects, rects.union().selfPadding(padding)) }
    rectGroup.aligned(anchor, position)

    return rectGroup
}