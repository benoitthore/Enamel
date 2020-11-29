package com.benoitthore.enamel.geometry.layout

import com.benoitthore.enamel.geometry.builders.*
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.figures.rect.minus
import com.benoitthore.enamel.geometry.primitives.offset.EOffset
import com.benoitthore.enamel.geometry.primitives.plus
import com.benoitthore.enamel.geometry.primitives.sub


class EPaddingLayout(child: ELayout = ELayoutLeaf.unit, var padding: EOffset = Offset.zero()) : ELayout {

    var child: ELayout = child
        set(value) {
            field = value
            _childLayouts.clear()
            _childLayouts.add(field)
        }


    private val _childLayouts: MutableList<ELayout> = mutableListOf(child)
    override val children: List<ELayout> get() = _childLayouts

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