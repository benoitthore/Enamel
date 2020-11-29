package com.benoitthore.enamel.geometry.figures.rect

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.builders.*
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.functions.EShape
import com.benoitthore.enamel.geometry.functions.EShapeMutable
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable
import com.benoitthore.enamel.geometry.primitives.size.ESizeMutable
import com.benoitthore.enamel.geometry.svg.SVGContext

interface ERect : EShape {
    val origin: EPoint
    val size: ESize

    override fun _copy(): ERect = Rect(this)
    fun set(other: ERect): ERect


    override fun addTo(context: SVGContext) {
        context.rect(this)
    }

    /**
     * @return true if the first parameter is fully contained in this rectangle
     */
    fun contains(other: ERect) = contains(other.origin, other.size)

    /**
     * @return true if the first parameter is fully contained in this rectangle
     */
    fun contains(origin: EPoint, size: ESize) =
        contains(origin.x, origin.y, size.width, size.height)

    /**
     * @return true if the first parameter is fully contained in this rectangle
     */

    fun contains(x: Number, y: Number, width: Number, height: Number): Boolean {
        val x = x.f
        val y = y.f
        return !(x < left
                || x + width.toFloat() > right
                || y < top
                || y + height.toFloat() > bottom
                )
    }

    fun contains(p: EPoint, radius: Number): Boolean =
        contains(p.x, p.y, radius)

    fun contains(c: ECircle): Boolean = contains(c.center, c.radius)

    fun contains(x: Number, y: Number, radius: Number): Boolean =
        radius.f.let { radius ->
            contains(
                x.f - radius,
                y.f - radius,
                radius * 2,
                radius * 2
            )
        }

    fun intersects(other: ERect) = intersects(
        top = other.top,
        left = other.left,
        right = other.right,
        bottom = other.bottom
    )

    fun intersects(
        left: Number, top: Number, right: Number,
        bottom: Number
    ): Boolean {
        val left = left.f
        val top = top.f
        val right = right.f
        val bottom = bottom.f
        val thisleft = this.left
        val thistop = this.top
        val thisbottom = this.bottom
        val thisright = this.right
        return (thisleft < right && left < thisright
                && thistop < bottom && top < thisbottom)
    }


    // contains Point
    fun contains(x: Number, y: Number) = contains(x, y, 0, 0)

    fun contains(p: EPoint) = contains(p.x, p.y)
}

interface ERectMutable : ERect, EShapeMutable {
    override val origin: EPointMutable
    override val size: ESizeMutable

    override fun _copy(): ERectMutable = MutableRect(this)

}


