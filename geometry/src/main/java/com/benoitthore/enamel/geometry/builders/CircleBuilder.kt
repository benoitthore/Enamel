package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.primitives.point.EPoint

interface CircleBuilder : PointBuilder, BaseBuilder {

    fun Circle(center: EPoint = Point(), radius: Number = 0f): ECircle =
        Circle(center.x, center.y, radius)

    fun Circle(other: ECircle): ECircle =
        Circle(other.center.x, other.center.y, other.radius)

}
