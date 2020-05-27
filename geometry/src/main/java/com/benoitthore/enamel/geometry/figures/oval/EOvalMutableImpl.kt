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
) : EOvalMutable {

    init {
        setCenter(cx, cy)
        setSize(rx * 2f, ry * 2f)
    }

    private val _center: EPointMutable = E.PointMutable()
    override val center: EPoint get() = _center.update()

    private fun EPointMutable.update() = apply {
        getCenter()
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
        result = 31 * result + _center.hashCode()
        return result
    }

    override fun toMutable(): EOval {
        TODO("Not yet implemented")
    }

    override fun toImmutable(): EOvalMutable {
        TODO("Not yet implemented")
    }

    override fun setBounds(left: Number, top: Number, right: Number, bottom: Number) {
        TODO("Not yet implemented")
    }

    override fun setCenter(x: Number, y: Number) {
        TODO("Not yet implemented")
    }


}