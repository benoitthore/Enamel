package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.figures.line.ELine
import com.benoitthore.enamel.geometry.figures.line.ELineImpl
import com.benoitthore.enamel.geometry.primitives.linearfunction.ELinearFunction
import com.benoitthore.enamel.geometry.primitives.point.EPoint

fun Line(x1: Number = 0, y1: Number = 0, x2: Number = 0, y2: Number = 0): ELine =
    ELineImpl(
        x1,
        y1,
        x2,
        y2
    )

fun Line(start: EPoint, end: EPoint): ELine =
    Line(start.x, start.y, end.x, end.y)


fun Line(other: ELine): ELine =
    Line(other.start.x, other.start.y, other.end.x, other.end.y)


