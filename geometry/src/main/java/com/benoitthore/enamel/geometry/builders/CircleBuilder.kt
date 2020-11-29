package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.figures.circle.ECircleImpl
import com.benoitthore.enamel.geometry.primitives.point.EPoint

fun Circle(centerX: Number = 0f, centerY: Number = 0f, radius: Number = 0f): ECircle =
    ECircleImpl(
        centerX,
        centerY,
        radius
    )

fun Circle(center: EPoint = Point(), radius: Number = 0f): ECircle =
    Circle(center.x, center.y, radius)

fun Circle(other: ECircle): ECircle =
    Circle(other.center.x, other.center.y, other.radius)
