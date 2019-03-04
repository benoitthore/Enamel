package com.thorebenoit.enamel.kotlin.geometry.layout.dsl

import com.thorebenoit.enamel.kotlin.geometry.layout.ELayoutAlongAxis
import com.thorebenoit.enamel.kotlin.geometry.layout.ESnuggingLayout

fun ELayoutAlongAxis.snugged() = ESnuggingLayout(this)