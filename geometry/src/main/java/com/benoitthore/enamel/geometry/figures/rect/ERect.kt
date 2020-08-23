package com.benoitthore.enamel.geometry.figures.rect

import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.functions.EShape
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.svg.SVGContext

interface ERect : EShape<ERect> {
    val origin: EPoint
    val size: ESize

    override fun addTo(context: SVGContext) {
        context.rect(this)
    }
}