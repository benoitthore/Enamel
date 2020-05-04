package com.benoitthore.enamel.geometry.figures.rect

import com.benoitthore.enamel.geometry.allocateDebugMessage
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable
import com.benoitthore.enamel.geometry.primitives.size.ESizeMutable

internal class ERectMutableImpl internal constructor(
    x: Number,
    y: Number,
    width: Number,
    height: Number
) :
    ERectMutable {
    init {
        allocateDebugMessage()
    }

    override val origin: EPointMutable = E.PointMutable(x, y)
    override val size: ESizeMutable = E.SizeMutable(width, height)
}