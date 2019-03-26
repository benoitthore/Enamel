package com.thorebenoit.enamel.kotlin.geometry.layout

import com.fasterxml.jackson.annotation.JsonIgnore
import com.thorebenoit.enamel.kotlin.core.color.randomColor
import com.thorebenoit.enamel.kotlin.geometry.alignement.ELayoutAxis
import com.thorebenoit.enamel.kotlin.geometry.figures.ERect
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ELayoutDeserializer
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ELayoutSerializer

class ELayoutLeaf(var color: Int = randomColor()) : ELayoutAlongAxis {

    override var layoutAxis: ELayoutAxis = ELayoutAxis.horizontal

    companion object {
        val unit = ELayoutLeaf(0)
    }

    val _frame: ERect = ERect()
    val frame: ERectType = _frame


    override val childLayouts: List<ELayout> = listOf()

    override fun size(toFit: ESizeType): ESizeType = toFit


    override fun arrange(frame: ERectType) {
        _frame.set(frame)
    }

    override fun serialize(dataStore: ELayoutSerializer) {
        dataStore.add(color)
    }

    override fun deserialize(dataStore: ELayoutDeserializer) {
        color = dataStore.readNumber().toInt()
    }

    override fun toString(): String {
        return "ELayoutLeaf(color=$color, frame=$frame)"
    }


}
