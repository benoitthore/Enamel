package com.benoitthore.enamel.geometry.figures.oval

import com.benoitthore.enamel.geometry.builders.*
import com.benoitthore.enamel.geometry.functions.EShape
import com.benoitthore.enamel.geometry.functions.EShapeMutable
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.size.ESizeMutable
import com.benoitthore.enamel.geometry.svg.SVGContext

interface EOval : EShape<EOval, EOvalMutable> {
    val size: ESize
    val center: EPoint


    override fun _copy(): EOval = Oval(this)


    val rx: Float
        get() = size.width / 2f
    val ry: Float
        get() = size.height / 2f
    override val centerY: Float
        get() = center.y
    override val centerX: Float
        get() = center.x


    override fun addTo(context: SVGContext) {
        context.oval(this)
    }
}

interface EOvalMutable : EOval, EShapeMutable<EOval, EOvalMutable> {

    override val size: ESizeMutable
    override val center: EPointMutable

    override var rx: Float
        get() = super.rx
        set(value) {
            size.width = value * 2
        }
    override var ry: Float
        get() = super.ry
        set(value) {
            size.height = value * 2
        }
    override var centerY: Float
        get() = super.centerY
        set(value) {
            center.y = value
        }
    override var centerX: Float
        get() = super.centerX
        set(value) {
            center.x = value
        }
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


    override fun _copy(): EOvalMutable = MutableOval(this)

    override fun toMutable(): EOvalMutable = _copy()

    override fun toImmutable(): EOval = _copy()

    override fun set(other: EOval): EOvalMutable = apply {
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
}

