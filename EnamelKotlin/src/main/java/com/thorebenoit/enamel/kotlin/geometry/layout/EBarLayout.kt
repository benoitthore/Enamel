package com.thorebenoit.enamel.kotlin.geometry.layout

import com.fasterxml.jackson.annotation.JsonIgnore
import com.thorebenoit.enamel.kotlin.geometry.alignement.*
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.toRect

class EBarLayout(child: ELayout = ELayoutLeaf.unit, var side: ERectEdge = ERectEdge.top) : ELayoutAlongAxis {

    var child: ELayout = child
        set(value) {
            field = value
            childLayouts.clear()
            childLayouts.add(field)
        }

    override val layoutAxis: ELayoutAxis
        get() = side.layoutAxis


    override val childLayouts: MutableList<ELayout> = mutableListOf(child)

    override fun size(toFit: ESizeType): ESizeType {
        return if (side.isHorizontal) {
            toFit.copy(height = child.size(toFit).height)
        } else {
            toFit.copy(width = child.size(toFit).width)
        }
    }

    override fun arrange(frame: ERectType) {
        val usingFrame = frame.rectAlignedInside(
            size = child.size(frame.size),
            aligned = side.alignement
        )
        child.arrange(usingFrame)
    }
}