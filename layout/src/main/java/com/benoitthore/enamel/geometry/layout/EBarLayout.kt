package com.benoitthore.enamel.geometry.layout

import com.benoitthore.enamel.geometry.alignement.*
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.figures.ESize

class EBarLayout(child: ELayout, var side: ERectEdge) : ELayoutAlongAxis {

    var child: ELayout = child
        set(value) {
            field = value
            _childLayouts.clear()
            _childLayouts.add(field)
        }

    override val layoutAxis: ELayoutAxis
        get() = side.layoutAxis


    private val _childLayouts: MutableList<ELayout> = mutableListOf(child)
    override val children: List<ELayout> get() = _childLayouts

    override fun size(toFit: ESize): ESize {
        return if (side.isHorizontal) {
            toFit.copy(height = child.size(toFit).height)
        } else {
            toFit.copy(width = child.size(toFit).width)
        }
    }

    override fun arrange(frame: ERect) {
        val usingFrame = frame.rectAlignedInside(
            size = child.size(frame.size),
            aligned = side.alignement
        )
        child.arrange(usingFrame)
    }


}