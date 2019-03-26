package com.thorebenoit.enamel.kotlin.geometry.layout

import com.thorebenoit.enamel.kotlin.geometry.figures.*
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ELayoutDataStore
import com.thorebenoit.enamel.kotlin.geometry.primitives.*

class EPaddingLayout(child: ELayout = ELayoutLeaf.unit, var padding: EOffset = EOffset.zero) : ELayout {

    var child: ELayout = child
        set(value) {
            field = value
            _childLayouts.clear()
            _childLayouts.add(field)
        }


    private val _childLayouts: MutableList<ELayout> = mutableListOf(child)
    override val childLayouts: List<ELayout> get() = _childLayouts

    override fun size(toFit: ESizeType): ESizeType {
        return child.size(toFit - padding) + padding
    }

    override fun arrange(frame: ERectType) {
        child.arrange(frame - padding)
    }

    override fun serialize(dataStore: ELayoutDataStore) {
        dataStore.add(padding)
        dataStore.add(child)
    }

    override fun deserialize(dataStore: ELayoutDataStore) {
        padding = dataStore.readOffset()
        child = dataStore.readLayout()
    }
}