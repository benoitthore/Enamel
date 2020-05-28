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

    override val height get() = size.height
    override val width get() = size.width
}

