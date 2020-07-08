package com.benoitthore.enamel.geometry.figures.oval

import com.benoitthore.enamel.geometry.Resetable
import com.benoitthore.enamel.geometry.interfaces.bounds.EShape
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.svg.SVGContext

interface EOval : EShape<EOval> {
    val size: ESize
    val center: EPoint

    var rx: Float
        get() = size.width / 2f
        set(value) {
            size.width = value
        }
    var ry: Float
        get() = size.height / 2f
        set(value) {
            size.height = value
        }
    override var centerY: Float
        get() = center.y
        set(value) {
            center.y = value
        }
    override var centerX: Float
        get() = center.x
        set(value) {
            center.x = value
        }


    override fun addTo(context: SVGContext) {
        context.oval(this)
    }
}