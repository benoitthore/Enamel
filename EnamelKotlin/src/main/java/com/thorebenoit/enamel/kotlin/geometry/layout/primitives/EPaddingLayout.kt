package com.thorebenoit.enamel.kotlin.geometry.layout.primitives

import com.thorebenoit.enamel.kotlin.geometry.figures.*
import com.thorebenoit.enamel.kotlin.geometry.primitives.*

data class EPaddingLayout(val child: ELayout, val padding: EOffset) :
    ELayout {

    override val childLayouts: List<ELayout> = listOf(child)

    override fun size(toFit: ESizeType): ESizeType {
        return child.size(toFit - padding) + padding
    }

    override fun arrange(frame: ERectType) {
        child.arrange(frame - padding)
    }
}