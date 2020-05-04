package com.benoitthore.enamel.geometry.primitives.offset

import com.benoitthore.enamel.geometry.primitives.Tuple2

interface EOffset : Tuple2 {
    val top: Float
    val right: Float
    val bottom: Float
    val left: Float

    override val v1: Number get() = horizontal
    override val v2: Number get() = vertical

    val horizontal get() = left + right
    val vertical get() = top + bottom
}

