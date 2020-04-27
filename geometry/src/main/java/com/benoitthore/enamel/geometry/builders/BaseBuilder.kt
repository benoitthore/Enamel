package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.figures.ECircleMutable
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.geometry.figures.ESizeMutable
import com.benoitthore.enamel.geometry.primitives.*

interface BaseBuilder {

    fun mpoint(x: Number = 0, y: Number = 0): EPointMutable =
        EPointMutable.Impl(x, y)

    fun mangle(value: Number = 0f, type: AngleType = AngleType.RADIAN): EAngleMutable =
        EAngleMutable.Impl(
            value,
            type
        )

    fun mrect(
        x: Number = 0f,
        y: Number = 0f,
        width: Number = 0f,
        height: Number = 0f
    ): ERectMutable =
        ERectMutable.Impl(x, y, width, height)

    fun msize(width: Number = 0, height: Number = 0): ESizeMutable =
        ESizeMutable.Impl(width, height)

    fun mcircle(centerX: Number, centerY: Number, radius: Number = 0f): ECircleMutable =
        ECircleMutable.Impl(centerX, centerY, radius)

    fun mlinearFunction(slope: Number, yIntercept: Number): ELinearFunction =
        ELinearFunction.Impl(slope.toFloat(), yIntercept.toFloat())

    fun moffset(
        top: Number = 0f, right: Number = 0f, bottom: Number = 0f, left: Number = 0f
    ): EOffsetMutable = EOffsetMutable.Impl(
        top = top.toFloat(),
        right = right.toFloat(),
        bottom = bottom.toFloat(),
        left = left.toFloat()
    )

}