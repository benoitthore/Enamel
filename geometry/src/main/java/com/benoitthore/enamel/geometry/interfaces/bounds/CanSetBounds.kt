package com.benoitthore.enamel.geometry.interfaces.bounds

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.interfaces.CanSetCenter

interface CanSetBounds : HasBounds,
    CanSetCenter {

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
}
