package com.benoitthore.enamel.geometry.interfaces.bounds

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.figures.rect.ERectMutable
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.size.ESizeMutable
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable

interface EShape<I, M> where I : EShape<I, M>, M : EShapeMutable<I, M> {
    fun toMutable(): M
    fun toImmutable(): I
}

interface EShapeMutable<I, M> : EShape<I, M> where I : EShape<I, M>, M : EShapeMutable<I, M>

fun <T, I, M> EShape<I, M>.ensureMutable(): T
        where  T : EShapeMutable<I, M>, I : EShape<I, M>, M : EShapeMutable<I, M> =
    if (this is EShapeMutable) this as T else toMutable() as T

/**
left,top,right,bottom must be implemented in a class, not an interface
 */
interface HasBounds<I, M> : EShapeMutable<I, M> where I : HasBounds<I, M>, M : CanSetBounds<I, M> {
    val left: Float
    val top: Float
    val right: Float
    val bottom: Float

    val originX: Float get() = left
    val originY: Float get() = top
    val width: Float get() = right - left
    val height: Float get() = bottom - top

    val centerX: Float get() = originX + width / 2
    val centerY: Float get() = originY + height / 2
    val center: EPoint get() = E.Point(centerX, centerY)

    fun getBounds(target: ERectMutable = E.RectMutable()): ERect =
        target.set(originX, originY, width, height)

    fun getSize(target: ESizeMutable = E.SizeMutable()): ESize = target.set(width, height)

    fun getCenter(target: EPointMutable = E.PointMutable()): EPointMutable =
        target.set(centerX, centerY)


}
