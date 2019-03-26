package com.thorebenoit.enamel.kotlin.geometry.layout

import com.fasterxml.jackson.annotation.JsonIgnore
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ELayoutDeserializer
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ELayoutSerializer

import com.thorebenoit.enamel.kotlin.geometry.toRect

class EBoxLayout(
    child: ELayout = ELayoutLeaf.unit,
    var alignment: EAlignment = EAlignment.topLeft,
    var snugged: Boolean = false
) : ELayout {

    var child: ELayout = child
        set(value) {
            field = value
            _childLayouts.clear()
            _childLayouts.add(field)
        }
    private val _childLayouts: MutableList<ELayout> = mutableListOf(child)
    override val childLayouts: List<ELayout> get() = _childLayouts

    override fun size(toFit: ESizeType): ESizeType {
        return if (snugged) {
            child.size(toFit)
        } else {
            toFit
        }
    }

    override fun arrange(frame: ERectType) {
        val usingFrame = frame.rectAlignedInside(
            aligned = alignment,
            size = child.size(frame.size)
        )
        child.arrange(usingFrame)
    }


    override fun serialize(dataStore: ELayoutSerializer) {
        dataStore.add(alignment)
        dataStore.add(snugged)
        dataStore.add(child)
    }

    override fun deserialize(dataStore: ELayoutDeserializer) {
        alignment = dataStore.readAlignment()
        snugged = dataStore.readBool()
        child = dataStore.readLayout()
    }
}