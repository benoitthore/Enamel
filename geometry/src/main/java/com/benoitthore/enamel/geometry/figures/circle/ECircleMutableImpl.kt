package com.benoitthore.enamel.geometry.figures.circle

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.allocateDebugMessage
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable
import kotlin.math.min

internal class ECircleMutableImpl internal constructor(
    centerX: Number,
    centerY: Number,
    radius: Number
) : ECircleMutable {

    init {
        allocateDebugMessage()
    }

    override var radius: Float = radius.toFloat()
    override val left: Float
        get() = centerX - radius
    override val top: Float
        get() = centerY - radius
    override val right: Float
        get() = centerX + radius
    override val bottom: Float
        get() = centerY + radius

    override var width: Float
        get() = radius * 2
        set(value) {
            TODO()
        }
    override var height: Float
        get() = radius * 2
        set(value) {
            TODO()
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


    override val center: EPointMutable = E.PointMutable(centerX, centerY)

    override fun toMutable(): ECircleMutable = E.CircleMutable(this)

    override fun toImmutable(): ECircle = E.Circle(this)

    /**
     * In case the bounds don't define a square, the circle gets align on the top left of the
     * given rectangle and sets the radius to be the half of whichever is smaller width or height
     */
    override fun setBounds(left: Number, top: Number, right: Number, bottom: Number) {
        val w = (right.f - left.f) / 2f
        val h = (bottom.f - top.f) / 2f
        radius = min(w, h)

        center.x = left.f + radius
        center.y = top.f + radius
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        if (other !is ECircleMutableImpl) return false

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