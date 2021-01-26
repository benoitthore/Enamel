package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.primitives.linearfunction.ELinearFunction
import com.benoitthore.enamel.geometry.primitives.linearfunction.ELinearFunctionImpl
import com.benoitthore.enamel.geometry.primitives.linearfunction.ELinearFunctionMutable

fun LinearFunction(slope: Number = 0f, yIntercept: Number = 0f): ELinearFunction =
    ELinearFunctionImpl(slope.toFloat(), yIntercept.toFloat())


fun MutableLinearFunction(slope: Number = 0f, yIntercept: Number = 0f): ELinearFunctionMutable =
    ELinearFunctionImpl(slope.toFloat(), yIntercept.toFloat())