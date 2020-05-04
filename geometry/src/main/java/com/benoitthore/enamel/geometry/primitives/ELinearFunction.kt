package com.benoitthore.enamel.geometry.primitives

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.allocateDebugMessage
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.line.ELineMutable

interface ELinearFunction {

    class Impl internal constructor(override val slope: Float, override val yIntercept: Float) :
        ELinearFunction {
        init {
            allocateDebugMessage()
        }
    }

    val slope: Float
    val yIntercept: Float

    val a get() = slope
    val b get() = yIntercept

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

    fun toLine(length: Number, target: ELineMutable = E.LineMutable()): ELineMutable {
        val x1 = 0f
        val x2 = length
        // TODO Make length work properly using pyth theorem
        return target.set(x1, this[x1], x2, this[x2])
    }
}