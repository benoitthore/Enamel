package com.thorebenoit.enamel.geometry.layout

import com.thorebenoit.enamel.geometry.figures.ERect
import com.thorebenoit.enamel.geometry.figures.ESize
import com.thorebenoit.enamel.geometry.figures.minus
import com.thorebenoit.enamel.geometry.primitives.EOffset
import com.thorebenoit.enamel.geometry.primitives.plus
import com.thorebenoit.enamel.geometry.primitives.sub


class EPaddingLayout(child: ELayout = ELayoutLeaf.unit, var padding: EOffset = EOffset.zero) : ELayout {

    var child: ELayout = child
        set(value) {
            field = value
            _childLayouts.clear()
            _childLayouts.add(field)
        }


    private val _childLayouts: MutableList<ELayout> = mutableListOf(child)
    override val childLayouts: List<ELayout> get() = _childLayouts

    override fun size(toFit: ESize): ESize {
        return child.size(toFit.sub(padding)) + padding
    }

    override fun arrange(frame: ERect) {
        child.arrange(frame - padding)
    }

    override fun toString(): String {
        return "EPaddingLayout(padding=$padding, child=$child)"
    }


}