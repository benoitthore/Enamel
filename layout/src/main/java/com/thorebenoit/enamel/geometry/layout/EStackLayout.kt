package com.thorebenoit.enamel.geometry.layout

import com.thorebenoit.enamel.geometry.alignement.EAlignment
import com.thorebenoit.enamel.geometry.alignement.ELayoutAxis
import com.thorebenoit.enamel.geometry.alignement.layoutAxis
import com.thorebenoit.enamel.geometry.figures.ERectType
import com.thorebenoit.enamel.geometry.figures.ESize
import com.thorebenoit.enamel.geometry.figures.rectGroup


class EStackLayout(
    override val childLayouts: MutableList<ELayout> = mutableListOf(),
    var alignment: EAlignment = EAlignment.topLeft,
    var spacing: Number = 0
) :
    ELayoutAlongAxis {

    override val layoutAxis: ELayoutAxis get() = alignment.layoutAxis ?: ELayoutAxis.horizontal


    override fun size(toFit: ESize): ESize {
        val group = childLayouts.map { it.size(toFit) }.rectGroup(alignment = alignment, spacing = spacing)

        if (alignment.isVertical) {
            return toFit.copy(height = group.size.height)
        } else {
            return toFit.copy(width = group.size.width)
        }
    }

    override fun arrange(frame: ERectType) {
        val sizes = childLayouts.map { it.size(frame.size) }
        val frames = sizes.rectGroup(
            alignment = alignment,
            anchor = alignment.flipped.namedPoint,
            position = frame.pointAtAnchor(alignment.flipped.namedPoint),
            spacing = spacing
        )
        childLayouts.zip(frames).forEach { (layout, rect) ->
            layout.arrange(rect)
        }
    }

    override fun toString(): String {
        return "EStackLayout(alignment=$alignment, spacing=$spacing, childLayouts=$childLayouts)"
    }


}