package com.benoitthore.enamel.geometry.figures.oval

import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.figures.rect.ERectMutable
import com.benoitthore.enamel.geometry.interfaces.bounds.HasBounds
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.svg.ESVG
import com.benoitthore.enamel.geometry.svg.SVGContext

interface EOval : ESVG, HasBounds<EOval, EOvalMutable> {

    val center: EPoint
    val rx: Float
    val ry: Float

    val x: Float get() = centerX
    val y: Float get() = centerY

    override val centerX: Float
        get() = center.x
    override val centerY: Float
        get() = center.y

    override val left: Float
        get() = center.x - rx
    override val top: Float
        get() = center.y - ry
    override val right: Float
        get() = center.x + rx
    override val bottom: Float
        get() = center.y + height

    override fun addTo(context: SVGContext) {
        context.oval(this)
    }
}

