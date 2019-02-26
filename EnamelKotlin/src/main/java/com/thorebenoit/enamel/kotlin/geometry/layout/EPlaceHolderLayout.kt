package com.thorebenoit.enamel.kotlin.geometry.layout

import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType

class EPlaceHolderLayout(val color: Int, var frame: ERectType? = null) : ELayout {

    override val childLayouts: List<ELayout> = emptyList()

    override fun size(toFit: ESizeType): ESizeType {
        return toFit // TODO Use frame?
    }


    override fun arrange(frame: ERectType) {
        this.frame = frame
    }
}