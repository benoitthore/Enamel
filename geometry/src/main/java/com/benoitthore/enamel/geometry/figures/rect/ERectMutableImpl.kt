package com.benoitthore.enamel.geometry.figures.rect

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.allocateDebugMessage
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable
import com.benoitthore.enamel.geometry.primitives.size.ESizeMutable

internal class ERectMutableImpl internal constructor(
    x: Number,
    y: Number,
    width: Number,
    height: Number
) : ERectMutable {

    init {
        allocateDebugMessage()
    }

    override val top: Float
        get() = origin.y
    override val bottom: Float
        get() = top + height

    override val left: Float
        get() = origin.x
    override val right: Float
        get() = origin.x + width


    override fun toMutable(): ERectMutable = E.RectMutable(this)

    override fun toImmutable(): ERect = E.Rect(this)

    override fun setBounds(left: Number, top: Number, right: Number, bottom: Number) {
        origin.set(x = left, y = top)
        size.set(width = right.f - left.f, height = bottom.f - top.f)
    }


    override val origin: EPointMutable = E.PointMutable(x, y)
    override val size: ESizeMutable = E.SizeMutable(width, height)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ERect) return false

        if (origin != other.origin) return false
        if (size != other.size) return false

        return true
    }

    override fun hashCode(): Int {
        var result = origin.hashCode()
        result = 31 * result + size.hashCode()
        return result
    }

    override fun toString(): String {
        return "Rect(origin=$origin, size=$size)"
    }

}