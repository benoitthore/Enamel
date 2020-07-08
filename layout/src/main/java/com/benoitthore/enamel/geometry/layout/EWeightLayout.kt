package com.benoitthore.enamel.geometry.layout

import com.benoitthore.enamel.geometry.alignement.EAlignment
import com.benoitthore.enamel.geometry.alignement.ELayoutAxis
import com.benoitthore.enamel.geometry.alignement.layoutAxis
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.figures.rectgroup.ERectGroup
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.figures.rectgroup.rectGroupWeights
import com.benoitthore.enamel.geometry.interfaces.bounds.pointAtAnchor
import com.benoitthore.enamel.geometry.interfaces.bounds.toRect

class EWeightLayout(
    val alignment: EAlignment,
    override val children: List<ELayout>,
    val weights: List<Number>,
    val spacing: Number
) :
    ELayoutAlongAxis {

    override val layoutAxis: ELayoutAxis get() = alignment.layoutAxis ?: ELayoutAxis.horizontal

    override fun size(toFit: ESize): ESize = toFit

    override fun arrange(frame: ERect) {
        val group = rects(frame)
        children.zip(group.rects)
            .forEach { (layout, rect) ->
                layout.arrange(rect.toRect()) // TODO Get rid off that
            }
    }

    private fun rects(frame: ERect): ERectGroup<ERect> {
        return weights.rectGroupWeights(
            alignment,
            toFit = frame.size,
            anchor = alignment.flipped.namedPoint,
            spacing = spacing,
            position = frame.pointAtAnchor(alignment.flipped.namedPoint)
        )
    }

    override fun toString(): String {
        return "EWeightLayout(alignment=$alignment, children=$children, weights=$weights, spacing=$spacing)"
    }


}