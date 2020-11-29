package com.benoitthore.enamel.geometry.primitives.point

import com.benoitthore.enamel.core.math.d
import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.Allocates
import com.benoitthore.enamel.geometry.builders.*
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.primitives.Tuple2
import com.benoitthore.enamel.geometry.primitives.angle.EAngle
import com.benoitthore.enamel.geometry.primitives.angle.EAngle.AngleType
import com.benoitthore.enamel.geometry.primitives.angle.radians
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.hypot

interface EPoint : Tuple2 {
    var x: Float
    var y: Float

    override val v1: Number
        get() = x
    override val v2: Number
        get() = y

    fun copy(): EPoint = Point(this)

    fun copy(x: Number = this.x, y: Number = this.y, target: EPoint = Point()) =
        target.set(x, y)

    var magnitude: Float
        get() = hypot(x.d, y.d).toFloat()
        set(value) {
            selfSetMagnitude(value)
        }


    fun heading(target: EAngle = Angle()) =
        target.set(atan2(y.toDouble(), x.toDouble()), AngleType.RADIAN)

    fun angleTo(x: Number, y: Number, target: EAngle = Angle()): EAngle =
        _angleTo(x, y).radians(target)


    fun angleTo(point: EPoint, target: EAngle = Angle()): EAngle =
        angleTo(point.x, point.y, target)

    fun distanceTo(o: EPoint) = this.distanceTo(o.x, o.y)
    fun distanceTo(x2: Number, y2: Number) = hypot((x2.d - x), (y2.d - y)).f

    fun distanceTo(circle: ECircle): Float {
        val distance = distanceTo(circle.center) - circle.radius
        if (distance < 0f) {
            return 0f
        }
        return distance
    }

    operator fun component1() = x
    operator fun component2() = y

    //////
    //////
    //////

    fun inverse(target: EPoint = Point()) = target.set(-x, -y)

    fun offset(x: Number, y: Number, target: EPoint = Point()) =
        target.set(this.x + x.f, this.y + y.f)

    fun offset(n: Number, target: EPoint = Point()) = offset(n, n, target)
    fun offset(other: Tuple2, target: EPoint = Point()) =
        offset(other.v1, other.v2, target)

    fun sub(x: Number, y: Number, target: EPoint = Point()) =
        target.set(this.x - x.f, this.y - y.f)

    fun sub(n: Number, target: EPoint = Point()) = sub(n, n, target)
    fun sub(other: Tuple2, target: EPoint = Point()) =
        sub(other.v1, other.v2, target)

    fun mult(x: Number, y: Number, target: EPoint = Point()) =
        target.set(this.x * x.f, this.y * y.f)

    fun mult(n: Number, target: EPoint = Point()) = mult(n, n, target)
    fun mult(other: Tuple2, target: EPoint = Point()) =
        mult(other.v1, other.v2, target)

    fun dividedBy(x: Number, y: Number, target: EPoint = Point()) =
        target.set(this.x / x.f, this.y / y.f)

    fun dividedBy(n: Number, target: EPoint = Point()) = dividedBy(n, n, target)
    fun dividedBy(other: Tuple2, target: EPoint = Point()) =
        dividedBy(other.v1, other.v2, target)

    fun offsetTowards(
        towardsX: Number,
        towardsY: Number,
        distance: Number,
        target: EPoint = Point()
    ): EPoint {
        val fromX = x
        val fromY = y
        target._set(angle = _angleTo(towardsX, towardsY), magnitude = distance)
        return target.set(target.x + fromX, target.y + fromY)
    }

    fun offsetTowards(
        towards: EPoint,
        distance: Number,
        target: EPoint = Point()
    ): EPoint = offsetTowards(towards.x, towards.y, distance, target)


    fun offsetFrom(from: EPoint, distance: Number, target: EPoint = Point()) =
        from.offsetTowards(this, distance, target)

    fun offsetAngle(
        angle: EAngle,
        distance: Number,
        target: EPoint = Point()
    ): EPoint = _offsetAngle(angle.radians, distance, target)

    @Allocates
    fun rotateAround(
        angle: EAngle,
        center: EPoint,
        target: EPoint = Point()
    ): EPoint {
        val angleTo = center.angleTo(this)
        val distance = center.distanceTo(this)
        val totalAngle = angle + angleTo
        val x = center.x + totalAngle.cos * distance
        val y = center.y + totalAngle.sin * distance
        return target.set(x, y)
    }

    fun normalize(target: EPoint = Point()): EPoint {
        val magnitude = magnitude.f
        target.set(this)
        if (magnitude != 0f) {
            target.selfDividedBy(magnitude)
        }
        return target
    }

    fun normalizeIn(frame: ERect, target: EPoint = Point()): EPoint {
        target.set(this)
        target.x /= frame.size.width
        target.y /= frame.size.height
        return target
    }

    fun limitMagnitude(max: Number, target: EPoint = Point()): EPoint {
        val max = max.f
        target.set(this)

        if (target.magnitude.d > max) {
            target.selfNormalize().selfMult(max)
        }

        return target
    }

    fun setMagnitude(magnitude: Number, target: EPoint = Point()) =
        target.set(this).selfNormalize().selfMult(magnitude)

    fun abs(target: EPoint = Point()) = target.also {
        target.set(abs(x), abs(y))
    }
    
    
    ///////////////

    fun set(other: EPoint) = apply { this.x = other.x; this.y = other.y }
    fun set(x: Number, y: Number) = apply { this.x = x.f; this.y = y.f }

    fun set(other: Tuple2) = set(other.v1, other.v2)

    fun set(angle: EAngle, magnitude: Number) =
        set(angle.cos * magnitude.f, angle.sin * magnitude.f)


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

    fun selfRotateAround(angle: EAngle, center: EPoint) = rotateAround(angle, center, this)

    fun selfInverse() = inverse(this)

    fun selfNormalize() = normalize(this)
    fun selfNormalizeIn(frame: ERect) = normalizeIn(frame, this)
    fun selfLimitMagnitude(max: Number) = limitMagnitude(max, this)
    fun selfSetMagnitude(magnitude: Number) = setMagnitude(magnitude, this)


}
