package com.benoitthore.enamel.geometry.figures.rect

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.Resetable
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.primitives.size.ESizeMutable
import com.benoitthore.enamel.geometry.interfaces.bounds.CanSetBounds
import com.benoitthore.enamel.geometry.interfaces.bounds.EShapeMutable
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable

interface ERectMutable : ERect, CanSetBounds<ERect, ERectMutable>, EShapeMutable<ERect,ERectMutable> {

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

    override fun setCenter(x: Number, y: Number) {
        E.RectMutableCenter(x, y, width, height, target = this)
    }


}