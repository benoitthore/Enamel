package com.benoitthore.enamel.geometry.interfaces.bounds

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.figures.rect.ERectMutable
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.size.ESizeMutable
import com.benoitthore.enamel.geometry.interfaces.HasCenter

interface EShape // add to geometry as mentioned on Trello

interface HasBounds : HasCenter, EShape {
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
}
