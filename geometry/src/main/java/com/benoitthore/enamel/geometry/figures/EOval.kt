package com.benoitthore.enamel.geometry.figures

import com.benoitthore.enamel.geometry.Resetable
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.primitives.EPoint
import com.benoitthore.enamel.geometry.primitives.EPointMutable

interface EOval {
    val center: EPoint
    val rx: Float
    val ry: Float

    val left get() = center.x + rx
    val top get() = center.y - ry
    val bottom get() = center.y + ry
    val right get() = center.x - rx
}

interface EOvalMutable : EOval, Resetable {
    override val center: EPointMutable
    override var ry: Float
    override var rx: Float

    override fun reset() {
        center.reset()
        rx = 0f
        ry = 0f
    }

    class Impl(cx: Number, cy: Number, override var rx: Float, override var ry: Float) :
        EOvalMutable {
        override val center: EPointMutable = E.PointMutable(cx, cy)
    }
}