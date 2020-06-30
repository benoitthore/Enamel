package com.benoitthore.enamel.geometry.figures.line

import com.benoitthore.enamel.geometry.allocateDebugMessage
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import kotlin.math.max
import kotlin.math.min

internal class ELineImpl internal constructor(
    x1: Number = 0,
    y1: Number = 0,
    x2: Number = 0,
    y2: Number = 0
) : ELine {

    init {
        allocateDebugMessage()
    }

    override val start: EPoint = E.Point(x1, y1)
    override val end: EPoint = E.Point(x2, y2)

    override var left: Float
        get() = min(start.x, end.x)
        set(value) {
            TODO()
        }
    override var top: Float
        get() = min(start.y, end.y)
        set(value) {
            TODO()
        }
    override var right: Float
        get() = max(start.x, end.x)
        set(value) {
            TODO()
        }
    override var bottom: Float
        set(value) {
            TODO()
        }
        get() = max(start.y, end.y)
    override var originX: Float
        get() = TODO("Not yet implemented")
        set(value) {
            TODO("Not yet implemented")
        }
    override var originY: Float
        get() = TODO("Not yet implemented")
        set(value) {
            TODO("Not yet implemented")
        }

    override var width: Float
        get() = right - left
        set(value) {
            val delta = height + value
            setBounds(
                left = left + delta / 2f,
                right = right - delta / 2f,
                top = top,
                bottom = bottom
            )
        }
    override var height: Float
        get() = bottom - top
        set(value) {
            val delta = height + value
            setBounds(
                left = left,
                right = right,
                top = top + delta / 2f,
                bottom = bottom - delta / 2f
            )
        }
    override var centerX: Float
        get() = left + width / 2f
        set(value) {
            val shift = value - centerX
            start.x += shift
            end.x += shift
        }
    override var centerY: Float
        get() = top + height / 2f
        set(value) {
            val shift = value - centerY
            start.y += shift
            end.y += shift
        }


    override fun setBounds(left: Number, top: Number, right: Number, bottom: Number) {
        if (isTLBR) {
            start.set(left, top)
            end.set(right, bottom)
        } else {
            start.set(right, top)
            end.set(left, bottom)
        }
    }

    override fun copy(): ELine {
        TODO("Not yet implemented")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ELine) return false

        if (start != other.start) return false
        if (end != other.end) return false

        return true
    }

    override fun hashCode(): Int {
        var result = start.hashCode()
        result = 31 * result + end.hashCode()
        return result
    }

    override fun toString(): String {
        return "Line(start=$start, end=$end)"
    }


}