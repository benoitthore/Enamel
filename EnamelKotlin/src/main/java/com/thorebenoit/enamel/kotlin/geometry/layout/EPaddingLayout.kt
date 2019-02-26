package com.thorebenoit.enamel.kotlin.geometry.layout

import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.primitives.EOffset

class EPaddingLayout(val child: ELayout, val offset: EOffset) : ELayout {

    override val childLayouts: List<ELayout> = listOf(child)

    override fun size(toFit: ESizeType): ESizeType {
        return child.size(toFit) //- offset
    }

    override fun arrange(frame: ERectType) {

    }
}