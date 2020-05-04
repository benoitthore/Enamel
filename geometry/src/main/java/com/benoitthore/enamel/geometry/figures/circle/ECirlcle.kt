package com.benoitthore.enamel.geometry.figures.circle

import com.benoitthore.enamel.geometry.interfaces.bounds.HasBounds
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.svg.ESVG
import com.benoitthore.enamel.geometry.svg.SVGContext

interface ECircle : ESVG,
    HasBounds {

    override fun addTo(context: SVGContext) {
        context.oval(x - radius, y - radius, x + radius, y + radius)
    }

    val center: EPoint
    val radius: Float


    val x: Float get() = center.x
    val y: Float get() = center.y
    override val centerX: Float get() = x
    override val centerY: Float get() = y

    override val left: Float
        get() = x - radius
    override val top: Float
        get() = y - radius
    override val right: Float
        get() = x + radius
    override val bottom: Float
        get() = y + radius


    fun intersects(other: ECircle) =
        this.center.distanceTo(other.center) < other.radius + this.radius

    fun intersects(list: List<ECircle>): Boolean {
        for (other in list) {
            if (other !== this && other.intersects(this)) {
                return true
            }
        }
        return false
    }

    fun contains(x: Number, y: Number): Boolean = center.distanceTo(x, y) < radius
    fun contains(point: EPoint) = contains(point.x, point.y)

}

