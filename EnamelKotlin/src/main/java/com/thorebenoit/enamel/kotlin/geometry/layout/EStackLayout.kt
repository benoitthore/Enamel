package com.thorebenoit.enamel.kotlin.geometry.layout

import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.alignement.layoutAxis
import com.thorebenoit.enamel.kotlin.geometry.alignement.with
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.figures.rectGroup

class EStackLayout(override val childLayouts: List<ELayout>, val alignment: EAlignment, val spacing: Number = 0) :
    ELayout {
    override fun size(toFit: ESizeType): ESizeType {
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

}