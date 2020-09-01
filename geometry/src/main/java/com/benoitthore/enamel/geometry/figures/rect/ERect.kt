package com.benoitthore.enamel.geometry.figures.rect

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.functions.EShape
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.svg.SVGContext

interface ERect : EShape {
    val origin: EPoint
    val size: ESize

    override fun _copy(): ERect = E.Rect(this)
    fun set(other : ERect) : ERect


    override fun addTo(context: SVGContext) {
        context.rect(this)
    }
}


/**
 * @return true if the first parameter is fully contained in this rectangle
 */
fun ERect.contains(other: ERect) = contains(other.origin, other.size)

/**
 * @return true if the first parameter is fully contained in this rectangle
 */
fun ERect.contains(origin: EPoint, size: ESize) =
    contains(origin.x, origin.y, size.width, size.height)

/**
 * @return true if the first parameter is fully contained in this rectangle
 */

fun ERect.contains(x: Number, y: Number, width: Number, height: Number): Boolean {
    val x = x.f
    val y = y.f
    return !(x < left
            || x + width.toFloat() > right
            || y < top
            || y + height.toFloat() > bottom
            )
}

fun ERect.contains(p: EPoint, radius: Number): Boolean =
    this@contains.contains(p.x, p.y, radius)

fun ERect.contains(c: ECircle): Boolean = this@contains.contains(c.center, c.radius)

fun ERect.contains(x: Number, y: Number, radius: Number): Boolean =
    radius.f.let { radius ->
        contains(
            x.f - radius,
            y.f - radius,
            radius * 2,
            radius * 2
        )
    }

fun ERect.intersects(other: ERect) = intersects(
    top = other.top,
    left = other.left,
    right = other.right,
    bottom = other.bottom
)

fun ERect.intersects(
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
fun ERect.contains(x: Number, y: Number) = contains(x, y, 0, 0)

fun ERect.contains(p: EPoint) = contains(p.x, p.y)

