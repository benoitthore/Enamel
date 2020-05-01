package com.benoitthore.enamel.geometry.figures

import com.benoitthore.enamel.geometry.alignement.EAlignment
import com.benoitthore.enamel.geometry.Resetable
import com.benoitthore.enamel.core.math.*
import com.benoitthore.enamel.geometry.allocateDebugMessage
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.interfaces.CanSetBounds
import com.benoitthore.enamel.geometry.interfaces.HasBounds
import com.benoitthore.enamel.geometry.interfaces.HasCenter
import com.benoitthore.enamel.geometry.interfaces.set
import com.benoitthore.enamel.geometry.primitives.*
import com.benoitthore.enamel.geometry.svg.ESVG
import com.benoitthore.enamel.geometry.svg.SVGContext

interface ERect : ESVG, HasBounds, HasCenter {

    val origin: EPoint
    val size: ESize

    override fun addTo(context: SVGContext) {
        context.rect(this)
    }

    fun copy(target: ERectMutable = E.RectMutable()) =
        E.RectMutable(origin.copy(target = target.origin), size.copy(target = target.size))

    override val height get() = size.height
    override val width get() = size.width

    override val top: Float
        get() = origin.y
    override val bottom: Float
        get() = top + height
    override val left: Float
        get() = origin.x
    override val right: Float
        get() = origin.x + width

    override val centerX: Float
        get() = originX + width / 2f
    override val centerY: Float
        get() = originY + height / 2f
}

interface ERectMutable : ERect, Resetable, CanSetBounds {
    override val origin: EPointMutable
    override val size: ESizeMutable

    override var height: Float
        get() = super<ERect>.height
        set(value) {
            size.height = value
        }
    override var width: Float
        get() = super<ERect>.width
        set(value) {
            size.width = value
        }

    class Impl internal constructor(x: Number, y: Number, width: Number, height: Number) :
        ERectMutable {
        init {
            allocateDebugMessage()
        }

        override val origin: EPointMutable = E.PointMutable(x, y)
        override val size: ESizeMutable = E.SizeMutable(width, height)
    }

    override fun reset() {
        origin.reset(); size.reset()
    }

    override fun setBounds(left: Number, top: Number, right: Number, bottom: Number) {
        origin.set(left, top)
        size.set(width = right.f - left.f, height = bottom.f - top.f)
    }
}
