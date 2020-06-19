package com.benoitthore.enamel.geometry.interfaces.bounds

import com.benoitthore.enamel.geometry.Resetable
import com.benoitthore.enamel.geometry.primitives.point.EPoint

interface CanSetBounds<I, M> : HasBounds<I, M>,
    Resetable where I : HasBounds<I, M>, M : CanSetBounds<I, M> {

    fun setBounds(
        left: Number = this.left,
        top: Number = this.top,
        right: Number = this.right,
        bottom: Number = this.bottom
    )

    override var centerX: Float
    override var centerY: Float

    override var width: Float
    override var height: Float
    fun setCenter(x: Number, y: Number) {
        TODO()
    }

    override fun reset() {
        setBounds(0, 0, 0, 0)
    }

    fun setCenter(center: EPoint) = setCenter(center.x, center.y)


}
