package com.thorebenoit.enamel.kotlin.geometry.layout.dsl

import com.thorebenoit.enamel.kotlin.core.color.*
import com.thorebenoit.enamel.kotlin.core.data.asList
import com.thorebenoit.enamel.kotlin.core.of
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment.*
import com.thorebenoit.enamel.kotlin.geometry.alignement.ERectEdge.*
import com.thorebenoit.enamel.kotlin.geometry.alignement.*
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.layout.EDivideLayout
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayoutLeaf
import com.thorebenoit.enamel.kotlin.geometry.layout.androidlike.dsl.stacked

typealias ELayoutExample = () -> ELayout


object ELayoutDemo {
    val _3: ELayoutExample = {

        val slice: ELayout = 5.of { ELayoutLeaf(red).sizedSquare((it + 1) * 10) }
            .stacked(
                alignment = bottomRight,
                gravity = topLeft,
//                    width = ESnugging.fill,
//                    height = 200,
                spacing = 10
            )
            .padded(20)
            .leaf()

        val remainder: ELayout = 4.of { ELayoutLeaf(blue).sizedSquare((it + 1) * 50) }
            .stacked(
                alignment = bottomCenter,
                gravity = topLeft,
//                    width = ESnugging.fill,
//                    height = 200,
                spacing = 10
            )
            .padded(10)
            .leaf()

        val layout: ELayout = remainder
            .aligned(left, of = slice, sizedBy = EDivideLayout.Division.Fraction(0.33))
            .arranged(middle)

        layout
    }
    val _2: ELayoutExample = {

        3.of { ELayoutLeaf(red) }
            .mapIndexed { i, layout ->
                layout.sizedSquare((i + 1) * 100)
            }
            .stacked(rightTop, spacing = 10)
            .arranged(topLeft)
            .padded(20)
    }
    val _1: ELayoutExample = {
        val redLayout = ELayoutLeaf(red)
        val blueLayout = ELayoutLeaf(blue)

        val sliceLayout = redLayout.sizedSquare(100)
            .aligned(top)

        val combinedLayout = sliceLayout.aligned(left, of = blueLayout, spacing = 5)

        combinedLayout
            .scaled(0.5, 0.25)
            .padded(10)
            .arranged(leftCenter)

        listOf(redLayout, blueLayout).stacked(EAlignment.topRight)
    }

}
