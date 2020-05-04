package com.benoitthore.enamel.geometry.primitives.offset

import com.benoitthore.enamel.geometry.Resetable

interface EOffsetMutable : EOffset,
    Resetable {
    override var top: Float
    override var right: Float
    override var bottom: Float
    override var left: Float

    override fun reset() = set(0, 0, 0, 0)

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