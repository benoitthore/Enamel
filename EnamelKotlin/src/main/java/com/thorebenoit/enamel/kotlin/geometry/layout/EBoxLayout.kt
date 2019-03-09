package com.thorebenoit.enamel.kotlin.geometry.layout

import com.fasterxml.jackson.annotation.JsonIgnore
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.toRect

data class EBoxLayout(@get:JsonIgnore val child: ELayout, val alignment: EAlignment, val snugged: Boolean = false) : ELayout {
    override val childLayouts: List<ELayout> = listOf(child)

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
}