package com.benoitthore.enamel.geometry.layout

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.primitives.size.ESize


object EEmptyLayout : ELayout {
    override val children: List<ELayout> get() = emptyList()

    override fun size(toFit: ESize): ESize = E.Size.zero
    override fun arrange(frame: ERect) {}
}

fun emptyLayout() = EEmptyLayout