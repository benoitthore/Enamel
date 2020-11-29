package com.benoitthore.enamel.geometry.primitives.angle

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.builders.*
import com.benoitthore.enamel.geometry.primitives.angle.EAngle.*

fun Number.degrees(target: EAngle = Angle()): EAngle =
    target.set(
        this.f,
        AngleType.DEGREE
    )

fun Number.radians(target: EAngle = Angle()): EAngle =
    target.set(
        this.f,
        AngleType.RADIAN
    )

fun Number.rotations(target: EAngle = Angle()): EAngle =
    target.set(
        this.f,
        AngleType.ROTATION
    )

operator fun Number.times(angle: EAngle): EAngle = angle * this