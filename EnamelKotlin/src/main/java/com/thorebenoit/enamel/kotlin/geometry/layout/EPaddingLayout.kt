package com.thorebenoit.enamel.kotlin.geometry.layout

import com.fasterxml.jackson.annotation.JsonIgnore
import com.thorebenoit.enamel.kotlin.geometry.figures.*
import com.thorebenoit.enamel.kotlin.geometry.primitives.*

class EPaddingLayout(child: ELayout = ELayoutLeaf.unit, var padding: EOffset = EOffset.zero) : ELayout {

    var child: ELayout = child
        set(value) {
            field = value
            childLayouts.clear()
            childLayouts.add(field)
        }


    override val childLayouts: MutableList<ELayout> = mutableListOf(child)

    override fun size(toFit: ESizeType): ESizeType {
        return child.size(toFit - padding) + padding
    }

    override fun arrange(frame: ERectType) {
        child.arrange(frame - padding)
    }
}