package com.benoitthore.enamel.geometry.primitives.point

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.Resetable
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.primitives.Tuple2
import com.benoitthore.enamel.geometry.primitives.angle.EAngle

interface EPointMutable : EPoint,
    Resetable {

    override var x: Float
    override var y: Float

    fun set(other: EPoint) = apply { this.x = other.x; this.y = other.y }
    fun set(x: Number, y: Number) = apply { this.x = x.f; this.y = y.f }

    fun set(other: Tuple2) = set(other.v1, other.v2)

    fun set(angle: EAngle, magnitude: Number) =
        set(angle.cos * magnitude.f, angle.sin * magnitude.f)

    override var magnitude
        get() = super.magnitude
        set(value) {
            selfSetMagnitude(value)
        }

    override fun reset() {
        set(0, 0)
    }

    /////

    fun selfOffset(x: Number, y: Number) = offset(x, y, this)
    fun selfOffset(n: Number) = offset(n, this)
    fun selfOffset(other: Tuple2) = offset(other, this)

    fun selfSub(x: Number, y: Number) = sub(x, y, this)
    fun selfSub(n: Number) = sub(n, this)
    fun selfSub(other: Tuple2) = sub(other, this)

    fun selfMult(x: Number, y: Number) = mult(x, y, this)
    fun selfMult(n: Number) = mult(n, this)
    fun selfMult(other: Tuple2) = mult(other, this)

    fun selfDividedBy(x: Number, y: Number) = dividedBy(x, y, this)
    fun selfDividedBy(n: Number) = dividedBy(n, this)
    fun selfDividedBy(other: Tuple2) = dividedBy(other, this)


    fun selfOffsetTowards(towards: EPoint, distance: Number) =
        offsetTowards(towards, distance, this)

    fun selfOffsetTowards(
        towardsX: Number,
        towardsY: Number, distance: Number
    ) =
        offsetTowards(towardsX, towardsY, distance, this)

    fun selfOffsetFrom(from: EPoint, distance: Number) = offsetFrom(from, distance, this)
    fun selfOffsetAngle(angle: EAngle, distance: Number) = offsetAngle(angle, distance, this)

    fun selfRotateAround(angle: EAngle, center: EPointMutable) = rotateAround(angle, center, this)

    fun selfInverse() = inverse(this)

    fun selfNormalize() = normalize(this)
    fun selfNormalizeIn(frame: ERect) = normalizeIn(frame, this)
    fun selfLimitMagnitude(max: Number) = limitMagnitude(max, this)
    fun selfSetMagnitude(magnitude: Number) = setMagnitude(magnitude, this)

}