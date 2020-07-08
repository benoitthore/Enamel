package com.benoitthore.enamel.geometry.interfaces.bounds

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.svg.ESVG

fun main() {
    val rect: ERect = E.Rect()

    rect.copy()
}

interface Copyable<T> where T : Copyable<T> {
    fun copy(): T
}

interface EShape<T> : ESVG, Copyable<T> where T : EShape<T> {

    var left: Float
    var top: Float
    var right: Float
    var bottom: Float

    var originX: Float
    var originY: Float

    var width: Float
    var height: Float
    var centerX: Float
    var centerY: Float

    // Removed because it can cause undesired allocations
//    val center: EPoint get() = E.Point(centerX, centerY)

    fun getBounds(target: ERect = E.Rect()): ERect =
        target.setOriginSize(originX, originY, width, height)

    fun getSize(target: ESize = E.Size()): ESize = target.set(width, height)

    fun getCenter(target: EPoint = E.Point()): EPoint =
        target.set(centerX, centerY)


    fun setBounds(
        left: Number = this.left,
        top: Number = this.top,
        right: Number = this.right,
        bottom: Number = this.bottom
    )

    fun setCenter(x: Number, y: Number) {
        val xOff = x.f - centerX
        val yOff = y.f - centerY
        setBounds(
            left = left + xOff,
            top = top + yOff,
            right = right + xOff,
            bottom = bottom + yOff
        )
    }

    fun setCenter(center: EPoint) = setCenter(center.x, center.y)

    fun setOrigin(x: Number, y: Number) {
        val xOff = x.f - originX
        val yOff = y.f - originY
        setBounds(
            left = left + xOff,
            top = top + yOff,
            right = right + xOff,
            bottom = bottom + yOff
        )
    }

    fun setOrigin(origin: EPoint) = setOrigin(origin.x, origin.y)

}
