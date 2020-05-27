package com.benoitthore.enamel.geometry.figures.rect

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.interfaces.bounds.HasBounds
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.svg.ESVG
import com.benoitthore.enamel.geometry.svg.SVGContext

interface ERect : ESVG, HasBounds<ERect,ERectMutable> {

    val origin: EPoint
    val size: ESize

    override fun addTo(context: SVGContext) {
        context.rect(this)
    }

    fun copy(target: ERectMutable = E.RectMutable()) =
        E.RectMutable(origin.copy(target = target.origin), size.copy(target = target.size))

    override val height get() = size.height
    override val width get() = size.width

    override val top: Float
        get() = origin.y
    override val bottom: Float
        get() = top + height
    override val left: Float
        get() = origin.x
    override val right: Float
        get() = origin.x + width

    override val centerX: Float
        get() = originX + width / 2f
    override val centerY: Float
        get() = originY + height / 2f
}

