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

    override var width: Float
        get() = rx * 2
        set(value) {
            rx = value / 2f
        }
    override var height: Float
        get() = ry * 2
        set(value) {
            ry = value / 2f
        }
    override val size: ESizeMutable = MutableSize(rx.f * 2f, ry.f * 2f)
    override val center: EPointMutable = MutablePoint(cx, cy)

    override var left: Float
        get() = centerX - rx
        set(value) {
            center.x = value + rx
        }
    override var top: Float
        get() = centerY - ry
        set(value) {
            center.y = value + ry
        }
    override var right: Float
        get() = centerX + rx
        set(value) {
            center.x = value - rx
        }
    override var bottom: Float
        get() = centerY + ry
        set(value) {
            center.y = value - ry
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

    override fun set(other: EOval): EOval = apply {
        center.set(other.center)
        size.set(other.size)
    }


    override fun _setBounds(left: Number, top: Number, right: Number, bottom: Number) {
        val left = left.toFloat()
        val top = top.toFloat()
        val right = right.toFloat()
        val bottom = bottom.toFloat()

        rx = (right - left) / 2f
        ry = (bottom - top) / 2f
        centerX = left + rx
        centerY = top + ry
    }


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