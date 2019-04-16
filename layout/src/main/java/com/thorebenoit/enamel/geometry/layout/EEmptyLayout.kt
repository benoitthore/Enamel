package com.thorebenoit.enamel.geometry.layout

import com.thorebenoit.enamel.geometry.figures.ERect
import com.thorebenoit.enamel.geometry.figures.ESize


object EEmptyLayout : ELayout {


    override val childLayouts: List<ELayout> = emptyList()

    override fun size(toFit: ESize): ESize = ESize.zero
    override fun arrange(frame: ERect) {}

}