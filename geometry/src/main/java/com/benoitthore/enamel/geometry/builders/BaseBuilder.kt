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
import com.benoitthore.enamel.geometry.primitives.offset.EOffset
import com.benoitthore.enamel.geometry.primitives.offset.EOffsetImpl
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.point.EPointImpl
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransformImpl
import com.benoitthore.enamel.geometry.primitives.angle.EAngle.*

interface BaseBuilder {

    fun Point(x: Number = 0, y: Number = 0): EPoint =
        EPointImpl(x, y)

    fun Angle(value: Number = 0f, type: AngleType = AngleType.RADIAN): EAngle =
        EAngleImpl(
            value,
            type
        )

    fun Rect(
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

    fun Size(width: Number = 0, height: Number = 0): ESize =
        ESizeImpl(width, height)

    fun Circle(centerX: Number, centerY: Number, radius: Number = 0f): ECircle =
        ECircleImpl(
            centerX,
            centerY,
            radius
        )

    fun Oval(
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

    fun LinearFunction(slope: Number = 0f, yIntercept: Number = 0f): ELinearFunction =
        ELinearFunction.Impl(slope.toFloat(), yIntercept.toFloat())

    fun Offset(
        top: Number = 0f, right: Number = 0f, bottom: Number = 0f, left: Number = 0f
    ): EOffset =
        EOffsetImpl(
            top = top.toFloat(),
            right = right.toFloat(),
            bottom = bottom.toFloat(),
            left = left.toFloat()
        )

    fun Transform(
        rotation: EAngle = E.Angle(),
        rotationPivot: EPoint = E.Point.half(),
        scale: EPoint = E.Point.unit(),
        scalePivot: EPoint = E.Point.half(),
        translation: EPoint = E.Point()
    ): ETransform =
        ETransformImpl(
            rotation = rotation,
            rotationPivot = rotationPivot,
            scale = scale,
            scalePivot = scalePivot,
            translation = translation
        )

}