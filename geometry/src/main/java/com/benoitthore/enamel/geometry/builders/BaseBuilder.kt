package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.figures.ECircleMutable
import com.benoitthore.enamel.geometry.figures.EOvalMutable
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.geometry.figures.ESizeMutable
import com.benoitthore.enamel.geometry.primitives.*

interface BaseBuilder {

    fun PointMutable(x: Number = 0, y: Number = 0): EPointMutable =
        EPointMutable.Impl(x, y)

    fun AngleMutable(value: Number = 0f, type: AngleType = AngleType.RADIAN): EAngleMutable =
        EAngleMutable.Impl(
            value,
            type
        )

    fun RectMutable(
        x: Number = 0f,
        y: Number = 0f,
        width: Number = 0f,
        height: Number = 0f
    ): ERectMutable =
        ERectMutable.Impl(x, y, width, height)

    fun SizeMutable(width: Number = 0, height: Number = 0): ESizeMutable =
        ESizeMutable.Impl(width, height)

    fun CircleMutable(centerX: Number, centerY: Number, radius: Number = 0f): ECircleMutable =
        ECircleMutable.Impl(centerX, centerY, radius)

    fun OvalMutable(
        centerX: Number = 0f,
        centerY: Number = 0f,
        rx: Number = 0f,
        ry: Number = 0f
    ): EOvalMutable =
        EOvalMutable.Impl(centerX, centerY, rx.toFloat(), ry.toFloat())

    fun LineMutablearFunction(slope: Number = 0f, yIntercept: Number = 0f): ELinearFunction =
        ELinearFunction.Impl(slope.toFloat(), yIntercept.toFloat())

    fun OffsetMutable(
        top: Number = 0f, right: Number = 0f, bottom: Number = 0f, left: Number = 0f
    ): EOffsetMutable = EOffsetMutable.Impl(
        top = top.toFloat(),
        right = right.toFloat(),
        bottom = bottom.toFloat(),
        left = left.toFloat()
    )

}