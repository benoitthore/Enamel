package com.benoitthore.enamel.geometry.interfaces

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.primitives.EPoint
import com.benoitthore.enamel.geometry.primitives.EPointMutable

interface HasCenter {
    fun getCenter(target: EPointMutable = E.PointMutable()): EPointMutable =
        target.set(centerX, centerY)

    val centerX: Float
    val centerY: Float
}

interface CanSetCenter : HasCenter {
    fun setCenter(x: Number, y: Number)
    fun setCenter(center: EPoint) = setCenter(center.x, center.y)
}


