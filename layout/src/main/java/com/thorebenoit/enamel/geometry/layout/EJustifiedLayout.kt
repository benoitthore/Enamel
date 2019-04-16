package com.thorebenoit.enamel.geometry.layout

import com.thorebenoit.enamel.geometry.alignement.EAlignment
import com.thorebenoit.enamel.geometry.alignement.ELayoutAxis
import com.thorebenoit.enamel.geometry.alignement.along
import com.thorebenoit.enamel.geometry.alignement.layoutAxis
import com.thorebenoit.enamel.geometry.figures.ERectGroup
import com.thorebenoit.enamel.geometry.figures.ERect
import com.thorebenoit.enamel.geometry.figures.ESize
import com.thorebenoit.enamel.geometry.figures.rectGroupJustified


class EJustifiedLayout(
    override val childLayouts: MutableList<ELayout>,
    var alignment: EAlignment
) : ELayoutAlongAxis {

    override val layoutAxis: ELayoutAxis = alignment.layoutAxis ?: ELayoutAxis.horizontal

    override fun size(toFit: ESize): ESize = toFit

    override fun arrange(frame: ERect) {
        val group = rects(frame)
        childLayouts.zip(group)
            .forEach { (layout, rect) ->
                layout.arrange(rect)
            }
    }

    private fun rects(frame: ERect): ERectGroup {
        val toFit = alignment.layoutAxis?.let { frame.size.along(it) } ?: frame.size.min

        return sizes(frame.size).rectGroupJustified(
            alignment = alignment,
            toFit = toFit,
            anchor = alignment.flipped.namedPoint,
            position = frame.pointAtAnchor(alignment.flipped.namedPoint)
        )
    }

    private fun sizes(toFit: ESize): List<ESize> {
        return childLayouts.map { it.size(toFit) }
    }


    override fun toString(): String {
        return "EJustifiedLayout(alignment=$alignment, layoutAxis=$layoutAxis, childLayouts=$childLayouts)"
    }


}