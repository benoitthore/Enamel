package com.benoitthore.enamel.geometry.e

import com.benoitthore.enamel.core.math.*
import com.benoitthore.enamel.geometry.allocate
import com.benoitthore.enamel.geometry.allocateDebugMessage
import com.benoitthore.enamel.geometry.figures.*
import com.benoitthore.enamel.geometry.primitives.*
import kotlin.math.*

/**
 * This interface can be used to create any kind of shape provided by this library
 */
object E {

    // TODO Extract to get rid of Anonymous classes
    // PRIMARY CONSTRUCTORS
    fun mpoint(x: Number = 0, y: Number = 0) = object : EPointMutable {
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

    fun mcircle(center: EPoint = mpoint(), radius: Number = 0f): ECircleMutable =
        object : ECircleMutable {
            init {
                allocateDebugMessage()
            }

            override val center: EPointMutable = center.copy()
            override var radius: Float = radius.toFloat()

            override fun equals(other: Any?): Boolean =
                (other as? ECircleMutable)?.let { other.center == this.center && other.radius == this.radius }
                    ?: false

        }

    fun msize(width: Number = 0, height: Number = 0): ESizeMutable = object : ESizeMutable {
        init {
            allocateDebugMessage()
        }

        override var width: Float = width.toFloat()
        override var height: Float = height.toFloat()

        override fun equals(other: Any?): Boolean =
            (other as? ESize)?.let { it.width == this.width && it.height == this.height } ?: false

        override fun toString(): String {
            return "size(width=$width, height=$height)"
        }

        override fun hashCode(): Int {
            var result = width.hashCode()
            result = 31 * result + height.hashCode()
            return result
        }
    }

    fun mrect(origin: EPointMutable = mpoint(), size: ESizeMutable = msize()): ERectMutable =
        object : ERectMutable {
            init {
                allocateDebugMessage()
            }

            override var origin: EPointMutable = E.mpoint(origin)
            override var size: ESizeMutable = E.msize(size)
        }

    // TODO Carry on with angle
    fun mangle(value: Number, type: AngleType) = object : EAngleMutable {
        override var value: Float = value.toFloat()
        override var type: AngleType = type
    }

//    fun mangle(value: Number = 0f, type: AngleType = AngleType.DEGREE) =

    // POINT
    fun point(x: Number = 0, y: Number = 0): EPoint =
        mpoint(x, y)

    fun point(other: EPoint) =
        point(other.x, other.y)

    fun point(angle: EAngle, magnitude: Number) =
        point(
            angle.cos * magnitude.f,
            angle.sin * magnitude.f
        )

    fun mpoint(other: EPoint) =
        mpoint(other.x, other.y)

    fun mpoint(angle: EAngle, magnitude: Number) =
        mpoint(
            angle.cos * magnitude.f,
            angle.sin * magnitude.f
        )

    // CIRCLE
    fun circle(center: EPoint = point(), radius: Number = 0f): ECircle =
        mcircle(center.toMutable(), radius)

    // SIZE
    fun size(width: Number = 0, height: Number = 0): ESize =
        msize(width, height)

    fun msize(other: ESize) = msize(other.width, other.height)


    // RECT
    fun rect(origin: EPoint = point(), size: ESize = size()): ERect =
        mrect(
            origin.toMutable(),
            size.toMutable()
        )

    fun rect(other: ERect): ERect = mrect(other)


    fun mrect(other: ERect): ERectMutable = mrect(other.origin.toMutable(), other.size.toMutable())

    // ANGLE
    fun angle(value: Number, type: AngleType) = mangle(value, type).toMutable()

    object Point {
        val inv = point(-1f, -1f)
        val zero = point(0f, 0f)
        val half = point(0.5f, 0.5f)
        val unit = point(1f, 1f)

        fun random(magnitude: Number = 1f, target: EPointMutable = mpoint()) =
            target.set(
                x = randomSign() * com.benoitthore.enamel.core.math.random() * magnitude.f,
                y = randomSign() * com.benoitthore.enamel.core.math.random() * magnitude.f
            ).selfLimitMagnitude(magnitude)

        fun random(
            minX: Number = 0f,
            minY: Number = 0f,
            maxX: Number = 1f,
            maxY: Number = 1f,
            target: EPointMutable = mpoint()
        ) = target.set(
            x = com.benoitthore.enamel.core.math.random(minX, maxX),
            y = com.benoitthore.enamel.core.math.random(minY, maxY)
        )
    }

    object PointMutable {
        fun zero(target: EPointMutable = E.mpoint()) = target.set(0f, 0f)
        fun half(target: EPointMutable = E.mpoint()) = target.set(0.5f, 0.5f)
        fun unit(target: EPointMutable = E.mpoint()) = target.set(1f, 1f)
    }

    object Rect {
        val zero = E.rect()
    }

    object RectMutable {
        val zero = E.mrect()
    }

    object Size {
        val zero: ESize = allocate { E.size() }
        val greatestSize: ESize = allocate { E.size(Float.MAX_VALUE, Float.MAX_VALUE) }
        fun square(size: Number) = E.msize(size, size)

        fun random(
            minSize: Number,
            maxSize: Number,
            target: ESizeMutable = E.msize()
        ): ESize =
            SizeMutable.random(minSize, maxSize, minSize, maxSize, target)

        fun random(
            minWidth: Number = 0,
            maxWidth: Number = 1,
            minHeight: Number = 0,
            maxHeight: Number = 1,
            target: ESizeMutable = E.msize()
        ): ESize = SizeMutable.random(minWidth, maxWidth, minHeight, maxHeight, target)
    }

    object SizeMutable {
        fun zero(target: ESizeMutable = E.msize()) = target.set(0, 0)
        fun greatestSize(target: ESizeMutable = E.msize()) =
            target.set(Float.MAX_VALUE, Float.MAX_VALUE)

        fun square(size: Number) = E.msize(size, size)

        fun random(
            minSize: Number,
            maxSize: Number,
            target: ESizeMutable = E.msize()
        ): ESizeMutable =
            random(minSize, maxSize, minSize, maxSize, target)

        fun random(
            minWidth: Number = 0,
            maxWidth: Number = 1,
            minHeight: Number = 0,
            maxHeight: Number = 1,
            target: ESizeMutable = E.msize()
        ): ESizeMutable = target.set(
            com.benoitthore.enamel.core.math.random(minWidth, maxWidth),
            com.benoitthore.enamel.core.math.random(minHeight, maxHeight)
        )
    }

    object Angle {
        val zero = 0.degrees()
        val unit = 1.rotations()
    }

    object MutableAngle {
        fun zero(target: EAngleMutable) = 0.degrees(target)
        fun unit(target: EAngleMutable) = 1.rotations(target)
    }
}
