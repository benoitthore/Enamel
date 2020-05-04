package com.benoitthore.enamel.geometry.figures.circle

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.Resetable
import com.benoitthore.enamel.geometry.allocateDebugMessage
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.interfaces.bounds.CanSetBounds
import com.benoitthore.enamel.geometry.primitives.EPoint
import com.benoitthore.enamel.geometry.primitives.EPointMutable
import kotlin.math.min

interface ECircleMutable : ECircle,
    CanSetBounds,
    Resetable {
    class Impl internal constructor(centerX: Number, centerY: Number, radius: Number) :
        ECircleMutable {
        init {
            allocateDebugMessage()
        }

        override val center: EPointMutable = E.PointMutable(centerX, centerY)
        override var radius: Float = radius.toFloat()

        override fun equals(other: Any?): Boolean {
            if (this === other) return true

            if (other !is Impl) return false

            if (center != other.center) return false
            if (radius != other.radius) return false

            return true
        }

        override fun hashCode(): Int {
            var result = center.hashCode()
            result = 31 * result + radius.hashCode()
            return result
        }

        override fun toString(): String {
            return "Circle(center=$center, radius=$radius)"
        }


    }

    override val center: EPointMutable
    override var radius: Float

    /**
    position the circle on the TOP RIGHT of the given rectangle
    sets the radius based on whichever is smaller width or height
     */
    override fun setBounds(left: Number, top: Number, right: Number, bottom: Number) {
        val w = (right.f - left.f) / 2f
        val h = (bottom.f - top.f) / 2f
        radius = min(w, h)

        center.x = left.f + radius
        center.y = top.f + radius
    }

    fun copy(
        x: Number = this.x,
        y: Number = this.y,
        radius: Number = this.radius,
        target: ECircleMutable = E.CircleMutable()
    ) =
        E.CircleMutable(center.copy(x = x, y = y, target = target.center), radius = radius)

    fun copy(
        center: EPoint,
        radius: Number = this.radius,
        target: ECircleMutable = E.CircleMutable()
    ) = copy(x = center.x, y = center.y, radius = radius, target = target)

    override var x: Float
        get() = super.x
        set(value) {
            center.x = value
        }
    override var y: Float
        get() = super.y
        set(value) {
            center.y = value
        }

    override fun reset() {
        set(0, 0, 0)
    }

    fun set(other: ECircle) = set(other.center.x, other.center.y, other.radius)
    fun set(center: EPoint = this.center, radius: Number = this.radius) =
        set(center.x, center.y, radius)

    fun set(x: Number = this.x, y: Number = this.y, radius: Number = this.radius): ECircleMutable {
        this.center.set(x, y)
        this.radius = radius.f
        return this
    }

    override var left: Float
        get() = super.left
        set(value) {

        }
    override var top: Float
        get() = super.top
        set(value) {

        }
    override var right: Float
        get() = super.right
        set(value) {

        }
    override var bottom: Float
        get() = super.bottom
        set(value) {

        }


}