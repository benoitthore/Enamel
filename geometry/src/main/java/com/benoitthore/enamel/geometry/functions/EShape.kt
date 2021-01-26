package com.benoitthore.enamel.geometry.functions

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.builders.*
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.figures.rect.ERectMutable
import com.benoitthore.enamel.geometry.primitives.Tuple2
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable
import com.benoitthore.enamel.geometry.primitives.size.ESizeMutable
import com.benoitthore.enamel.geometry.svg.ESVG

fun <T, I, M> EShape<I, M>.ensureMutable(): T
        where  T : EShapeMutable<I, M>, I : EShape<I, M>, M : EShapeMutable<I, M> =
    if (this is EShapeMutable) this as T else toMutable() as T

interface EShape<I, M> : ESVG where M : EShapeMutable<I, M>, I : EShape<I, M> {

    fun _copy(): EShape<I, M>
    fun toMutable(): M
    fun toImmutable(): I

    val left: Float
    val top: Float
    val right: Float
    val bottom: Float

    val originX: Float
    val originY: Float

    val width: Float
    val height: Float
    val centerX: Float
    val centerY: Float

    /**
     * @return the bounds of the shape
     */
    fun getBounds(target: ERectMutable = MutableRect()): ERectMutable =
        target.setOriginSize(originX, originY, width, height)

    /**
     * @return the size of the shape
     */
    fun getSize(target: ESizeMutable = MutableSize()): ESizeMutable = target.set(width, height)

    /**
     * @return the center of the shape
     */
    fun getCenter(target: EPointMutable = MutablePoint()): EPointMutable =
        target.set(centerX, centerY)


}

interface EShapeMutable<I, M> : EShape<I, M> where  M : EShapeMutable<I, M>, I : EShape<I, M> {

    override var left: Float
    override var top: Float
    override var right: Float
    override var bottom: Float

    override var originX: Float
    override var originY: Float

    override var width: Float
    override var height: Float
    override var centerX: Float
    override var centerY: Float

    fun set(other : I) : M
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