package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.figures.ELine
import com.benoitthore.enamel.geometry.figures.ELineMutable
import com.benoitthore.enamel.geometry.primitives.EPoint

interface LineBuilder : PointBuilder, BaseBuilder {
    fun mLine(x1: Number = 0, y1: Number = 0, x2: Number = 0, y2: Number = 0): ELineMutable =
        ELineMutable.Impl(x1, y1, x2, y2)

    fun mLine(start: EPoint, end: EPoint): ELineMutable = mLine(start.x, start.y, end.x, end.y)

    fun Line(x1: Number = 0, y1: Number = 0, x2: Number = 0, y2: Number = 0): ELine =
        Line(x1, y1, x2, y2)

    fun Line(start: EPoint, end: EPoint): ELine = Line(start.x, start.y, end.x, end.y)

}