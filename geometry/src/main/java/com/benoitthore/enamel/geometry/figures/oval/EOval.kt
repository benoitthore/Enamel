package com.benoitthore.enamel.geometry.figures.oval

import com.benoitthore.enamel.geometry.Resetable
import com.benoitthore.enamel.geometry.interfaces.bounds.EShape
import com.benoitthore.enamel.geometry.svg.SVGContext

interface EOval : EShape<EOval> {
    var ry: Float
    var rx: Float

    override fun addTo(context: SVGContext) {
        context.oval(this)
    }
}