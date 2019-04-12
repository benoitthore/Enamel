package com.thorebenoit.enamel.geometry.layout.dsl

import com.thorebenoit.enamel.geometry.layout.ELayoutAlongAxis
import com.thorebenoit.enamel.geometry.layout.ESnuggingLayout

fun ELayoutAlongAxis.snugged() = ESnuggingLayout(this)