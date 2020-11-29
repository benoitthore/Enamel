package com.benoitthore.enamel.geometry.figures.circle

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.allocateDebugMessage
import com.benoitthore.enamel.geometry.builders.*
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable
import kotlin.math.min

internal class ECircleImpl internal constructor(
    centerX: Number,
    centerY: Number,
    radius: Number
) : ECircleMutable {

    init {
        allocateDebugMessage()
    }

    override var radius: Float = radius.toFloat()
    override var left: Float
        get() = center.x - radius
        set(value) {
            radius += (left - value) / 2f
        }
    override var top: Float
        get() = center.y - radius
        set(value) {
            radius += (top - value) / 2f
        }
    override var right: Float
        get() = center.x + radius
        set(value) {
            radius -= (right - value) / 2f
        }
    override var bottom: Float
        get() = center.y + radius
        set(value) {
            radius -= (bottom - value) / 2f
        }
    override var originX: Float
        get() = left
        set(value) {
            left = value
        }
    override var originY: Float
        get() = top
        set(value) {
            top = value
        }

    override var width: Float
        get() = radius * 2
        set(value) {
            radius = value / 2f
        }
    override var height: Float
        get() = radius * 2
        set(value) {
            radius = value / 2f
        }
    override var centerX: Float
        get() = center.x
        set(value) {
            center.x = value
        }
    override var centerY: Float
        get() = center.y
        set(value) {
            center.y = value
        }

    override val center: EPointMutable = MutablePoint(centerX, centerY)

    /**
     * In case the bounds don't define a square, the circle gets align on the center of the
     * given rectangle and sets the radius to be the half of whichever is smaller width or height
     */
    override fun _setBounds(left: Number, top: Number, right: Number, bottom: Number) {

        val left = left.f
        val top = top.f
        val right = right.f
        val bottom = bottom.f

        val width = (right - left) / 2f
        val height = (bottom - top) / 2f
        radius = min(width, height)


        center.x = (right + left) / 2f
        center.y = (bottom + top) / 2f
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        if (other !is ECircleImpl) return false

        if (center != other.center) return false
        if (radius != other.radius) return false

        return true
    }

    override fun hashCode(): Int {
        var result = center.hashCode()
        result = 31 * result + radius.hashCode()
        return result
    }

    override fun toString(): String {
        return "Circle(center=$center, radius=$radius)"
    }


}