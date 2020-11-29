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
import kotlin.math.*

interface EPoint : Tuple2 {
    val x: Float
    val y: Float

    override val v1: Number
        get() = x
    override val v2: Number
        get() = y

    fun copy(): EPoint = Point(this)

    fun copy(x: Number = this.x, y: Number = this.y, target: EPointMutable = MutablePoint()) =
        target.set(x, y)

    val magnitude: Float
        get() = hypot(x.d, y.d).toFloat()


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

    fun inverse(target: EPointMutable = MutablePoint()) = target.set(-x, -y)

    fun offset(x: Number, y: Number, target: EPointMutable = MutablePoint()) =
        target.set(this.x + x.f, this.y + y.f)

    fun offset(n: Number, target: EPointMutable = MutablePoint()) = offset(n, n, target)
    fun offset(other: Tuple2, target: EPointMutable = MutablePoint()) =
        offset(other.v1, other.v2, target)

    fun sub(x: Number, y: Number, target: EPointMutable = MutablePoint()) =
        target.set(this.x - x.f, this.y - y.f)

    fun sub(n: Number, target: EPointMutable = MutablePoint()) = sub(n, n, target)
    fun sub(other: Tuple2, target: EPointMutable = MutablePoint()) =
        sub(other.v1, other.v2, target)

    fun mult(x: Number, y: Number, target: EPointMutable = MutablePoint()) =
        target.set(this.x * x.f, this.y * y.f)

    fun mult(n: Number, target: EPointMutable = MutablePoint()) = mult(n, n, target)
    fun mult(other: Tuple2, target: EPointMutable = MutablePoint()) =
        mult(other.v1, other.v2, target)

    fun dividedBy(x: Number, y: Number, target: EPointMutable = MutablePoint()) =
        target.set(this.x / x.f, this.y / y.f)

    fun dividedBy(n: Number, target: EPointMutable = MutablePoint()) = dividedBy(n, n, target)
    fun dividedBy(other: Tuple2, target: EPointMutable = MutablePoint()) =
        dividedBy(other.v1, other.v2, target)

    fun offsetTowards(
        towardsX: Number,
        towardsY: Number,
        distance: Number,
        target: EPointMutable = MutablePoint()
    ): EPointMutable {
        val fromX = x
        val fromY = y
        target._set(angle = _angleTo(towardsX, towardsY), magnitude = distance)
        return target.set(target.x + fromX, target.y + fromY)
    }

    fun offsetTowards(
        towards: EPoint,
        distance: Number,
        target: EPointMutable = MutablePoint()
    ): EPointMutable = offsetTowards(towards.x, towards.y, distance, target)


    fun offsetFrom(from: EPoint, distance: Number, target: EPointMutable = MutablePoint()) =
        from.offsetTowards(this, distance, target)

    fun offsetAngle(
        angle: EAngle,
        distance: Number,
        target: EPointMutable = MutablePoint()
    ): EPointMutable = _offsetAngle(angle.radians, distance, target)

    fun rotateAround(
        angle: EAngle,
        center: EPoint,
        target: EPointMutable = MutablePoint()
    ): EPointMutable = rotateAround(angle, center.x, center.y, target)

    fun rotateAround(
        angle: EAngle,
        centerX: Number,
        centerY: Number,
        target: EPointMutable = MutablePoint()
    ): EPointMutable {
        TODO("TEST THIS")
        val centerX = centerX.toFloat()
        val centerY = centerY.toFloat()
        val angleTo = _angleTo(centerX, centerY) // might need to put a minus sign here
        val distance = distanceTo(centerX, centerY)
        val totalAngle = angle.radians + angleTo
        val x = centerX + cos(totalAngle) * distance
        val y = centerY + sin(totalAngle) * distance
        return target.set(x, y)
    }

    fun normalize(target: EPointMutable = MutablePoint()): EPointMutable {
        val magnitude = magnitude.f
        target.set(this)
        if (magnitude != 0f) {
            target.selfDividedBy(magnitude)
        }
        return target
    }

    fun normalizeIn(frame: ERect, target: EPointMutable = MutablePoint()): EPointMutable {
        target.set(this)
        target.x /= frame.size.width
        target.y /= frame.size.height
        return target
    }

    fun limitMagnitude(max: Number, target: EPointMutable = MutablePoint()): EPointMutable {
        val max = max.f
        target.set(this)

        if (target.magnitude.d > max) {
            target.selfNormalize().selfMult(max)
        }

        return target
    }

    fun setMagnitude(magnitude: Number, target: EPointMutable = MutablePoint()) =
        target.set(this).selfNormalize().selfMult(magnitude)

    fun abs(target: EPointMutable = MutablePoint()) = target.also {
        target.set(abs(x), abs(y))
    }


}
