package com.thorebenoit.enamel.geometry.layout.dsl

import com.thorebenoit.enamel.geometry.layout.ELayout
import com.thorebenoit.enamel.geometry.layout.EPaddingLayout
import com.thorebenoit.enamel.geometry.primitives.EOffset

fun ELayout.padded(offset: EOffset) = EPaddingLayout(this, offset)
fun ELayout.padded(all: Number) = EPaddingLayout(this, EOffset(all))
fun ELayout.padded(
    top: Number = 0,
    right: Number = 0,
    bottom: Number = 0,
    left: Number = 0
) = EPaddingLayout(this, EOffset(top = top, right = right, bottom = bottom, left = left))