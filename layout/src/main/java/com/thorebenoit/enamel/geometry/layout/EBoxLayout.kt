package com.thorebenoit.enamel.geometry.layout

import com.thorebenoit.enamel.geometry.alignement.EAlignment
import com.thorebenoit.enamel.geometry.figures.ERect
import com.thorebenoit.enamel.geometry.figures.ESize
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayoutLeaf

class EBoxLayout(
    child: ELayout = ELayoutLeaf.unit,
    var alignment: EAlignment = EAlignment.topLeft,
    var snugged: Boolean = true
) : ELayout {

    var child: ELayout = child
        set(value) {
            field = value
            _childLayouts.clear()
            _childLayouts.add(field)
        }
    private val _childLayouts: MutableList<ELayout> = mutableListOf(child)
    override val childLayouts: List<ELayout> get() = _childLayouts

    override fun size(toFit: ESize): ESize {
        return if (snugged) {
            child.size(toFit)
        } else {
            toFit
        }
    }

    override fun arrange(frame: ERect) {
        val usingFrame = frame.rectAlignedInside(
            aligned = alignment,
            size = child.size(frame.size)
        )
        child.arrange(usingFrame)
    }


    override fun toString(): String {
        return "EBoxLayout(alignment=$alignment, snugged=$snugged, child=$child)"
    }


}