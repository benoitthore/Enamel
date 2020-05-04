package com.benoitthore.enamel.geometry

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.figures.circle.ECircleMutable
import com.benoitthore.enamel.geometry.figures.line.ELine
import com.benoitthore.enamel.geometry.figures.line.ELineMutable
import com.benoitthore.enamel.geometry.figures.oval.EOval
import com.benoitthore.enamel.geometry.figures.oval.EOvalMutable
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.figures.rect.ERectMutable
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.size.ESizeMutable
import com.benoitthore.enamel.geometry.interfaces.bounds.set
import com.benoitthore.enamel.geometry.primitives.angle.EAngle
import com.benoitthore.enamel.geometry.primitives.angle.EAngleMutable
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable


fun EOval.toMutable(target: EOvalMutable = E.OvalMutable()): EOvalMutable =
    target.set(origin.toMutable(target.origin), size.toMutable())

fun EOval.toImmutable(): EOval = E.Oval(this)
fun EOval.ensureMutable(): EOvalMutable = if (this is EOvalMutable) this else toMutable()

fun ELine.toMutable(target: ELineMutable = E.LineMutable()): ELineMutable =
    target.set(start, end)

fun ELine.toImmutable(): ELine = E.Line(this)
fun ELine.ensureMutable(): ELineMutable = if (this is ELineMutable) this else toMutable()

fun ERect.toMutable(target: ERectMutable = E.RectMutable()): ERectMutable =
    target.set(origin.toMutable(target.origin), size.toMutable())

fun ERect.toImmutable(): ERect = E.Rect(this)
fun ERect.ensureMutable(): ERectMutable = if (this is ERectMutable) this else toMutable()

fun EAngle.toMutable(): EAngleMutable = E.AngleMutable(value, type)
fun EAngle.toImmutable(): EAngle = E.Angle(value, type)
fun EAngle.ensureMutable(): EAngleMutable = if (this is EAngleMutable) this else toMutable()

fun EPoint.toMutable(target: EPointMutable = E.PointMutable()) = target.set(x, y)
fun EPoint.toImmutable() = E.Point(x, y)
fun EPoint.ensureMutable(): EPointMutable = if (this is EPointMutable) this else toMutable()

fun ESize.toMutable() = E.SizeMutable(width, height)
fun ESize.toImmutable() = E.Size(width, height)
fun ESize.ensureMutable(): ESizeMutable = if (this is ESizeMutable) this else toMutable()

fun ECircle.toMutable() = E.CircleMutable(center = center.toMutable(), radius = radius)
fun ECircle.toImmutable() = E.Circle(center = center.toImmutable(), radius = radius)
fun ECircle.ensureMutable(): ECircleMutable = if (this is ECircleMutable) this else toMutable()