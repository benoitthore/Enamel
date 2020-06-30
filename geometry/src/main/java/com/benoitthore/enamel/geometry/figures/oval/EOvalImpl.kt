package com.benoitthore.enamel.geometry.figures.oval

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.line.ELine

internal class EOvalImpl(
    cx: Number,
    cy: Number,
    rx: Number,
    ry: Number
) : EOval {

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
    override var centerX: Float = cx.toFloat()
    override var centerY: Float = cy.toFloat()
    override var ry: Float = rx.toFloat()
    override var rx: Float = ry.toFloat()

    override var left: Float
        get() = centerX - rx
        set(value) {
            TODO()
        }
    override var top: Float
        get() = centerY - ry
        set(value) {
            TODO()
        }
    override var right: Float
        get() = centerX + rx
        set(value) {
            TODO()
        }
    override var bottom: Float
        get() = centerY + ry
        set(value) {
            TODO()
        }
    override var originX: Float
        get() = TODO("Not yet implemented")
        set(value) {}
    override var originY: Float
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun copy(): EOval {
        TODO("Not yet implemented")
    }

    override fun setBounds(left: Number, top: Number, right: Number, bottom: Number) {
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