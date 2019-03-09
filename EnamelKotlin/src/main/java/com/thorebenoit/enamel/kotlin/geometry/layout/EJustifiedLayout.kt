package com.thorebenoit.enamel.kotlin.geometry.layout

import com.fasterxml.jackson.annotation.JsonIgnore
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.alignement.ELayoutAxis
import com.thorebenoit.enamel.kotlin.geometry.alignement.along
import com.thorebenoit.enamel.kotlin.geometry.alignement.layoutAxis
import com.thorebenoit.enamel.kotlin.geometry.figures.*

data class EJustifiedLayout(override val childLayouts: List<ELayout>, val alignment: EAlignment) : ELayoutAlongAxis {
    @get:JsonIgnore
    override val layoutAxis: ELayoutAxis = alignment.layoutAxis ?: ELayoutAxis.horizontal

    override fun size(toFit: ESizeType): ESizeType = toFit

    override fun arrange(frame: ERectType) {
        val group = rects(frame)
        childLayouts.zip(group)
            .forEach { (layout, rect) ->
                layout.arrange(rect)
            }
    }

    private fun rects(frame: ERectType): ERectGroup {
        val toFIt = alignment.layoutAxis?.let { frame.size.along(it) } ?: frame.size.min

        return sizes(frame.size).rectGroupJustified(
            alignment = alignment,
            toFit = toFIt,
            anchor = alignment.flipped.namedPoint,
            position = frame.pointAtAnchor(alignment.flipped.namedPoint)
        )
    }

    private fun sizes(toFit: ESizeType): List<ESizeType> {
        return childLayouts.map { it.size(toFit) }
    }
}