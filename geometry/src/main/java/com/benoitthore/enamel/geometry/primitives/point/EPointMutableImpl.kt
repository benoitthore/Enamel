package com.benoitthore.enamel.geometry.primitives.point

import com.benoitthore.enamel.geometry.allocateDebugMessage

internal class EPointMutableImpl internal constructor(x: Number, y: Number) :
    EPointMutable {
    override var x: Float = x.toFloat()
    override var y: Float = y.toFloat()

    init {
        allocateDebugMessage()
    }

    override fun toString(): String {
        return "point($x ; $y)"
    }

    override fun equals(other: Any?): Boolean =
        (other as? EPoint)?.let { it.x == x && it.y == y } ?: false

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }
}