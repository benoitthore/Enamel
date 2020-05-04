package com.benoitthore.enamel.geometry.primitives

import com.benoitthore.enamel.core.math.d
import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.Allocates
import com.benoitthore.enamel.geometry.Resetable
import com.benoitthore.enamel.geometry.allocateDebugMessage
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.builders.E
import kotlin.math.*

interface EPoint : Tuple2 {
    val x: Float
    val y: Float
    override val v1: Number
        get() = x
    override val v2: Number
        get() = y

    fun copy(x: Number = this.x, y: Number = this.y, target: EPointMutable = E.PointMutable()) =
        target.set(x, y)

    val magnitude: Number
        get() = hypot(x.d, y.d)

    fun heading(target: EAngleMutable = E.AngleMutable()) =
        target.set(atan2(y.toDouble(), x.toDouble()), AngleType.RADIAN)

    fun angleTo(x: Number, y: Number, target: EAngleMutable = E.AngleMutable()): EAngleMutable =
        _angleTo(x, y).radians(target)


    fun angleTo(point: EPoint, target: EAngleMutable = E.AngleMutable()): EAngleMutable =
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

    fun inverse(target: EPointMutable = E.PointMutable()) = target.set(-x, -y)

    fun offset(x: Number, y: Number, target: EPointMutable = E.PointMutable()) =
        target.set(this.x + x.f, this.y + y.f)

    fun offset(n: Number, target: EPointMutable = E.PointMutable()) = offset(n, n, target)
    fun offset(other: Tuple2, target: EPointMutable = E.PointMutable()) =
        offset(other.v1, other.v2, target)

    fun sub(x: Number, y: Number, target: EPointMutable = E.PointMutable()) =
        target.set(this.x - x.f, this.y - y.f)

    fun sub(n: Number, target: EPointMutable = E.PointMutable()) = sub(n, n, target)
    fun sub(other: Tuple2, target: EPointMutable = E.PointMutable()) =
        sub(other.v1, other.v2, target)

    fun mult(x: Number, y: Number, target: EPointMutable = E.PointMutable()) =
        target.set(this.x * x.f, this.y * y.f)

    fun mult(n: Number, target: EPointMutable = E.PointMutable()) = mult(n, n, target)
    fun mult(other: Tuple2, target: EPointMutable = E.PointMutable()) =
        mult(other.v1, other.v2, target)

    fun dividedBy(x: Number, y: Number, target: EPointMutable = E.PointMutable()) =
        target.set(this.x / x.f, this.y / y.f)

    fun dividedBy(n: Number, target: EPointMutable = E.PointMutable()) = dividedBy(n, n, target)
    fun dividedBy(other: Tuple2, target: EPointMutable = E.PointMutable()) =
        dividedBy(other.v1, other.v2, target)

    fun offsetTowards(
        towardsX: Number,
        towardsY: Number,
        distance: Number,
        target: EPointMutable = E.PointMutable()
    ): EPointMutable {
        val fromX = x
        val fromY = y
        target._set(angle = _angleTo(towardsX, towardsY), magnitude = distance)
        return target.set(target.x + fromX, target.y + fromY)
    }

    fun offsetTowards(
        towards: EPoint,
        distance: Number,
        target: EPointMutable = E.PointMutable()
    ): EPointMutable = offsetTowards(towards.x, towards.y, distance, target)


    fun offsetFrom(from: EPoint, distance: Number, target: EPointMutable = E.PointMutable()) =
        from.offsetTowards(this, distance, target)

    fun offsetAngle(
        angle: EAngle,
        distance: Number,
        target: EPointMutable = E.PointMutable()
    ): EPointMutable = _offsetAngle(angle.radians, distance, target)

    @Allocates
    fun rotateAround(
        angle: EAngle,
        center: EPoint,
        target: EPointMutable = E.PointMutable()
    ): EPointMutable {
        val angleTo = center.angleTo(this)
        val distance = center.distanceTo(this)
        val totalAngle = angle + angleTo
        val x = center.x + totalAngle.cos * distance
        val y = center.y + totalAngle.sin * distance
        return target.set(x, y)
    }

    fun normalize(target: EPointMutable = E.PointMutable()): EPointMutable {
        val magnitude = magnitude.f
        target.set(this)
        if (magnitude != 0f) {
            target.selfDividedBy(magnitude)
        }
        return target
    }

    fun limitMagnitude(max: Number, target: EPointMutable = E.PointMutable()): EPointMutable {
        val max = max.f
        target.set(this)

        if (target.magnitude.d > max) {
            target.selfNormalize().selfMult(max)
        }

        return target
    }

    fun setMagnitude(magnitude: Number, target: EPointMutable = E.PointMutable()) =
        target.set(this).selfNormalize().selfMult(magnitude)

    fun abs(target: EPointMutable = E.PointMutable()) = target.also {
        target.set(abs(x), abs(y))
    }


}

interface EPointMutable : EPoint, Resetable {
    class Impl internal constructor(x: Number, y: Number) : EPointMutable {
        override var x: Float = x.toFloat()
        override var y: Float = y.toFloat()

        init {
            allocateDebugMessage()
        }

        override fun toString(): String {
            return "point($x ; $y)"
        }

        override fun equals(other: Any?): Boolean =
            (other as? EPoint)?.let { it.x == x && it.y == y } ?: false

        override fun hashCode(): Int {
            var result = x.hashCode()
            result = 31 * result + y.hashCode()
            return result
        }
    }

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
    fun selfLimitMagnitude(max: Number) = limitMagnitude(max, this)
    fun selfSetMagnitude(magnitude: Number) = setMagnitude(magnitude, this)

}

internal fun EPoint._angleTo(x: Number, y: Number): Double =
    atan2(
        ((y.f - this.y).d), ((x.f - this.x).d)
    )

internal fun EPoint._offsetAngle(
    angleRadians: Number,
    distance: Number,
    target: EPointMutable = E.PointMutable()
): EPointMutable {
    val fromX = x
    val fromY = y
    target._set(angleRadians, distance)
    return target.set(target.x + fromX, target.y + fromY)
}

// Used to set angle without having to allocate one
internal fun EPointMutable._set(angle: Number, magnitude: Number) =
    set(cos(angle.toFloat()) * magnitude.f, sin(angle.toFloat()) * magnitude.f)


/////////////////////////
/////////////////////////
/////////////////////////

/////////////////////////
/////////////////////////
/////////////////////////

infix fun Number.point(other: Number): EPointMutable =
    E.PointMutable(this, other)


val List<EPoint>.length: Float
    get() {
        var last: EPoint? = null
        var total = 0f
        forEach { p ->
            last?.let { last ->
                total += last.distanceTo(p)
            }
            last = p
        }
        return total
    }