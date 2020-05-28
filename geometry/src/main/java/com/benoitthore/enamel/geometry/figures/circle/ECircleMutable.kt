package com.benoitthore.enamel.geometry.figures.circle

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.Resetable
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.interfaces.bounds.CanSetBounds
import com.benoitthore.enamel.geometry.interfaces.bounds.EShape
import com.benoitthore.enamel.geometry.interfaces.bounds.EShapeMutable
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable
import kotlin.math.min

interface ECircleMutable : ECircle, CanSetBounds<ECircle, ECircleMutable>, EShapeMutable<ECircle,ECircleMutable>{

    override var radius: Float

    fun set(other: ECircle) = set(other.center.x, other.center.y, other.radius)
    fun set(center: EPoint = this.center, radius: Number = this.radius) =
        set(center.x, center.y, radius)

    fun set(x: Number = centerX, y: Number = centerY, radius: Number = this.radius): ECircleMutable {
        this.centerX = x.f
        this.centerY = y.f
        this.radius = radius.f
        return this
    }


}