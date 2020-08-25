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

    /* TODO Handle vertical/horizontal
        Reproduce issue by doing -> someLine.left = mousePosition().x
     */
    override var left: Float
        get() = min(start.x, end.x)
        set(value) {
            if (isTLBR) {
                start.x = value
            } else {
                end.x = value
            }
        }

    override var top: Float
        get() = min(start.y, end.y)
        set(value) {
            if (isTLBR) {
                start.y = value
            } else {
                end.y = value
            }
        }
    override var right: Float
        get() = max(start.x, end.x)
        set(value) {
            if (isTLBR) {
                end.x = value
            } else {
                start.x = value
            }
        }
    override var bottom: Float
        get() = max(start.y, end.y)
        set(value) {
            if (isTLBR) {
                end.y = value
            } else {
                start.y = value
            }
        }

    override var originX: Float
        get() = left
        set(value) {
            left = value
        }
    override var originY: Float
        get() = top
        set(value) {
            top = value
        }

    override var width: Float
        get() = right - left
        set(value) {
            val delta = width + value
            _setBounds(
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
            _setBounds(
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


    override fun _setBounds(left: Number, top: Number, right: Number, bottom: Number) {
        if (isTLBR) {
            start.set(left, top)
            end.set(right, bottom)
        } else {
            start.set(right, top)
            end.set(left, bottom)
        }
    }

    override fun set(other: ELine): ELine = apply {
        start.set(other.start)
        end.set(other.end)
    }

    override fun copy(): ELine = E.Line(this)

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