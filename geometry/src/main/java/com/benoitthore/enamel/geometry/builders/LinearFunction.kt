package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.primitives.linearfunction.ELinearFunction

fun LinearFunction(slope: Number = 0f, yIntercept: Number = 0f): ELinearFunction =
    ELinearFunction.Impl(slope.toFloat(), yIntercept.toFloat())