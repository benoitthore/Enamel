package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.figures.ELine
import com.benoitthore.enamel.geometry.figures.ELineMutable
import com.benoitthore.enamel.geometry.primitives.EPoint

interface LineBuilder : PointBuilder, BaseBuilder {
    fun mline(x1: Number = 0, y1: Number = 0, x2: Number = 0, y2: Number = 0): ELineMutable =
        ELineMutable.Impl(x1, y1, x2, y2)

    fun mline(start: EPoint, end: EPoint): ELineMutable = mline(start.x, start.y, end.x, end.y)

    fun line(x1: Number = 0, y1: Number = 0, x2: Number = 0, y2: Number = 0): ELine =
        line(x1, y1, x2, y2)

    fun line(start: EPoint, end: EPoint): ELine = line(start.x, start.y, end.x, end.y)

}