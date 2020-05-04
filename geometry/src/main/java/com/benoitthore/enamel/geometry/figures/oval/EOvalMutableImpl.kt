package com.benoitthore.enamel.geometry.figures.oval

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rect.ERectMutable
import com.benoitthore.enamel.geometry.interfaces.bounds.setSize
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable

internal class EOvalMutableImpl(
    cx: Number,
    cy: Number,
    override var rx: Float,
    override var ry: Float
) :
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