package com.benoitthore.enamel.geometry.primitives.point

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.primitives.Tuple2
import com.benoitthore.enamel.geometry.primitives.angle.EAngle

interface EPointMutable : EPoint {

    override var x: Float
    override var y: Float
    override var magnitude: Float
        get() = super.magnitude
        set(value) {
            selfSetMagnitude(value)
        }

    ///////////////

    fun set(other: EPoint) = apply { this.x = other.x; this.y = other.y }
    fun set(x: Number, y: Number) = apply { this.x = x.f; this.y = y.f }

    fun set(other: Tuple2) = set(other.v1, other.v2)

    fun set(angle: EAngle, magnitude: Number) =
        set(angle.cos * magnitude.f, angle.sin * magnitude.f)


    /////

    fun selfOffset(x: Number, y: Number) = apply {
        offset(x, y, this)
    }

    fun selfOffset(n: Number) = apply {
        offset(n, this)
    }

    fun selfOffset(other: Tuple2) = apply {
        offset(other, this)
    }

    fun selfSub(x: Number, y: Number) = apply {
        sub(x, y, this)
    }

    fun selfSub(n: Number) = apply {
        sub(n, this)
    }

    fun selfSub(other: Tuple2) = apply {
        sub(other, this)
    }

    fun selfMult(x: Number, y: Number) = apply {
        mult(x, y, this)
    }

    fun selfMult(n: Number) = apply {
        mult(n, this)
    }

    fun selfMult(other: Tuple2) = apply {
        mult(other, this)
    }

    fun selfDividedBy(x: Number, y: Number) = apply {
        dividedBy(x, y, this)
    }

    fun selfDividedBy(n: Number) = apply {
        dividedBy(n, this)
    }

    fun selfDividedBy(other: Tuple2) = apply {
        dividedBy(other, this)
    }

    fun selfOffsetTowards(towards: EPoint, distance: Number) = apply {
        offsetTowards(towards, distance, this)
    }

    fun selfOffsetTowards(
        towardsX: Number,
        towardsY: Number, distance: Number
    ) = apply {
        offsetTowards(towardsX, towardsY, distance, this)
    }


    fun selfOffsetFrom(from: EPoint, distance: Number) = apply {
        offsetFrom(from, distance, this)
    }

    fun selfOffsetAngle(angle: EAngle, distance: Number) = apply {
        offsetAngle(angle, distance, this)
    }

    fun selfRotateAround(angle: EAngle, center: EPoint) = apply {
        rotateAround(angle, center, this)
    }

    fun selfInverse() = apply {
        inverse(this)
    }

    fun selfNormalize() = apply {
        normalize(this)
    }

    fun selfNormalizeIn(frame: ERect) = apply {
        normalizeIn(frame, this)
    }

    fun selfLimitMagnitude(max: Number) = apply {
        limitMagnitude(max, this)
    }

    fun selfSetMagnitude(magnitude: Number) = apply {
        setMagnitude(magnitude, this)
    }


}