package com.benoitthore.enamel.geometry.layout.dsl

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.geometry.layout.EPaddingLayout
import com.benoitthore.enamel.geometry.primitives.EOffset


fun ELayout.paddedHorizontally(value: Number) = padded(left = value, right = value)
fun ELayout.paddedVertically(value: Number) = padded(top = value, bottom = value)

fun ELayout.padded(offset: EOffset) = EPaddingLayout(this, offset)
fun ELayout.padded(all: Number) = EPaddingLayout(this, E.offset(all))
fun ELayout.padded(
    top: Number = 0,
    right: Number = 0,
    bottom: Number = 0,
    left: Number = 0
) = EPaddingLayout(this, E.offset(top = top, right = right, bottom = bottom, left = left))