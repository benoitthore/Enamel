package com.benoitthore.enamel.geometry.figures.circle

import com.benoitthore.enamel.geometry.allocateDebugMessage
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable

internal class ECircleMutableImpl internal constructor(
    centerX: Number,
    centerY: Number,
    radius: Number
) :
    ECircleMutable {
    init {
        allocateDebugMessage()
    }

    override val center: EPointMutable = E.PointMutable(centerX, centerY)
    override var radius: Float = radius.toFloat()

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