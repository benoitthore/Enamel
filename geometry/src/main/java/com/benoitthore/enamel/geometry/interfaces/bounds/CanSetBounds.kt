package com.benoitthore.enamel.geometry.interfaces.bounds

import com.benoitthore.enamel.geometry.Resetable
import com.benoitthore.enamel.geometry.primitives.point.EPoint

/** setBounds must be implemented in a class, not an interface
 */
interface CanSetBounds<M : EShape<M, I>, I : EShapeMutable<M, I>> : HasBounds<M, I>, Resetable {

    fun setBounds(
        left: Number = this.left,
        top: Number = this.top,
        right: Number = this.right,
        bottom: Number = this.bottom
    )

    override var centerX: Float
        get() = super.centerX
        set(value) {
            TODO("Untested code")
            val offset = centerX + value
            setBounds(
                left = left - offset,
                right = right - offset
            )
        }
    override var centerY: Float
        get() = super.centerY
        set(value) {
            TODO("Untested code")
            val offset = centerY + value
            setBounds(
                top = top - offset,
                bottom = bottom - offset
            )
        }


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

    fun setCenter(x: Number, y: Number) {
        val xOffset = centerX + x.toFloat()
        val yOffset = centerY + y.toFloat()
        setBounds(
            left = left - xOffset,
            right = right - xOffset,
            top = top - yOffset,
            bottom = bottom - yOffset
        )
        TODO("Untested code")
    }

    override fun reset() {
        setBounds(0, 0, 0, 0)
    }

    fun setCenter(center: EPoint) = setCenter(center.x, center.y)


}
