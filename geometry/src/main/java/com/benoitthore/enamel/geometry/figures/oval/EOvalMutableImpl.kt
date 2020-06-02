package com.benoitthore.enamel.geometry.figures.oval

import com.benoitthore.enamel.geometry.builders.E

internal class EOvalMutableImpl(
    cx: Number,
    cy: Number,
    rx: Number,
    ry: Number
) : EOvalMutable {

    override val left: Float
        get() = TODO("Not yet implemented")
    override val top: Float
        get() = TODO("Not yet implemented")
    override val right: Float
        get() = TODO("Not yet implemented")
    override val bottom: Float
        get() = TODO("Not yet implemented")

    override var ry: Float
        get() = TODO("Not yet implemented")
        set(value) {}
    override var rx: Float
        get() = TODO("Not yet implemented")
        set(value) {}

    init {
        val cx = cx.toFloat()
        val cy = cy.toFloat()
        val rx = rx.toFloat()
        val ry = ry.toFloat()
        TODO()
//        setBounds(
//            left = cx - rx,
//            top = cy - ry,
//            right = cx + rx,
//            bottom = cy + ry
//        )
    }


    override fun toMutable(): EOvalMutable = E.OvalMutable(this)
    override fun toImmutable(): EOval = E.Oval(this)

    override fun setBounds(left: Number, top: Number, right: Number, bottom: Number) {
        TODO()
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