package com.benoitthore.enamel.geometry.functions

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.svg.ESVG


fun <T : EShape> T.copy() = _copy() as? T ?: throw Exception("${this::class.java} _copy() returns ${_copy()::class.java}")

interface EShape : ESVG {

    fun _copy() : EShape

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

    /**
     * @return the bounds of the shape
     */
    fun getBounds(target: ERect = E.Rect()): ERect =
        target.setOriginSize(originX, originY, width, height)

    /**
     * @return the size of the shape
     */
    fun getSize(target: ESize = E.Size()): ESize = target.set(width, height)

    /**
     * @return the center of the shape
     */
    fun getCenter(target: EPoint = E.Point()): EPoint =
        target.set(centerX, centerY)


    /**
     * Use setBounds extensions instead
     */
    fun _setBounds(
        left: Number = this.left,
        top: Number = this.top,
        right: Number = this.right,
        bottom: Number = this.bottom
    )

    /**
     * Use setCenter extensions instead
     */
    fun _setCenter(x: Number, y: Number) {
        val xOff = x.f - centerX
        val yOff = y.f - centerY
        _setBounds(
            left = left + xOff,
            top = top + yOff,
            right = right + xOff,
            bottom = bottom + yOff
        )
    }


    /**
     * Use setCenter extensions instead
     */
    fun _setCenter(center: EPoint) = _setCenter(center.x, center.y)


    /**
     * Use setOrigin extensions instead
     */
    fun _setOrigin(x: Number, y: Number) {
        val xOff = x.f - originX
        val yOff = y.f - originY
        _setBounds(
            left = left + xOff,
            top = top + yOff,
            right = right + xOff,
            bottom = bottom + yOff
        )
    }

    /**
     * Use setOrigin extensions instead
     */
    fun _setOrigin(origin: EPoint) = _setOrigin(origin.x, origin.y)
}
