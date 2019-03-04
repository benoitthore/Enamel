package com.thorebenoit.enamel.kotlin.geometry.layout.dsl

import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.alignement.ERectEdge
import com.thorebenoit.enamel.kotlin.geometry.layout.EBarLayout
import com.thorebenoit.enamel.kotlin.geometry.layout.EBoxLayout
import com.thorebenoit.enamel.kotlin.geometry.layout.EDivideLayout
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout


// TODO Refactor to use bar alignment, this causes issues
/*
                val slice = EPlaceHolderLayout(red)
                val remainder = EPlaceHolderLayout(blue)

                val sliceLayout = slice.sizedSquare(100).aligned(EAlignment.topLeft) // TODO If uses topLeft or bottomLeft, there's differences

                val divideLayout = sliceLayout.aligned(ERectEdge.left, of = remainder, spacing = 5)

                val layout = divideLayout
//                    .scaled(0.5, 0.25)
//                    .fitting(100,100)
                    .width(200)
//                    .padded(10)
                    .arranged(EAlignment.middle)

                layout.arrange(eframe)

                slice.draw()
                remainder.draw()
 */
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


fun ELayout.arranged(alignement: EAlignment, snugged: Boolean = false) = EBoxLayout(this, alignement, snugged)