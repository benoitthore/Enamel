package com.benoitthore.enamel.geometry.figures.oval

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.builders.*
import com.benoitthore.enamel.geometry.figures.line.ELine
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.size.ESizeMutable

internal class EOvalImpl(
    cx: Number,
    cy: Number,
    rx: Number,
    ry: Number
) : EOvalMutable {


    override val size: ESizeMutable = MutableSize(rx.f * 2f, ry.f * 2f)
    override val center: EPointMutable = MutablePoint(cx, cy)


    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        if (other !is EOval) return false

        if (rx != other.rx) return false
        if (ry != other.ry) return false
        if (centerX != other.centerX) return false
        if (centerY != other.centerY) return false

        return true
    }

    override fun hashCode(): Int {
        var result = rx.hashCode()
        result = 31 * result + ry.hashCode()
        return result
    }

    override fun toString(): String {
        return "EOval(centerX=$centerX,centerY=$centerY,,rx=$rx, ry=$ry)"
    }
}