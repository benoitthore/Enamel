package com.thorebenoit.enamel.kotlin.geometry.layout

import com.thorebenoit.enamel.kotlin.core.color.black
import com.thorebenoit.enamel.kotlin.core.color.randomColor
import com.thorebenoit.enamel.kotlin.core.math.random
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.figures.size
import com.thorebenoit.enamel.kotlin.geometry.toRect

class EPlaceHolderLayout(val color: Int = randomColor()) : ELayout {
    var frame: ERectType = ERectType.zero


    override val childLayouts: List<ELayout> = emptyList()

    override fun size(toFit: ESizeType): ESizeType = toFit


    override fun arrange(frame: ERectType) {
        this.frame = frame
    }
}
