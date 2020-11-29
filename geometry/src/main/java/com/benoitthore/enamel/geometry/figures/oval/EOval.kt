package com.benoitthore.enamel.geometry.figures.oval

import com.benoitthore.enamel.geometry.builders.*
import com.benoitthore.enamel.geometry.functions.EShape
import com.benoitthore.enamel.geometry.functions.EShapeMutable
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.size.ESizeMutable
import com.benoitthore.enamel.geometry.svg.SVGContext

interface EOval : EShape {
    val size: ESize
    val center: EPoint


    override fun _copy(): EOval = Oval(this)
    fun set(other: EOval): EOval


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

interface EOvalMutable : EOval, EShapeMutable {

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

    override fun _copy(): EOvalMutable = MutableOval(this)
}

