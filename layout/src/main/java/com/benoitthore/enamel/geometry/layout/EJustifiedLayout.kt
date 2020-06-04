package com.benoitthore.enamel.geometry.layout

import com.benoitthore.enamel.geometry.alignement.EAlignment
import com.benoitthore.enamel.geometry.alignement.ELayoutAxis
import com.benoitthore.enamel.geometry.alignement.layoutAxis
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.figures.rect.ERectMutable
import com.benoitthore.enamel.geometry.figures.rectgroup.ERectGroup
import com.benoitthore.enamel.geometry.figures.rectgroup.rectGroupJustified
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.size.along
import com.benoitthore.enamel.geometry.interfaces.bounds.pointAtAnchor
import com.benoitthore.enamel.geometry.interfaces.bounds.toRect


class EJustifiedLayout(
    override val children: MutableList<ELayout>,
    var alignment: EAlignment
) : ELayoutAlongAxis {

    override val layoutAxis: ELayoutAxis = alignment.layoutAxis ?: ELayoutAxis.horizontal

    override fun size(toFit: ESize): ESize = toFit

    override fun arrange(frame: ERect) {
        val group = rects(frame)
        children.zip(group)
            .forEach { (layout, rect) ->
                layout.arrange(rect.toRect()) // TODO Get rid off that
            }
    }

    private fun rects(frame: ERect): ERectGroup<*, *, *> {
        val toFit = alignment.layoutAxis?.let { frame.size.along(it) } ?: frame.size.min

        return sizes(frame.size).rectGroupJustified(
            alignment = alignment,
            toFit = toFit,
            anchor = alignment.flipped.namedPoint,
            position = frame.pointAtAnchor(alignment.flipped.namedPoint)
        )
    }

    private fun sizes(toFit: ESize): List<ESize> {
        return children.map { it.size(toFit) }
    }


    override fun toString(): String {
        return "EJustifiedLayout(alignment=$alignment, layoutAxis=$layoutAxis, children=$children)"
    }


}