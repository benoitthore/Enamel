package com.benoitthore.enamel.geometry.layout

import com.benoitthore.enamel.geometry.alignement.EAlignment
import com.benoitthore.enamel.geometry.alignement.alignedInside
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.primitives.size.ESize

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
    override val children: List<ELayout> get() = _childLayouts

    override fun size(toFit: ESize): ESize {
        return if (snugged) {
            child.size(toFit)
        } else {
            toFit
        }
    }

    override fun arrange(frame: ERect) {

        val usingFrame = E.Rect(child.size(frame.size)).alignedInside(frame, alignment)
        child.arrange(usingFrame)
    }


    override fun toString(): String {
        return "EBoxLayout(alignment=$alignment, snugged=$snugged, child=$child)"
    }


}