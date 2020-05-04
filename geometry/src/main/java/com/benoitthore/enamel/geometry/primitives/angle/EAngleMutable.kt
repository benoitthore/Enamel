package com.benoitthore.enamel.geometry.primitives.angle

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.Resetable
import com.benoitthore.enamel.geometry.primitives.angle.EAngle.*

interface EAngleMutable : EAngle,
    Resetable {

    override var value: Float
    override var type: AngleType

    fun set(other: EAngle) = set(
        other.value,
        other.type
    )

    fun set(value: Number, type: AngleType): EAngleMutable {
        this.value = value.f
        this.type = type
        return this
    }

    override fun reset() {
        set(0, AngleType.DEGREE)
    }

    fun selfInverse() = inverse(this)
    fun selfOffset(angle: EAngle) = offset(angle, this)


}