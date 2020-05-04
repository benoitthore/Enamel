package com.benoitthore.enamel.geometry.primitives.angle

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.primitives.angle.EAngle.*

fun Number.degrees(target: EAngleMutable = E.AngleMutable()): EAngleMutable =
    target.set(
        this.f,
        AngleType.DEGREE
    )

fun Number.radians(target: EAngleMutable = E.AngleMutable()): EAngleMutable =
    target.set(
        this.f,
        AngleType.RADIAN
    )

fun Number.rotations(target: EAngleMutable = E.AngleMutable()): EAngleMutable =
    target.set(
        this.f,
        AngleType.ROTATION
    )

operator fun Number.times(angle: EAngleMutable): EAngleMutable = angle * this