package com.benoitthore.enamel.geometry.layout

import com.benoitthore.enamel.geometry.alignement.EAlignment
import com.benoitthore.enamel.geometry.alignement.ELayoutAxis
import com.benoitthore.enamel.geometry.alignement.along
import com.benoitthore.enamel.geometry.alignement.layoutAxis
import com.benoitthore.enamel.geometry.figures.*


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