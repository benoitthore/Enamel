package com.benoitthore.enamel.geometry.figures.circle

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.interfaces.bounds.EShape
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable
import com.benoitthore.enamel.geometry.svg.ESVG
import com.benoitthore.enamel.geometry.svg.SVGContext

interface ECircle : EShape {
    var radius: Float
    val center: EPointMutable

    override fun addTo(context: SVGContext) {
        context.oval(centerX - radius, centerY - radius, centerX + radius, centerY + radius)
    }

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

fun <T : ECircle> T.set(other: ECircle) = set(other.center.x, other.center.y, other.radius)
fun <T : ECircle> T.set(center: EPoint = this.center, radius: Number = this.radius) =
    set(center.x, center.y, radius)

fun <T : ECircle> T.set(
    x: Number = centerX,
    y: Number = centerY,
    radius: Number = this.radius
): T {
    this.centerX = x.f
    this.centerY = y.f
    this.radius = radius.f
    return this
}