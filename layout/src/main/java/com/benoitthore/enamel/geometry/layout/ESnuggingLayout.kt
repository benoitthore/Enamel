package com.benoitthore.enamel.geometry.layout

import com.benoitthore.enamel.geometry.alignement.isVertical
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.figures.ESize


class ESnuggingLayout(child: ELayoutAlongAxis) : ELayout {

    var child: ELayoutAlongAxis = child
        set(value) {
            field = value
            _childLayouts.clear()
            _childLayouts.add(field)
        }

    private val _childLayouts: MutableList<ELayout> = mutableListOf(child)
    override val children: List<ELayout> get() = _childLayouts

    override fun size(toFit: ESize): ESize {
        val sizes = child.children.map { it.size(toFit) }

        val maxChildExtent = sizes.map {
            if (child.layoutAxis.isVertical) {
                it.width
            } else {
                it.height
            }
        }.max() ?: 0f

        val unconstrainedSize = child.size(toFit)

        return if (child.layoutAxis.isVertical) {
            unconstrainedSize.copy(width = maxChildExtent)
        } else {
            unconstrainedSize.copy(height = maxChildExtent)
        }
    }

    override fun arrange(frame: ERect) {
        child.arrange(frame)
    }



    override fun toString(): String {
        return "ESnuggingLayout(child=$child)"
    }


}