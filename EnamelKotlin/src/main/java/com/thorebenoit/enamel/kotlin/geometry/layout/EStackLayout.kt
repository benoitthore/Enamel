package com.thorebenoit.enamel.kotlin.geometry.layout

import com.fasterxml.jackson.annotation.JsonIgnore
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.alignement.ELayoutAxis
import com.thorebenoit.enamel.kotlin.geometry.alignement.layoutAxis
import com.thorebenoit.enamel.kotlin.geometry.alignement.with
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.figures.rectGroup
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ELayoutDeserializer
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ELayoutSerializer


class EStackLayout(
    override val childLayouts: MutableList<ELayout> = mutableListOf(),
    var alignment: EAlignment = EAlignment.topLeft,
    var spacing: Number = 0
) :
    ELayoutAlongAxis {

    override val layoutAxis: ELayoutAxis get() = alignment.layoutAxis ?: ELayoutAxis.horizontal


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

    override fun serialize(dataStore: ELayoutSerializer) {
        dataStore.add(alignment)
        dataStore.add(spacing)
        dataStore.add(childLayouts)
    }

    override fun deserialize(dataStore: ELayoutDeserializer) {
        alignment = dataStore.readAlignment()
        spacing = dataStore.readNumber()
        childLayouts.clear()
        childLayouts.addAll(dataStore.readLayouts())
    }

    override fun toString(): String {
        return "EStackLayout(alignment=$alignment, spacing=$spacing, childLayouts=$childLayouts)"
    }


}