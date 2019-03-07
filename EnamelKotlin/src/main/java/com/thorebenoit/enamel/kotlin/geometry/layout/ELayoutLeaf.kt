package com.thorebenoit.enamel.kotlin.geometry.layout

import com.fasterxml.jackson.annotation.JsonIgnore
import com.thorebenoit.enamel.kotlin.core.color.randomColor
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType

class ELayoutLeaf(val color: Int = randomColor()) : ELayout {
    @get:JsonIgnore
    var frame: ERectType = ERectType.zero


    override val childLayouts: List<ELayout> = emptyList()

    override fun size(toFit: ESizeType): ESizeType = toFit


    override fun arrange(frame: ERectType) {
        this.frame = frame
    }
}
