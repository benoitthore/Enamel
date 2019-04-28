package com.thorebenoit.enamel.geometry.primitives

import com.thorebenoit.enamel.core.math.*
import com.thorebenoit.enamel.geometry.Resetable
import com.thorebenoit.enamel.geometry.allocateDebugMessage
import com.thorebenoit.enamel.geometry.figures.ECircle

//TODO Mark warning if not used on non-self functions in points/rect/circle/etc
open class EPoint(open val x: Float = 0f, open val y: Float = 0f) : Tuple2 {
    override val v1: Number get() = x
    override val v2: Number get() = y

    companion object {
        val inv = EPoint(-1f, -1f)
        val zero = EPoint(0f, 0f)
        val half = EPoint(0.5f, 0.5f)
        val unit = EPoint(1f, 1f)
    }

    init {
        allocateDebugMessage()
    }

    constructor(x: Number, y: Number) : this(x.f, y.f)
    constructor(angle: EAngle, magnitude: Number) : this(angle.cos * magnitude.f, angle.sin * magnitude.f)

    fun toMutable(buffer: EPointMutable = EPointMutable()) = buffer.set(x, y)
    fun toImmutable() = EPoint(x, y)
    fun copy(buffer: EPointMutable = EPointMutable()) = buffer.set(x, y)


    open val magnitude get() = Math.hypot(x.d, y.d)

    fun heading(buffer: EAngleMutable = EAngleMutable()) =
        buffer.set(Math.atan2(y.toDouble(), x.toDouble()), AngleType.RADIAN)

    fun angleTo(point: EPoint): EAngleMutable =
        Math.atan2(
            ((point.y - y).d), ((point.x - x).d)
        ).radians()

    fun distanceTo(o: EPoint) = this.distanceTo(o.x, o.y)
    fun distanceTo(x2: Number, y2: Number) = Math.hypot((x2.d - x), (y2.d - y)).f

    fun distanceTo(circle: ECircle): Float {
        val distance = distanceTo(circle.center) - circle.radius
        if (distance < 0f) {
            return 0f
        }
        return distance
    }

    override fun toString(): String {
        return "($x ; $y)"
    }

    override fun equals(other: Any?): Boolean = (other as? EPoint)?.let { it.x == x && it.y == y } ?: false

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


    fun inverse(buffer: EPointMutable = EPointMutable()) = buffer.set(-x, -y)

    fun offset(x: Number, y: Number, buffer: EPointMutable = EPointMutable()) = buffer.set(this.x + x.f, this.y + y.f)
    fun offset(n: Number, buffer: EPointMutable = EPointMutable()) = offset(n, n, buffer)
    fun offset(other: Tuple2, buffer: EPointMutable = EPointMutable()) = offset(other.v1, other.v2, buffer)

    fun sub(x: Number, y: Number, buffer: EPointMutable = EPointMutable()) = buffer.set(this.x - x.f, this.y - y.f)
    fun sub(n: Number, buffer: EPointMutable = EPointMutable()) = sub(n, n, buffer)
    fun sub(other: Tuple2, buffer: EPointMutable = EPointMutable()) = sub(other.v1, other.v2, buffer)

    fun mult(x: Number, y: Number, buffer: EPointMutable = EPointMutable()) = buffer.set(this.x * x.f, this.y * y.f)
    fun mult(n: Number, buffer: EPointMutable = EPointMutable()) = mult(n, n, buffer)
    fun mult(other: Tuple2, buffer: EPointMutable = EPointMutable()) = mult(other.v1, other.v2, buffer)

    fun dividedBy(x: Number, y: Number, buffer: EPointMutable = EPointMutable()) =
        buffer.set(this.x / x.f, this.y / y.f)

    fun dividedBy(n: Number, buffer: EPointMutable = EPointMutable()) = dividedBy(n, n, buffer)
    fun dividedBy(other: Tuple2, buffer: EPointMutable = EPointMutable()) = dividedBy(other.v1, other.v2, buffer)


    fun offsetTowards(towards: EPoint, distance: Number, buffer: EPointMutable = EPointMutable()): EPointMutable {
        val fromX = x
        val fromY = y
        buffer.set(angle = angleTo(towards), magnitude = distance)
        return buffer.set(buffer.x + fromX, buffer.y + fromY)
    }

    fun offsetFrom(from: EPoint, distance: Number, buffer: EPointMutable = EPointMutable()) =
        from.offsetTowards(this, distance, buffer)


    fun offsetAngle(angle: EAngle, distance: Number, buffer: EPointMutable = EPointMutable()): EPointMutable {
        val fromX = x
        val fromY = y
        buffer.set(angle, distance)
        return buffer.set(buffer.x + fromX, buffer.y + fromY)
    }

    fun rotateAround(angle: EAngle, center: EPoint, buffer: EPointMutable = EPointMutable()): EPointMutable {
        val angleTo = center.angleTo(this)
        val distance = center.distanceTo(this)
        val totalAngle = angle + angleTo
        val x = center.x + totalAngle.cos * distance
        val y = center.y + totalAngle.sin * distance
        return buffer.set(x, y)
    }

    fun normalize(buffer: EPointMutable = EPointMutable()): EPointMutable {
        val magnitude = magnitude.f
        buffer.set(this)
        if (magnitude != 0f) {
            buffer.selfDividedBy(magnitude)
        }
        return buffer
    }

    fun limitMagnitude(max: Number, buffer: EPointMutable = EPointMutable()): EPointMutable {
        val max = max.f
        buffer.set(this)

        if (buffer.magnitude > max) {
            buffer.selfNormalize().selfMult(max)
        }

        return buffer
    }

    fun setMagnitude(magnitude: Number, buffer: EPointMutable = EPointMutable()) =
        buffer.set(this).selfNormalize().selfMult(magnitude)

    fun abs(buffer: EPointMutable = EPointMutable()) = buffer.also {
        buffer.set(Math.abs(x), Math.abs(y))
    }


}


class EPointMutable(override var x: Float = 0f, override var y: Float = 0f) : EPoint(x, y), Resetable {


    constructor(x: Number, y: Number) : this(x.f, y.f)
    constructor(angle: EAngleMutable, magnitude: Number) : this(angle.cos * magnitude.f, angle.sin * magnitude.f)

    companion object {
        val zero get() = EPointMutable(0f, 0f)
        val half get() = EPointMutable(0.5f, 0.5f)
        val unit get() = EPointMutable(1f, 1f)
    }

    fun set(x: Number, y: Number) = apply { this.x = x.f; this.y = y.f }

    fun set(other: Tuple2) = set(other.v1, other.v2)

    fun set(angle: EAngle, magnitude: Number) =
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

    fun selfMult(x: Number, y: Number) = mult(x, y, this)
    fun selfMult(n: Number) = mult(n, this)
    fun selfMult(other: Tuple2) = mult(other, this)

    fun selfDividedBy(x: Number, y: Number) = dividedBy(x, y, this)
    fun selfDividedBy(n: Number) = dividedBy(n, this)
    fun selfDividedBy(other: Tuple2) = dividedBy(other, this)


    fun selfOffsetTowards(towards: EPoint, distance: Number) = offsetTowards(towards, distance, this)
    fun selfOffsetFrom(from: EPoint, distance: Number) = offsetFrom(from, distance, this)
    fun selfOffsetAngle(angle: EAngle, distance: Number) = offsetAngle(angle, distance)

    fun selfRotateAround(angle: EAngle, center: EPointMutable) = rotateAround(angle, center, this)

    fun selfInverse() = inverse(this)

    fun selfNormalize() = normalize(this)
    fun selfLimitMagnitude(max: Number) = limitMagnitude(max, this)
    fun selfSetMagnitude(magnitude: Number) = setMagnitude(magnitude, this)

}


fun RandomPoint(magnitude: Number = 1f, buffer: EPointMutable = EPointMutable()) =
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

infix fun Number.point(other: Number): EPointMutable =
    EPointMutable(this, other)