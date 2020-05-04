package com.benoitthore.enamel.geometry.figures.line

import com.benoitthore.enamel.geometry.allocateDebugMessage
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable

internal class ELineMutableImpl internal constructor(
    x1: Number = 0,
    y1: Number = 0,
    x2: Number = 0,
    y2: Number = 0
) : ELineMutable {
    init {
        allocateDebugMessage()
    }

    override val start: EPointMutable = E.PointMutable(x1, y1)
    override val end: EPointMutable = E.PointMutable(x2, y2)
}