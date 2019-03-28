package com.thorebenoit.enamel.kotlin.geometry.layout.refs

import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ELayoutDeserializer
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ELayoutSerializer

class ELayoutTag(var tag: String) : ELayout {
    override val childLayouts: List<ELayout> = emptyList()

    override fun size(toFit: ESizeType): ESizeType = toFit

    override fun arrange(frame: ERectType) {

    }

    override fun serialize(dataStore: ELayoutSerializer) {
        dataStore.add(tag)
    }

    override fun deserialize(dataStore: ELayoutDeserializer) {
        tag = dataStore.readString()
    }

}