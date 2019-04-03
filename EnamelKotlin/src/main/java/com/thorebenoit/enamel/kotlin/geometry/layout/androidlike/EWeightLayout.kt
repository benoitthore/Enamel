package com.thorebenoit.enamel.kotlin.geometry.layout.androidlike

import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.alignement.layoutAxis
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.figures.rectGroupWeights
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout


// TODO Snugging doesn't work:
/*
Try this in ProcessingTestMain
5.of { ELayoutLeaf().sizedSquare(20) to random(2, 10) }.weighted(topLeft, spacing = 10).arrangeAndDraw()

 */

fun List<Pair<ELayout, Number>>.weighted(
    alignment: EAlignment = EAlignment.rightCenter,
    gravity: EAlignment = EAlignment.topLeft,
    snugging: ESnugging = ESnugging.wrap,
    spacing: Number = 0
) = EWeightLayout(
    weights = this,
    alignment = alignment,
    gravity = gravity,
    snugging = snugging,
    spacing = spacing
)


class EWeightLayout(
    val weights: List<Pair<ELayout, Number>>,
    val alignment: EAlignment,
    val gravity: EAlignment,
    val snugging: ESnugging = ESnugging.wrap,
    val spacing: Number
) : ELayout {
    override val childLayouts: List<ELayout> = weights.map { it.first }

    private val weightsMap = weights.toMap()
    private val ELayout.weight get() = weightsMap[this] ?: 1f


    override fun size(toFit: ESizeType): ESizeType {
        val group = childLayouts.map { it.weight }
            .rectGroupWeights(alignment = alignment, spacing = spacing, toFit = toFit)

        return ESizeType(
            width = if (snugging == ESnugging.wrap) Math.min(group.size.width, toFit.width) else toFit.width,
            height = if (snugging == ESnugging.wrap) Math.min(group.size.height, toFit.height) else toFit.height
        )
    }

    override fun arrange(frame: ERectType) {
        val frames = childLayouts.map { it.weight }
            .rectGroupWeights(
                alignment = alignment,
                toFit = frame.size,
                anchor = gravity.namedPoint,
                position = frame.pointAtAnchor(gravity.namedPoint),
                spacing = spacing
            )
        childLayouts.zip(frames).forEach { (layout, rect) ->
            layout.arrange(rect)
        }
    }
}