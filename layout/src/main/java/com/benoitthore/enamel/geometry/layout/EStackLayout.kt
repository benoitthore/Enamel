package com.benoitthore.enamel.geometry.layout

import com.benoitthore.enamel.geometry.alignement.EAlignment
import com.benoitthore.enamel.geometry.alignement.ELayoutAxis
import com.benoitthore.enamel.geometry.alignement.layoutAxis
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.figures.size.ESize
import com.benoitthore.enamel.geometry.figures.rectgroup.rectGroup
import com.benoitthore.enamel.geometry.interfaces.bounds.pointAtAnchor


class EStackLayout(
    override val children: MutableList<ELayout> = mutableListOf(),
    var alignment: EAlignment = EAlignment.topLeft,
    var spacing: Number = 0
) :
    ELayoutAlongAxis {

    override val layoutAxis: ELayoutAxis get() = alignment.layoutAxis ?: ELayoutAxis.horizontal


    override fun size(toFit: ESize): ESize {
        val group = children.map { it.size(toFit) }.rectGroup(alignment = alignment, spacing = spacing)

        return if (alignment.isVertical) {
            toFit.copy(height = group.size.height)
        } else {
            toFit.copy(width = group.size.width)
        }
    }

    override fun arrange(frame: ERect) {
        val sizes = children.map { it.size(frame.size) }
        val frames = sizes.rectGroup(
            alignment = alignment,
            anchor = alignment.flipped.namedPoint,
            position = frame.pointAtAnchor(alignment.flipped.namedPoint),
            spacing = spacing
        )
        children.zip(frames).forEach { (layout, rect) ->
            layout.arrange(rect)
        }
    }

    override fun toString(): String {
        return "EStackLayout(alignment=$alignment, spacing=$spacing, children=$children)"
    }


}