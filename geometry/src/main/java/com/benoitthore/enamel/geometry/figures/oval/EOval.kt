package com.benoitthore.enamel.geometry.figures.oval

import com.benoitthore.enamel.geometry.interfaces.bounds.HasBounds
import com.benoitthore.enamel.geometry.svg.ESVG
import com.benoitthore.enamel.geometry.svg.SVGContext

interface EOval : ESVG, HasBounds<EOval, EOvalMutable> {

    val rx: Float
    val ry: Float

    override fun addTo(context: SVGContext) {
        context.oval(this)
    }
}

