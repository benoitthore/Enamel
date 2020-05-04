package com.benoitthore.enamel.geometry.interfaces.bounds

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.figures.rect.ERectMutable
import com.benoitthore.enamel.geometry.figures.size.ESize
import com.benoitthore.enamel.geometry.interfaces.CanSetCenter
import com.benoitthore.enamel.geometry.primitives.EOffset
import com.benoitthore.enamel.geometry.primitives.EPoint
import com.benoitthore.enamel.geometry.primitives.Tuple2

interface CanSetBounds : HasBounds,
    CanSetCenter {

    fun setBounds(
        left: Number,
        top: Number,
        right: Number,
        bottom: Number
    )

    fun setOriginSize(
        originX: Number = this.originX, originY: Number = this.originY,
        width: Number = this.width, height: Number = this.height
    ) = setBounds(
        left = originX,
        top = originY,
        right = originX.f + width.f,
        bottom = originY.f + height.f
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
