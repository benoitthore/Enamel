package com.thorebenoit.enamel.kotlin.geometry.layout

import com.fasterxml.jackson.annotation.JsonIgnore
import com.thorebenoit.enamel.kotlin.geometry.alignement.*
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ELayoutDataStore
import com.thorebenoit.enamel.kotlin.geometry.toRect

class EBarLayout(child: ELayout = ELayoutLeaf.unit, var side: ERectEdge = ERectEdge.top) : ELayoutAlongAxis {

    var child: ELayout = child
        set(value) {
            field = value
            _childLayouts.clear()
            _childLayouts.add(field)
        }

    override val layoutAxis: ELayoutAxis
        get() = side.layoutAxis


    private val _childLayouts: MutableList<ELayout> = mutableListOf(child)
    override val childLayouts: List<ELayout> get() = _childLayouts

    override fun size(toFit: ESizeType): ESizeType {
        return if (side.isHorizontal) {
            toFit.copy(height = child.size(toFit).height)
        } else {
            toFit.copy(width = child.size(toFit).width)
        }
    }

    override fun arrange(frame: ERectType) {
        val usingFrame = frame.rectAlignedInside(
            size = child.size(frame.size),
            aligned = side.alignement
        )
        child.arrange(usingFrame)
    }


    override fun serialize(dataStore: ELayoutDataStore) {
        dataStore.add(side)
        dataStore.add(child)
    }

    override fun deserialize(dataStore: ELayoutDataStore) {
        side = dataStore.readRectEdge()
        child = dataStore.readLayout()
    }

}