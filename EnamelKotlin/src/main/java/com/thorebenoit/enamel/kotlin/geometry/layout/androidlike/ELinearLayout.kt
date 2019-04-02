package com.thorebenoit.enamel.kotlin.geometry.layout.androidlike

import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.figures.rectGroup
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.androidlike.ESnugging.*

class ELinearLayout(
    override val childLayouts: MutableList<ELayout>,
    val alignment: EAlignment,
    val gravity: EAlignment,
    var width: ESnugging,
    var height: ESnugging,
    val spacing: Number
) : ELayout {


    override fun size(toFit: ESizeType): ESizeType {
        val group = childLayouts.map { it.size(toFit) }.rectGroup(alignment = alignment, spacing = spacing)

        return ESizeType(
            width = if (width == wrap) Math.min(group.size.width, toFit.width) else toFit.width,
            height = if (height == wrap) Math.min(group.size.height, toFit.height) else toFit.height
        )
    }

    override fun arrange(frame: ERectType) {
        val sizes = childLayouts.map { it.size(frame.size) }
        val frames = sizes.rectGroup(
            alignment = alignment,
            anchor = gravity.namedPoint,
            position = frame.pointAtAnchor(gravity.namedPoint),
            spacing = spacing
        )
        childLayouts.zip(frames).forEach { (layout, rect) ->
            layout.arrange(rect)
        }
    }
}