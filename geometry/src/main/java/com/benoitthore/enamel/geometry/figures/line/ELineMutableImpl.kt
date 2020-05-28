package com.benoitthore.enamel.geometry.figures.line

import com.benoitthore.enamel.geometry.allocateDebugMessage
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable
import kotlin.math.max
import kotlin.math.min

internal class ELineMutableImpl internal constructor(
    x1: Number = 0,
    y1: Number = 0,
    x2: Number = 0,
    y2: Number = 0
) : ELineMutable {
    init {
        allocateDebugMessage()
    }

    override val left: Float
        get() = min(start.x, end.x)
    override val top: Float
        get() = min(start.y, end.y)
    override val right: Float
        get() = max(start.x, end.x)
    override val bottom: Float
        get() = max(start.y, end.y)



    override val start: EPointMutable = E.PointMutable(x1, y1)
    override val end: EPointMutable = E.PointMutable(x2, y2)
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

    override fun toMutable(): ELine {
        TODO("Not yet implemented")
    }

    override fun toImmutable(): ELineMutable {
        TODO("Not yet implemented")
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

}