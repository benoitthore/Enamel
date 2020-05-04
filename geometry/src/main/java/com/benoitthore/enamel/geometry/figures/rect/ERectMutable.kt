package com.benoitthore.enamel.geometry.figures.rect

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.Resetable
import com.benoitthore.enamel.geometry.allocateDebugMessage
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.size.ESizeMutable
import com.benoitthore.enamel.geometry.interfaces.bounds.CanSetBounds
import com.benoitthore.enamel.geometry.primitives.EPointMutable

interface ERectMutable : ERect,
    Resetable,
    CanSetBounds {
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

    override fun setCenter(x: Number, y: Number) {
        E.RectMutableCenter(x, y, width, height, target = this)
    }

    override fun reset() {
        origin.reset(); size.reset()
    }

    override fun setBounds(left: Number, top: Number, right: Number, bottom: Number) {
        origin.set(left, top)
        size.set(width = right.f - left.f, height = bottom.f - top.f)
    }
}