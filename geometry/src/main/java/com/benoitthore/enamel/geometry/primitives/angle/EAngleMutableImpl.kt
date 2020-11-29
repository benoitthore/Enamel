package com.benoitthore.enamel.geometry.primitives.angle

import com.benoitthore.enamel.core.math.i
import com.benoitthore.enamel.geometry.builders.*
import com.benoitthore.enamel.geometry.primitives.angle.EAngle.*

internal class EAngleImpl internal constructor(value: Number, override var type: AngleType) :
    EAngle {
    override var value: Float = value.toFloat()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        if (other !is EAngle) return false

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