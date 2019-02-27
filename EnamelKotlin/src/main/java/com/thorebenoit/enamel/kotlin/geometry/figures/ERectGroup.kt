package com.thorebenoit.enamel.kotlin.geometry.figures

import com.thorebenoit.enamel.kotlin.core.math.f
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.GeometryBufferProvider
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.allocate
import com.thorebenoit.enamel.kotlin.geometry.primitives.EOffset
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType

class ERectGroup(private val _rects: List<ERect>, overrideFrame: ERectType? = null) : Iterable<ERectType> by _rects {

    val frame: ERectType get() = _frame
    val size: ESizeType get() = _frame.size
    val origin: EPointType get() = _frame.origin
    val rects: List<ERectType> get() = _rects

    val count get() = _rects.size

    private val _frame = ERect()
//    private val _origin: EPoint
//    private val _size: ESize


    init {
        val frameTmp = overrideFrame ?: _rects.union(GeometryBufferProvider.rect())
        _frame.size.set(frameTmp.size)
        _frame.origin.set(frameTmp.origin)
    }

    fun offsetOrigin(x: Number, y: Number) {
        _frame.origin.selfOffset(x, y)
        _rects.forEach { it.selfOffset(x, y) }
    }

    fun scaledAnchor(factor: Number, anchor: EPointType) {
        val factor = factor.f
        _rects.forEach { it.selfScaleAnchor(factor, anchor) }
        _frame.selfScaleAnchor(factor, anchor)
    }

    fun scaledRelative(factor: Number, point: EPointType) {
        val factor = factor.f
        _rects.forEach { it.selfScaleRelative(factor, point) }
        _frame.selfScaleRelative(factor, point)
    }

    fun aligned(anchor: EPointType, position: EPointType) {
        val pointAtAnchor = frame.pointAtAnchor(anchor, buffer = GeometryBufferProvider.point())

        val offsetX = position.x - pointAtAnchor.x
        val offsetY = position.y - pointAtAnchor.y

        offsetOrigin(offsetX, offsetY)
    }
}

// Allocates because this is essentially a constructor
fun List<ESizeType>.rectGroup(
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
                spacing = if (prev.size == ESizeType.zero) 0 else spacing
            )
        }
        prev
    }

    val rectGroup = allocate { ERectGroup(rects, rects.union().selfPadding(padding)) }
    rectGroup.aligned(anchor, position)

    return rectGroup
}