package com.benoitthore.enamel.geometry.figures

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.Resetable
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.interfaces.*
import com.benoitthore.enamel.geometry.primitives.EPoint
import com.benoitthore.enamel.geometry.primitives.EPointMutable
import com.benoitthore.enamel.geometry.svg.ESVG
import com.benoitthore.enamel.geometry.svg.SVGContext
import kotlin.text.set

interface EOval : ERect {

    val center: EPoint
    val rx: Float
    val ry: Float

    override val centerX: Float
        get() = center.x
    override val centerY: Float
        get() = center.y

    override val left: Float
        get() = center.x - rx
    override val top: Float
        get() = center.y - ry
    override val right: Float
        get() = center.x + rx
    override val bottom: Float
        get() = center.y + height

    override fun addTo(context: SVGContext) {
        context.oval(this)
    }
}

interface EOvalMutable : EOval, ERectMutable {
    override var ry: Float
        get() = size.height / 2f
        set(value) {
            size.height = value / 2f
        }
    override var rx: Float
        get() = size.width / 2f
        set(value) {
            size.width = value / 2f
        }

    override fun reset() {
        rx = 0f
        ry = 0f
    }


    class Impl(cx: Number, cy: Number, override var rx: Float, override var ry: Float) :
        EOvalMutable, ERectMutable by E.RectMutable() {

        init {
            setCenter(cx, cy)
            setSize(rx * 2f, ry * 2f)
        }

        private val _center: EPointMutable = E.PointMutable()
        override val center: EPoint get() = _center.update()

        private fun EPointMutable.update() = apply {
            getCenter()
        }

    }
}