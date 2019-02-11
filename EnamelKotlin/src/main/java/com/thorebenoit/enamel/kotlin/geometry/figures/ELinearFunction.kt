package com.thorebenoit.enamel.kotlin.geometry.figures

import com.thorebenoit.enamel.kotlin.core.math.f
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType
import com.thorebenoit.enamel.kotlin.geometry.primitives.point

data class ELinearFunction(val slope: Float, val yIntercept: Float) {

    inline val a
        get() = slope
    inline val b
        get() = yIntercept

    constructor(slope: Number, yIntercept: Number) : this(slope.f, yIntercept.f)

    operator fun get(x: Number) = get(x.f)
    operator fun get(x: Float) = a * x + b

    fun getIntersection(other: ELinearFunction): EPoint? {
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

    fun projectedPoint(from: EPointType): EPoint {
        val x = from.x
        val y = this[x]
        return x point y
    }
}