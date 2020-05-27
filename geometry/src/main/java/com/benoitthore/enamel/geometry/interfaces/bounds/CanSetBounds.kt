package com.benoitthore.enamel.geometry.interfaces.bounds

import com.benoitthore.enamel.geometry.primitives.point.EPoint

interface CanSetBounds<M : EShape<M, I>, I : EShapeMutable<M, I>> : HasBounds<M, I> {

    fun setBounds(
        left: Number = this.left,
        top: Number = this.top,
        right: Number = this.right,
        bottom: Number = this.bottom
    )

    override var width: Float
        get() = super.width
        set(value) {
            setBounds(
                right = right + value - width,
                top = top,
                left = left,
                bottom = bottom
            )
        }
    override var height: Float
        get() = super.height
        set(value) {
            setBounds(
                bottom = value - height,
                top = height,
                right = right,
                left = left
            )

        }

    fun setCenter(x: Number, y: Number)
    fun setCenter(center: EPoint) = setCenter(center.x, center.y)
}
