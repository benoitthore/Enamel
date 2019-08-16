package com.benoitthore.enamel.geometry.layout

import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.figures.ESize


object EEmptyLayout : ELayout {


    override val childLayouts: List<ELayout> = emptyList()

    override fun size(toFit: ESize): ESize = ESize.zero
    override fun arrange(frame: ERect) {}

}