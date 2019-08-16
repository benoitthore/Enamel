package com.benoitthore.enamel.geometry.layout.dsl

import com.benoitthore.enamel.geometry.layout.ELayoutAlongAxis
import com.benoitthore.enamel.geometry.layout.ESnuggingLayout

fun ELayoutAlongAxis.snugged() = ESnuggingLayout(this)