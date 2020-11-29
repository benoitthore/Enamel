package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.figures.oval.EOval
import com.benoitthore.enamel.geometry.figures.oval.EOvalImpl
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.primitives.angle.EAngle
import com.benoitthore.enamel.geometry.primitives.angle.degrees
import com.benoitthore.enamel.geometry.primitives.angle.rotations
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.size.ESize


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

    fun Oval(other: EOval) = Oval(other.centerX, other.centerY, other.rx, other.ry)
    fun Oval(rect: ERect) = Oval(rect.centerX, rect.centerY, rect.width / 2f, rect.height / 2f)
    fun Oval(center: EPoint, size: ESize) =
        Oval(center.x, center.y, size.width / 2f, size.height / 2f)