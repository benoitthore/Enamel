package com.thorebenoit.enamel.kotlin.core.math

import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType


fun List<EPointType>.toBSpline() =
    BezierSplineCalculator.getCurveControlEPointTypesTriple(this)

private object BezierSplineCalculator {

    /*
    Return a B-Spline with a triple like: point, control1, control2
     */
    fun getCurveControlEPointTypesTriple(knots: List<EPointType>): List<Triple<EPointType?, EPointType?, EPointType>> {
        val ret = mutableListOf<Triple<EPointType?, EPointType?, EPointType>>()
        val n = knots.size - 1
        if (n < 1) {
            return ret
        }

        // Calculate first Bezier control points
        // Right hand side vector
        val rhs = FloatArray(n)

        // Set right hand side X values
        for (i in 1 until n - 1)
            rhs[i] = (4 * knots[i].x + 2 * knots[i + 1].x)
        rhs[0] = (knots[0].x + 2 * knots[1].x)
        rhs[n - 1] = (3 * knots[n - 1].x)
        // Get first control points X-values
        val x = GetFirstControlPoint(rhs)

        // Set right hand side Y values
        for (i in 1 until n - 1)
            rhs[i] = (4 * knots[i].y + 2 * knots[i + 1].y)
        rhs[0] = (knots[0].y + 2 * knots[1].y)
        rhs[n - 1] = (3 * knots[n - 1].y)
        // Get first control points Y-values
        val y = GetFirstControlPoint(rhs)

        ret.add(Triple(null, null, knots.first()))
        // Calculate points
        for (i in 0 until n) {

            // First control point
            val first = EPointType(x[i], y[i])

            // Second control point
            val second = if (i < n - 1)
                EPointType(2 * knots[i + 1].x - x[i + 1], 2 * knots[i + 1].y - y[i + 1])
            else
                EPointType((knots[n].x + x[n - 1]) / 2, (knots[n].y + y[n - 1]) / 2)

            // data points
            val data = knots[i + 1]
            ret.add(Triple(data, first, second))
        }

        return ret
    }

    /**
    Solves a tridiagonal system for one of coordinates (x or y) of first Bezier control points.
    @params rhs : Right hand side vector
    @returns Solution vector
     */
    private fun GetFirstControlPoint(rhs: FloatArray): FloatArray {
        val n = rhs.size
        val x = FloatArray(n) // Solution vector.
        val tmp = FloatArray(n) // Temp workspace.

        var b = 2f
        x[0] = rhs[0] / b
        for (i in 1 until n)
        // Decomposition and forward substitution.
        {
            tmp[i] = 1 / b
            b = (if (i < n - 1) 4f else 2f) - tmp[i]
            x[i] = (rhs[i] - x[i - 1]) / b
        }
        for (i in 1 until n)
            x[n - i - 1] -= tmp[n - i] * x[n - i] // Backsubstitution.
        return x
    }
}