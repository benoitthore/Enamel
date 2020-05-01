package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.figures.ECircle
import com.benoitthore.enamel.geometry.figures.ECircleMutable
import com.benoitthore.enamel.geometry.primitives.EPoint

interface CircleBuilder : PointBuilder, BaseBuilder {

    fun Circle(centerX: Number, centerY: Number, radius: Number = 0f): ECircle =
        CircleMutable(centerX, centerY, radius)

    //

    fun CircleMutable(center: EPoint = PointMutable(), radius: Number = 0f): ECircleMutable =
        CircleMutable(center.x, center.y, radius)

    fun Circle(center: EPoint = Point(), radius: Number = 0f): ECircle =
        CircleMutable(center, radius)
}
