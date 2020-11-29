package com.benoitthore.enamel.geometry.figures.oval

import com.benoitthore.enamel.geometry.builders.*
import com.benoitthore.enamel.geometry.functions.EShape
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.svg.SVGContext

interface EOval : EShape {
    val size: ESize
    val center: EPoint


    override fun _copy(): EOval = Oval(this)
    fun set(other : EOval) : EOval




    var rx: Float
        get() = size.width / 2f
        set(value) {
            size.width = value * 2
        }
    var ry: Float
        get() = size.height / 2f
        set(value) {
            size.height = value * 2
        }
    override var centerY: Float
        get() = center.y
        set(value) {
            center.y = value
        }
    override var centerX: Float
        get() = center.x
        set(value) {
            center.x = value
        }


    override fun addTo(context: SVGContext) {
        context.oval(this)
    }
}