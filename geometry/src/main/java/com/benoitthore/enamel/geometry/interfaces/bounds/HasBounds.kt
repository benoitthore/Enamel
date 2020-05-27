package com.benoitthore.enamel.geometry.interfaces.bounds

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.figures.rect.ERectMutable
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.size.ESizeMutable
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable

/*
<M : EShape<M, I>, I : EShapeMutable<M, I>>
 */
interface EShape<M : EShape<M, I>, I : EShapeMutable<M, I>> {
    fun toMutable(): M
    fun toImmutable(): I
}

interface EShapeMutable<M : EShape<M, I>, I : EShapeMutable<M, I>> : EShape<M, I>

interface HasBounds<M : EShape<M, I>, I : EShapeMutable<M, I>> : EShape<M,I> {
    val left: Float
    val top: Float
    val right: Float
    val bottom: Float

    val originX: Float get() = left
    val originY: Float get() = top
    val width: Float get() = right - left
    val height: Float get() = bottom - top

    fun getBounds(target: ERectMutable = E.RectMutable()): ERect =
        target.set(originX, originY, width, height)

    fun getSize(target: ESizeMutable = E.SizeMutable()): ESize = target.set(width, height)

    fun getCenter(target: EPointMutable = E.PointMutable()): EPointMutable =
        target.set(centerX, centerY)

    val centerX: Float
    val centerY: Float
}
