package com.benoitthore.enamel.geometry.figures.oval

import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.primitives.EPoint
import com.benoitthore.enamel.geometry.svg.SVGContext

interface EOval : ERect {

    val center: EPoint
    val rx: Float
    val ry: Float

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

