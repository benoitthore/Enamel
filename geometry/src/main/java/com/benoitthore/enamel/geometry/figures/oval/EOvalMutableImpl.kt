package com.benoitthore.enamel.geometry.figures.oval

import com.benoitthore.enamel.geometry.builders.E

internal class EOvalMutableImpl(
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
    override var centerX: Float = cx.toFloat()
    override var centerY: Float = cy.toFloat()
    override var ry: Float = rx.toFloat()
    override var rx: Float = ry.toFloat()

    override val left: Float
        get() = centerX - rx
    override val top: Float
        get() = centerY - ry
    override val right: Float
        get() = centerX + rx
    override val bottom: Float
        get() = centerY + ry

    override fun toMutable(): EOvalMutable = E.OvalMutable(this)
    override fun toImmutable(): EOval = E.Oval(this)

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
        if (center != other.center) return false

        return true
    }

    override fun hashCode(): Int {
        var result = rx.hashCode()
        result = 31 * result + ry.hashCode()
        return result
    }

    override fun toString(): String {
        return "EOval(center=$center,rx=$rx, ry=$ry)"
    }
}