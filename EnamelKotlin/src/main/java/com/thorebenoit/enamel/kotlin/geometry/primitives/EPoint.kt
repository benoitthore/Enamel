package com.thorebenoit.enamel.kotlin.geometry.primitives

import com.thorebenoit.enamel.kotlin.core.Resetable
import com.thorebenoit.enamel.kotlin.core.math.d
import com.thorebenoit.enamel.kotlin.core.math.f
import com.thorebenoit.enamel.kotlin.core.math.random
import com.thorebenoit.enamel.kotlin.core.math.randomSign
import com.thorebenoit.enamel.kotlin.geometry.allocateDebugMessage
import com.thorebenoit.enamel.kotlin.geometry.figures.ECircleType

//TODO Mark warning if not used on non-self functions in points/rect/circle/etc
open class EPointType(open val x: Float = 0f, open val y: Float = 0f) : Tuple2 {
    override val v1: Number get() = x
    override val v2: Number get() = y

    companion object {
        val inv = EPointType(-1f, -1f)
        val zero = EPointType(0f, 0f)
        val half = EPointType(0.5f, 0.5f)
        val unit = EPointType(1f, 1f)
    }

    init {
        allocateDebugMessage()
    }

    constructor(x: Number, y: Number) : this(x.f, y.f)
    constructor(angle: EAngleType, magnitude: Number) : this(angle.cos * magnitude.f, angle.sin * magnitude.f)

    fun toMutable(buffer: EPoint = EPoint()) = buffer.set(x, y)
    fun toImmutable() = EPointType(x, y)
    fun copy(buffer: EPoint = EPoint()) = buffer.set(x, y)


    open val magnitude get() = Math.hypot(x.d, y.d)

    fun heading(buffer: EAngle = EAngle()) = buffer.set(Math.atan2(y.toDouble(), x.toDouble()), AngleType.RADIAN)

    fun angleTo(point: EPointType): EAngle =
        Math.atan2(
            ((point.y - y).d), ((point.x - x).d)
        ).radians()

    fun distanceTo(o: EPointType) = this.distanceTo(o.x, o.y)
    fun distanceTo(x2: Number, y2: Number) = Math.hypot((x2.d - x), (y2.d - y)).f

    fun distanceTo(circle: ECircleType): Float {
        val distance = distanceTo(circle.center) - circle.radius
        if (distance < 0f) {
            return 0f
        }
        return distance
    }

    override fun toString(): String {
        return "($x ; $y)"
    }

    override fun equals(other: Any?): Boolean = (other as? EPointType)?.let { it.x == x && it.y == y } ?: false

    operator fun component1() = x
    operator fun component2() = y

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }


    //////
    //////
    //////


    fun inverse(buffer: EPoint = EPoint()) = buffer.set(-x, -y)

    fun offset(x: Number, y: Number, buffer: EPoint = EPoint()) = buffer.set(this.x + x.f, this.y + y.f)
    fun offset(n: Number, buffer: EPoint = EPoint()) = offset(n, n, buffer)
    fun offset(other: Tuple2, buffer: EPoint = EPoint()) = offset(other.v1, other.v2, buffer)

    fun minus(x: Number, y: Number, buffer: EPoint = EPoint()) = buffer.set(this.x - x.f, this.y - y.f)
    fun minus(n: Number, buffer: EPoint = EPoint()) = minus(n, n, buffer)
    fun minus(other: Tuple2, buffer: EPoint = EPoint()) = minus(other.v1, other.v2, buffer)

    fun times(x: Number, y: Number, buffer: EPoint = EPoint()) = buffer.set(this.x * x.f, this.y * y.f)
    fun times(n: Number, buffer: EPoint = EPoint()) = times(n, n, buffer)
    fun times(other: Tuple2, buffer: EPoint = EPoint()) = times(other.v1, other.v2, buffer)

    fun div(x: Number, y: Number, buffer: EPoint = EPoint()) = buffer.set(this.x / x.f, this.y / y.f)
    fun div(n: Number, buffer: EPoint = EPoint()) = div(n, n, buffer)
    fun div(other: Tuple2, buffer: EPoint = EPoint()) = div(other.v1, other.v2, buffer)


    fun offsetTowards(towards: EPointType, distance: Number, buffer: EPoint = EPoint()): EPoint {
        val fromX = x
        val fromY = y
        buffer.set(angle = angleTo(towards), magnitude = distance)
        return buffer.set(buffer.x + fromX, buffer.y + fromY)
    }

    fun offsetFrom(from: EPointType, distance: Number, buffer: EPoint = EPoint()) =
        from.offsetTowards(this, distance, buffer)




    fun offsetAngle(angle: EAngleType, distance: Number, buffer: EPoint = EPoint()): EPoint {
        val fromX = x
        val fromY = y
        buffer.set(angle, distance)
        return buffer.set(buffer.x + fromX, buffer.y + fromY)
    }

    fun rotateAround(angle: EAngleType, center: EPointType, buffer: EPoint = EPoint()): EPoint {
        val angleTo = center.angleTo(this)
        val distance = center.distanceTo(this)
        val totalAngle = angle + angleTo
        val x = center.x + totalAngle.cos * distance
        val y = center.y + totalAngle.sin * distance
        return buffer.set(x, y)
    }

    fun normalize(buffer: EPoint = EPoint()): EPoint {
        val magnitude = magnitude.f
        buffer.set(this)
        if (magnitude != 0f) {
            buffer.selfDiv(magnitude)
        }
        return buffer
    }

    fun limitMagnitude(max: Number, buffer: EPoint = EPoint()): EPoint {
        val max = max.f
        buffer.set(this)

        if (buffer.magnitude > max) {
            buffer.selfNormalize().selfMult(max)
        }

        return buffer
    }

    fun setMagnitude(magnitude: Number, buffer: EPoint = EPoint()) =
        buffer.set(this).selfNormalize().selfMult(magnitude)


}


class EPoint(override var x: Float = 0f, override var y: Float = 0f) : EPointType(x, y), Resetable {


    constructor(x: Number, y: Number) : this(x.f, y.f)
    constructor(angle: EAngle, magnitude: Number) : this(angle.cos * magnitude.f, angle.sin * magnitude.f)

    companion object {
        val zero get() = EPoint(0f, 0f)
        val half get() = EPoint(0.5f, 0.5f)
        val unit get() = EPoint(1f, 1f)
    }

    fun set(x: Number, y: Number) = apply { this.x = x.f; this.y = y.f }

    fun set(other: Tuple2) = set(other.v1, other.v2)

    fun set(angle: EAngleType, magnitude: Number) =
        set(angle.cos * magnitude.f, angle.sin * magnitude.f)

    override var magnitude: Double
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

    fun selfMinus(x: Number, y: Number) = minus(x, y, this)
    fun selfMinus(n: Number) = minus(n, this)
    fun selfMinus(other: Tuple2) = minus(other, this)

    fun selfMult(x: Number, y: Number) = times(x, y, this)
    fun selfMult(n: Number) = times(n, this)
    fun selfMult(other: Tuple2) = times(other, this)

    fun selfDiv(x: Number, y: Number) = div(x, y, this)
    fun selfDiv(n: Number) = div(n, this)
    fun selfDiv(other: Tuple2) = div(other, this)


    fun selfOffsetTowards(towards: EPointType, distance: Number) = offsetTowards(towards, distance, this)
    fun selfOffsetFrom(from: EPointType, distance: Number) = offsetFrom(from, distance, this)
    fun selfOffsetAngle(angle: EAngleType, distance: Number) = offsetAngle(angle, distance)

    fun selfRotateAround(angle: EAngleType, center: EPoint) = rotateAround(angle, center, this)

    fun selfInverse() = inverse(this)

    fun selfNormalize() = normalize(this)
    fun selfLimitMagnitude(max: Number) = limitMagnitude(max, this)
    fun selfSetMagnitude(magnitude: Number) = setMagnitude(magnitude, this)

}


fun RandomPoint(magnitude: Number = 1f, buffer: EPoint = EPoint()) =
    buffer.set(
        x = randomSign() * random() * magnitude.f,
        y = randomSign() * random() * magnitude.f
    )
        .selfLimitMagnitude(magnitude)

/////////////////////////
/////////////////////////
/////////////////////////

/////////////////////////
/////////////////////////
/////////////////////////

infix fun Number.point(other: Number): EPoint =
    EPoint(this, other)