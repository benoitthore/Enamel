package com.benoitthore.enamel.geometry.figures.line

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.Resetable
import com.benoitthore.enamel.geometry.interfaces.bounds.CanSetBounds
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable

interface ELineMutable : ELine, CanSetBounds, Resetable {

    override val start: EPointMutable
    override val end: EPointMutable

    override fun setCenter(x: Number, y: Number) {
        val xOffset = x.toFloat() - centerX
        val yOffset = y.toFloat() - centerY
        start.selfOffset(xOffset, yOffset)
        end.selfOffset(xOffset, yOffset)
    }

    override fun setBounds(left: Number, top: Number, right: Number, bottom: Number) {
        if (isTLBR) {
            start.set(left, top)
            end.set(right, bottom)
        } else {
            start.set(right, top)
            end.set(left, bottom)
        }
    }

    fun set(start: EPoint = this.start, end: EPoint = this.end) =
        set(start.x, start.y, end.x, end.y)

    fun set(
        x1: Number = start.x,
        y1: Number = start.y,
        x2: Number = end.x,
        y2: Number = end.y
    ) = apply {
        start.x = x1.f
        start.y = y1.f

        end.x = x2.f
        end.y = y2.f
    }

    fun selfOffset(xOff: Number, yOff: Number) = apply {
        start.selfOffset(xOff, yOff)
        end.selfOffset(xOff, yOff)
    }

    fun selfOffset(p: EPoint) = selfOffset(p.x, p.y)
    fun selfScale(width: Number, height: Number): ELineMutable {
        start.x *= width.f
        end.x *= width.f

        start.y *= height.f
        end.y *= height.f
        return this
    }

    override fun reset() {
        start.reset()
        end.reset()
    }

}