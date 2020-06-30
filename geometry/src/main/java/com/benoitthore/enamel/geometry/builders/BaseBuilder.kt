package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.figures.circle.ECircleImpl
import com.benoitthore.enamel.geometry.figures.oval.EOval
import com.benoitthore.enamel.geometry.figures.oval.EOvalImpl
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.figures.rect.ERectImpl
import com.benoitthore.enamel.geometry.primitives.size.ESizeImpl
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.angle.EAngle
import com.benoitthore.enamel.geometry.primitives.angle.EAngleImpl
import com.benoitthore.enamel.geometry.primitives.linearfunction.ELinearFunction
import com.benoitthore.enamel.geometry.primitives.offset.EOffsetMutable
import com.benoitthore.enamel.geometry.primitives.offset.EOffsetMutableImpl
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable
import com.benoitthore.enamel.geometry.primitives.point.EPointMutableImpl
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransformMutable
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransformMutableImpl
import com.benoitthore.enamel.geometry.primitives.angle.EAngle.*

interface BaseBuilder {

    fun PointMutable(x: Number = 0, y: Number = 0): EPointMutable =
        EPointMutableImpl(x, y)

    fun AngleMutable(value: Number = 0f, type: AngleType = AngleType.RADIAN): EAngle =
        EAngleImpl(
            value,
            type
        )

    fun RectMutable(
        x: Number = 0f,
        y: Number = 0f,
        width: Number = 0f,
        height: Number = 0f
    ): ERect =
        ERectImpl(
            x,
            y,
            width,
            height
        )

    fun SizeMutable(width: Number = 0, height: Number = 0): ESize =
        ESizeImpl(width, height)

    fun CircleMutable(centerX: Number, centerY: Number, radius: Number = 0f): ECircle =
        ECircleImpl(
            centerX,
            centerY,
            radius
        )

    fun OvalMutable(
        centerX: Number = 0f,
        centerY: Number = 0f,
        rx: Number = 0f,
        ry: Number = 0f
    ): EOval =
        EOvalImpl(
            centerX,
            centerY,
            rx.toFloat(),
            ry.toFloat()
        )

    fun LineMutablearFunction(slope: Number = 0f, yIntercept: Number = 0f): ELinearFunction =
        ELinearFunction.Impl(slope.toFloat(), yIntercept.toFloat())

    fun OffsetMutable(
        top: Number = 0f, right: Number = 0f, bottom: Number = 0f, left: Number = 0f
    ): EOffsetMutable =
        EOffsetMutableImpl(
            top = top.toFloat(),
            right = right.toFloat(),
            bottom = bottom.toFloat(),
            left = left.toFloat()
        )

    fun TransformMutable(
        rotation: EAngle = E.AngleMutable(),
        rotationPivot: EPoint = E.PointMutable.half(),
        scale: EPoint = E.PointMutable.unit(),
        scalePivot: EPoint = E.PointMutable.half(),
        translation: EPoint = E.PointMutable()
    ): ETransformMutable =
        ETransformMutableImpl(
            rotation = rotation,
            rotationPivot = rotationPivot,
            scale = scale,
            scalePivot = scalePivot,
            translation = translation
        )

}