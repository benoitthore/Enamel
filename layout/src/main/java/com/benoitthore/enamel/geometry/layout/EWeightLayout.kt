package com.benoitthore.enamel.geometry.layout

import com.benoitthore.enamel.geometry.alignement.EAlignment
import com.benoitthore.enamel.geometry.alignement.ELayoutAxis
import com.benoitthore.enamel.geometry.alignement.layoutAxis
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.figures.ERectGroup
import com.benoitthore.enamel.geometry.figures.ESize
import com.benoitthore.enamel.geometry.figures.rectGroupWeights

class EWeightLayout(
    val alignment: EAlignment,
    override val childLayouts: List<ELayout>,
    val weights: List<Number>,
    val spacing: Number
) :
    ELayoutAlongAxis {

    override val layoutAxis: ELayoutAxis get() = alignment.layoutAxis ?: ELayoutAxis.horizontal

    override fun size(toFit: ESize): ESize = toFit

    override fun arrange(frame: ERect) {
        val group = rects(frame)
        childLayouts.zip(group)
            .forEach { (layout, rect) ->
                layout.arrange(rect)
            }
    }

    private fun rects(frame: ERect): ERectGroup {
        return weights.rectGroupWeights(
            alignment,
            toFit = frame.size,
            anchor = alignment.flipped.namedPoint,
            spacing = spacing,
            position = frame.pointAtAnchor(alignment.flipped.namedPoint)
        )
    }

    override fun toString(): String {
        return "EWeightLayout(alignment=$alignment, childLayouts=$childLayouts, weights=$weights, spacing=$spacing)"
    }


}