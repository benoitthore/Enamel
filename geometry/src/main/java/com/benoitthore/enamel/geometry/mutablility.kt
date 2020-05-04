package com.benoitthore.enamel.geometry

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.*
import com.benoitthore.enamel.geometry.interfaces.set
import com.benoitthore.enamel.geometry.primitives.EAngle
import com.benoitthore.enamel.geometry.primitives.EAngleMutable
import com.benoitthore.enamel.geometry.primitives.EPoint
import com.benoitthore.enamel.geometry.primitives.EPointMutable


fun EOval.toMutable(target: EOvalMutable = E.OvalMutable()): EOvalMutable =
    target.set(origin.toMutable(target.origin), size.toMutable())

fun EOval.toImmutable(): EOval = E.Oval(this)

fun ELine.toMutable(target: ELineMutable = E.LineMutable()): ELineMutable =
    target.set(start, end)

fun ELine.toImmutable(): ELine = E.Line(this)

fun ERect.toMutable(target: ERectMutable = E.RectMutable()): ERectMutable =
    target.set(origin.toMutable(target.origin), size.toMutable())

fun ERect.toImmutable(): ERect = E.Rect(this)

fun EAngle.toMutable(): EAngleMutable = E.AngleMutable(value, type)
fun EAngle.toImmutable(): EAngle = E.Angle(value, type)


fun EPoint.toMutable(target: EPointMutable = E.PointMutable()) = target.set(x, y)
fun EPoint.toImmutable() = E.Point(x, y)


fun ESize.toMutable() = E.SizeMutable(width, height)
fun ESize.toImmutable() = E.Size(width, height)


fun ECircle.toMutable() = E.CircleMutable(center = center.toMutable(), radius = radius)
fun ECircle.toImmutable() = E.Circle(center = center.toImmutable(), radius = radius)