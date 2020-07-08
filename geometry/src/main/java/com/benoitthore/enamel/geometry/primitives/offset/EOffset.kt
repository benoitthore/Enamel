package com.benoitthore.enamel.geometry.primitives.offset

import com.benoitthore.enamel.geometry.interfaces.bounds.Copyable
import com.benoitthore.enamel.geometry.primitives.Tuple2

interface EOffset : Tuple2, Copyable<EOffset> {

    var top: Float
    var right: Float
    var bottom: Float
    var left: Float

    override val v1: Number get() = horizontal
    override val v2: Number get() = vertical

    val horizontal get() = left + right
    val vertical get() = top + bottom

    fun set(
        top: Number = this.top,
        right: Number = this.right,
        bottom: Number = this.bottom,
        left: Number = this.left
    ) {
        this.top = top.toFloat()
        this.right = right.toFloat()
        this.bottom = bottom.toFloat()
        this.left = left.toFloat()
    }

}

