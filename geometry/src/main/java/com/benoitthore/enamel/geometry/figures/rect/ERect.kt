package com.benoitthore.enamel.geometry.figures.rect

import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.interfaces.bounds.EShape
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable
import com.benoitthore.enamel.geometry.svg.SVGContext

interface ERect : EShape<ERect> {
    val origin: EPointMutable
    val size: ESize

    override fun addTo(context: SVGContext) {
        context.rect(this)
    }
}