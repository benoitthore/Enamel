package com.benoitthore.enamel.geometry.figures.rect

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.allocateDebugMessage
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.line.ELine
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.size.ESize

internal class ERectImpl internal constructor(
    x: Number,
    y: Number,
    width: Number,
    height: Number
) : ERect {

    init {
        allocateDebugMessage()
    }

    override val origin: EPoint = E.Point(x, y)
    override val size: ESize = E.Size(width, height)

    override var top: Float
        get() = origin.y
        set(value) {
            TODO()
        }
    override var bottom: Float
        get() = top + height
        set(value) {
            TODO()
        }
    override var originX: Float
        get() = TODO("Not yet implemented")
        set(value) {}
    override var originY: Float
        get() = TODO("Not yet implemented")
        set(value) {}
    override var left: Float
        get() = origin.x
        set(value) {
            TODO()
        }
    override var right: Float
        get() = origin.x + width
        set(value) {
            TODO()
        }


    override var centerX: Float
        get() = left + width / 2f
        set(value) {
            val shift = value - centerX
            origin.x += shift
        }
    override var centerY: Float
        get() = top + height / 2f
        set(value) {
            val shift = value - centerY
            origin.y += shift
        }


    override var height: Float
        get() = size.height
        set(value) {
            size.height = value
        }
    override var width: Float
        get() = size.width
        set(value) {
            size.width = value
        }


    override fun copy(): ERect {
        TODO("Not yet implemented")
    }

    override fun setBounds(left: Number, top: Number, right: Number, bottom: Number) {
        origin.set(x = left, y = top)
        size.set(width = right.f - left.f, height = bottom.f - top.f)
    }

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