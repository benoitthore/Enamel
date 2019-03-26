package com.thorebenoit.enamel.kotlin.geometry.layout

import com.thorebenoit.enamel.kotlin.geometry.alignement.ELayoutAxis
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ELayoutDataStore


interface ELayout {
    val childLayouts: List<ELayout>

    // TODO Refactor so these 2 functions don't allocate
    fun size(toFit: ESizeType): ESizeType

    fun arrange(frame: ERectType)

    fun serialize(dataStore: ELayoutDataStore)
    fun deserialize(dataStore: ELayoutDataStore)
}

interface ELayoutAlongAxis : ELayout {
    val layoutAxis: ELayoutAxis
}

val ELayout.unconstrainedSize get() = size(ESizeType.greatestSize)


