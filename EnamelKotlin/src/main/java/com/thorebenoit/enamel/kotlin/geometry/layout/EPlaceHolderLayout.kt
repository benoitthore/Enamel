package com.thorebenoit.enamel.kotlin.geometry.layout

import com.thorebenoit.enamel.kotlin.core.color.black
import com.thorebenoit.enamel.kotlin.core.color.randomColor
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.figures.size
import com.thorebenoit.enamel.kotlin.geometry.toRect

class EPlaceHolderLayout(val color: Int, var frame: ERectType = ERectType.zero) : ELayout {

    constructor(color: Int, size: ESizeType) : this(color, size.toRect())

    override val childLayouts: List<ELayout> = emptyList()

    override fun size(toFit: ESizeType): ESizeType {
        val w = Math.min(toFit.width, frame.width)
        val h = Math.min(toFit.height, frame.height)
        return w size h
    }


    override fun arrange(frame: ERectType) {
        this.frame = frame
    }
}

fun ERectType.toPlaceHolder(color: Int = randomColor()): EPlaceHolderLayout = EPlaceHolderLayout(color, this)
fun ESizeType.toPlaceHolder(color: Int = randomColor()): EPlaceHolderLayout = EPlaceHolderLayout(color, toRect())