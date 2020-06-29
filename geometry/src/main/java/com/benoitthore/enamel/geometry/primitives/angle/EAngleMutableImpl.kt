package com.benoitthore.enamel.geometry.primitives.angle

import com.benoitthore.enamel.core.math.i
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.primitives.angle.EAngle.*

internal class EAngleMutableImpl internal constructor(value: Number, override var type: AngleType) :
    EAngleMutable {
    override var value: Float = value.toFloat()

    override fun toMutable(): EAngleMutable = E.AngleMutable(value, type)

    override fun toImmutable(): EAngle = toMutable()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        if (other !is EAngleMutable) return false

        if (type != other.type) return false
        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + value.hashCode()
        return result
    }

    override fun toString(): String {
        return "${degrees.i}°"
    }

}