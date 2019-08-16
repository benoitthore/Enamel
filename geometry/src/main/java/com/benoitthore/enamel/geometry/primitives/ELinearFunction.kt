package com.benoitthore.enamel.geometry.primitives

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.figures.ELineMutable

data class ELinearFunction(val slope: Float = 1f, val yIntercept: Float = 0f) {

    inline val a
        get() = slope
    inline val b
        get() = yIntercept

    constructor(slope: Number = 1f, yIntercept: Number = 0f) : this(slope.f, yIntercept.f)

    operator fun get(x: Number) = get(x.f)
    operator fun get(x: Float) = a * x + b

    fun getIntersection(other: ELinearFunction): EPointMutable? {
        // a1 * x + b1 = a2 * x + b2
        val a1 = this.a
        val b1 = this.b
        val a2 = other.a
        val b2 = other.b
        if (a1 == a2) {
            // Lines are parallel
            return null
        }

        val x = (b2 - b1) / (a1 - a2)
        val y = this[x]

        return x point y
    }

    fun projectedPoint(from: EPoint): EPointMutable {
        val x = from.x
        val y = this[x]
        return x point y
    }

    fun toLine(length: Number, buffer: ELineMutable = ELineMutable()): ELineMutable {
        val x1 = 0f
        val x2 = length
        // TODO Make length work properly using pyth theorem
        return buffer.set(x1, this[x1], x2, this[x2])
    }
}