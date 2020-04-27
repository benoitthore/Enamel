package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.figures.ECircle
import com.benoitthore.enamel.geometry.figures.ECircleMutable
import com.benoitthore.enamel.geometry.primitives.EPoint

interface CircleBuilder : PointBuilder, BaseBuilder {

    fun circle(centerX: Number, centerY: Number, radius: Number = 0f): ECircle =
        mcircle(centerX, centerY, radius)

    //

    fun mcircle(center: EPoint = mpoint(), radius: Number = 0f): ECircleMutable =
        mcircle(center.x, center.y, radius)

    fun circle(center: EPoint = point(), radius: Number = 0f): ECircle =
        mcircle(center, radius)
}
