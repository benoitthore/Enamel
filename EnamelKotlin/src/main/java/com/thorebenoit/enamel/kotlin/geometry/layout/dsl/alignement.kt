package com.thorebenoit.enamel.kotlin.geometry.layout.dsl

import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.alignement.ERectEdge
import com.thorebenoit.enamel.kotlin.geometry.layout.EBarLayout
import com.thorebenoit.enamel.kotlin.geometry.layout.EBoxLayout
import com.thorebenoit.enamel.kotlin.geometry.layout.EDivideLayout
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout


fun ELayout.aligned(side: ERectEdge) = EBarLayout(this, side)

fun ELayout.aligned(
    side: ERectEdge,
    of: ELayout,
    sizedBy: EDivideLayout.Division = EDivideLayout.Division.Slice,
    spacing: Number = 0,
    snugged: Boolean = true
) = EDivideLayout(
    slice = this,
    edge = side,
    remainder = of,
    by = sizedBy,
    spacing = spacing,
    snugged = snugged
)


fun ELayout.arranged(alignement: EAlignment, snugged: Boolean = true) = EBoxLayout(this, alignement, snugged)