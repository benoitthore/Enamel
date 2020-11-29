package com.benoitthore.enamel.geometry.layout

import com.benoitthore.enamel.geometry.alignement.ELayoutAxis
import com.benoitthore.enamel.geometry.builders.*
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.primitives.size.ESize


interface ELayout {
    val children: List<ELayout>

    // TODO Refactor so these 2 functions don't allocate
    fun size(toFit: ESize): ESize

    fun arrange(frame: ERect)
}

interface ELayoutAlongAxis : ELayout {
    val layoutAxis: ELayoutAxis
}

val ELayout.unconstrainedSize get() = size(Size.GreatestSize())


