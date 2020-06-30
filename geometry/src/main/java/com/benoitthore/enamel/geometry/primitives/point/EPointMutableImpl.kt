package com.benoitthore.enamel.geometry.primitives.point

import com.benoitthore.enamel.geometry.allocateDebugMessage
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.line.ELine

internal class EPointImpl internal constructor(x: Number, y: Number) :
    EPoint {
    override var x: Float = x.toFloat()
    override var y: Float = y.toFloat()

    init {
        allocateDebugMessage()
    }

    override fun copy(): EPoint {
        TODO("Not yet implemented")
    }
    override fun toString(): String {
        return "Point($x ; $y)"
    }

    override fun equals(other: Any?): Boolean =
        (other as? EPoint)?.let { it.x == x && it.y == y } ?: false

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }
}