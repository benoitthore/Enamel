package com.thorebenoit.enamel.kotlin.geometry.layout

import com.fasterxml.jackson.annotation.JsonIgnore
import com.thorebenoit.enamel.kotlin.core.color.randomColor
import com.thorebenoit.enamel.kotlin.geometry.alignement.ELayoutAxis
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ELayoutDataStore

class ELayoutLeaf(var color: Int = randomColor()) : ELayoutAlongAxis {

    override var layoutAxis: ELayoutAxis = ELayoutAxis.horizontal

    companion object {
        val unit = ELayoutLeaf(0)
    }

    var frame: ERectType = ERectType.zero


    override val childLayouts: List<ELayout> = listOf()

    override fun size(toFit: ESizeType): ESizeType = toFit


    override fun arrange(frame: ERectType) {
        this.frame = frame
    }

    override fun serialize(dataStore: ELayoutDataStore) {
        dataStore.add(color)
    }

    override fun deserialize(dataStore: ELayoutDataStore) {
        color = dataStore.readNumber().toInt()
    }

}
