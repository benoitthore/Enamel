package com.thorebenoit.enamel.kotlin.geometry.layout

import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.alignement.ELayoutAxis
import com.thorebenoit.enamel.kotlin.geometry.alignement.layoutAxis
import com.thorebenoit.enamel.kotlin.geometry.alignement.with
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.toRect

// TODO Refactor to use its own Alignment instead of EAlignment
class EBarLayout(val child: ELayout, val alignment: EAlignment) : ELayoutAlongAxis {

    override val layoutAxis: ELayoutAxis get() = alignment.layoutAxis ?: ELayoutAxis.horizontal// TODO Fix this

    override val childLayouts: List<ELayout> = listOf(child)

    override fun size(toFit: ESizeType): ESizeType {
        return if (alignment.isHorizontal) {
            toFit.copy(height = child.size(toFit).height)
        } else {
            toFit.copy(width = child.size(toFit).width)
        }
    }

    override fun arrange(frame: ERectType) {
        val usingFrame = frame.rectAlignedInside(
            size = child.size(frame.size),
            aligned = alignment
        )
        child.arrange(usingFrame)
    }
}