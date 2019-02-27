package com.thorebenoit.enamel.kotlin.geometry.layout

import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.toRect

class EBoxLayout(val child: ELayout, val alignment: EAlignment, val snugged: Boolean = false) : ELayout {
    override val childLayouts: List<ELayout> = listOf(child)

    override fun size(toFit: ESizeType): ESizeType {
        return if (snugged) {
            child.size(toFit)
        } else {
            toFit
        }
    }

    override fun arrange(frame: ERectType) {
        val size = child.size(frame.size)
        val usingFrame = frame.rectAlignedInside(alignment, size)
        child.arrange(usingFrame)
    }
}