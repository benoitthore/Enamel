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
            val dH = top - value
            origin.y = value
            height += dH
        }
    override var bottom: Float
        get() = top + height
        set(value) {
            height = value - top
        }

    override var left: Float
        get() = origin.x
        set(value) {
            val dW = left - value
            origin.x= value
            width += dW
        }
    override var right: Float
        get() = origin.x + width
        set(value) {
            width = value - left
        }


    override var originX: Float
        get() = left
        set(value) {
            origin.x = value
        }
    override var originY: Float
        get() = top
        set(value) {
            origin.y = value
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


    override fun _copy(): ERect = E.Rect(this)
    override fun set(other: ERect): ERect = apply {
        size.set(other.size)
        origin.set(other.origin)
    }

    override fun _setBounds(left: Number, top: Number, right: Number, bottom: Number) {
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