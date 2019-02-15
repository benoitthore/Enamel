package com.thorebenoit.enamel.kotlin.geometry.primitives

import com.thorebenoit.enamel.kotlin.core.Resetable
import com.thorebenoit.enamel.kotlin.core.math.d
import com.thorebenoit.enamel.kotlin.core.math.f
import com.thorebenoit.enamel.kotlin.core.math.random
import com.thorebenoit.enamel.kotlin.core.math.randomSign
import com.thorebenoit.enamel.kotlin.geometry.allocateDebugMessage
import com.thorebenoit.enamel.kotlin.geometry.figures.ECircleType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import jdk.nashorn.internal.objects.Global
import java.lang.Exception

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

    fun sub(x: Number, y: Number, buffer: EPoint = EPoint()) = buffer.set(this.x - x.f, this.y - y.f)
    fun sub(n: Number, buffer: EPoint = EPoint()) = sub(n, n, buffer)
    fun sub(other: Tuple2, buffer: EPoint = EPoint()) = sub(other.v1, other.v2, buffer)


    fun offsetTowards(towards: EPointType, distance: Number, buffer: EPoint = EPoint()): EPoint {
        val fromX = x
        val fromY = y
        buffer.set(angle = angleTo(towards), magnitude = distance)
        return buffer.set(buffer.x + fromX, buffer.y + fromY)
    }

    fun offsetFrom(from: EPointType, distance: Number, buffer: EPoint = EPoint()) =
        from.offsetTowards(this, distance, buffer)


    fun mult(x: Number, y: Number, buffer: EPoint = EPoint()) = buffer.set(this.x * x.f, this.y * y.f)
    fun mult(n: Number, buffer: EPoint = EPoint()) = mult(n, n, buffer)
    fun mult(other: Tuple2, buffer: EPoint = EPoint()) = mult(other.v1, other.v2, buffer)

    fun divide(x: Number, y: Number, buffer: EPoint = EPoint()) = buffer.set(this.x / x.f, this.y / y.f)
    fun divide(n: Number, buffer: EPoint = EPoint()) = divide(n, n, buffer)
    fun divide(other: Tuple2, buffer: EPoint = EPoint()) = divide(other.v1, other.v2, buffer)


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

    fun selfSub(x: Number, y: Number) = sub(x, y, this)
    fun selfSub(n: Number) = sub(n, this)
    fun selfSub(other: Tuple2) = sub(other, this)


    fun selfOffsetTowards(towards: EPointType, distance: Number) = offsetTowards(towards, distance, this)
    fun selfOffsetFrom(from: EPointType, distance: Number) = offsetFrom(from, distance, this)


    fun selfMult(x: Number, y: Number) = mult(x, y, this)
    fun selfMult(n: Number) = mult(n, this)
    fun selfMult(other: Tuple2) = mult(other, this)

    fun selfDiv(x: Number, y: Number) = divide(x, y, this)
    fun selfDiv(n: Number) = divide(n, this)
    fun selfDiv(other: Tuple2) = divide(other, this)


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

interface Tuple2 {
    val v1: Number
    val v2: Number
}


inline operator fun EPointType.unaryMinus() = EPointType(-x, -y)

inline operator fun EPointType.div(other: Tuple2) = divide(other)
inline operator fun EPointType.div(n: Number) = divide(n)

inline operator fun EPointType.times(other: Tuple2) = mult(other)
inline operator fun EPointType.times(n: Number) = mult(n)

inline operator fun EPointType.plus(other: Tuple2) = offset(other)
inline operator fun EPointType.plus(n: Number) = offset(n)

inline operator fun EPointType.minus(other: Tuple2) = sub(other)
inline operator fun EPointType.minus(n: Number) = sub(n)


inline operator fun Number.times(p: EPointType) = p.mult(this)
inline operator fun Number.plus(p: EPointType) = p.offset(this)
/////////////////////////
/////////////////////////
/////////////////////////

infix fun Number.point(other: Number): EPoint =
    EPoint(this, other)