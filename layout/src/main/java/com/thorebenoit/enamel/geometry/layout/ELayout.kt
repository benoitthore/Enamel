package com.thorebenoit.enamel.geometry.layout

import com.thorebenoit.enamel.geometry.alignement.ELayoutAxis
import com.thorebenoit.enamel.geometry.figures.ERectType
import com.thorebenoit.enamel.geometry.figures.ESize


interface ELayout {
    val childLayouts: List<ELayout>

    // TODO Refactor so these 2 functions don't allocate
    fun size(toFit: ESize): ESize

    fun arrange(frame: ERectType)

}

interface ELayoutAlongAxis : ELayout {
    val layoutAxis: ELayoutAxis
}

val ELayout.unconstrainedSize get() = size(ESize.greatestSize)


