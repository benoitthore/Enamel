package com.benoitthore.enamel.geometry.figures.line

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.interfaces.bounds.CanSetBounds
import com.benoitthore.enamel.geometry.interfaces.bounds.EShapeMutable
import com.benoitthore.enamel.geometry.primitives.angle.EAngleMutable
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable

interface ELineMutable : ELine, CanSetBounds<ELine, ELineMutable>,
    EShapeMutable<ELine, ELineMutable> {

    override val start: EPointMutable
    override val end: EPointMutable

    fun set(start: EPoint = this.start, end: EPoint = this.end) =
        set(start.x, start.y, end.x, end.y)

    fun set(
        x1: Number = start.x,
        y1: Number = start.y,
        x2: Number = end.x,
        y2: Number = end.y
    ) = apply {
        start.x = x1.f
        start.y = y1.f

        end.x = x2.f
        end.y = y2.f
    }


    fun selfRotate(
        offsetAngle: EAngleMutable,
        around: EPoint = getCenter(E.PointMutable()),
        target: ELineMutable
    ) = rotate(offsetAngle, around, this)

    fun selfExpand(distance: Number, from: Number = 0f) = expand(distance, from, this)


}