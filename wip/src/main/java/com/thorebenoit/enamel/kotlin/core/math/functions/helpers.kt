package com.thorebenoit.enamel.kotlin.core.math.functions

import com.thorebenoit.enamel.geometry.primitives.ELinearFunction
import com.thorebenoit.enamel.geometry.primitives.EPoint

fun List<EPoint>.linearRegression(): ELinearFunction {
    if (size == 0) {
        return ELinearFunction(0, 0)
    }
    if (size == 1) {
        return ELinearFunction(0, first().y)
    }
    var xAvg = 0f
    var yAvg = 0f
    forEach { (x, y) ->
        xAvg += x
        yAvg += y
    }
    xAvg /= size
    yAvg /= size

    var num = 0f
    var den = 0f
    forEach { (x, y) ->
        num += (x - xAvg) * (y - yAvg)
        den += (x - xAvg) * (x - xAvg)
    }

    val m = num / den
    val b = yAvg - m * xAvg

    return ELinearFunction(m, b)

}