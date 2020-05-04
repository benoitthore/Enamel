package com.benoitthore.enamel.geometry.figures.line

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.Resetable
import com.benoitthore.enamel.geometry.allocateDebugMessage
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.interfaces.bounds.CanSetBounds
import com.benoitthore.enamel.geometry.interfaces.CanSetCenter
import com.benoitthore.enamel.geometry.primitives.EPoint
import com.benoitthore.enamel.geometry.primitives.EPointMutable

interface ELineMutable : ELine,
    CanSetBounds,
    CanSetCenter,
    Resetable {

    class Impl internal constructor(
        x1: Number = 0,
        y1: Number = 0,
        x2: Number = 0,
        y2: Number = 0
    ) : ELineMutable {
        init {
            allocateDebugMessage()
        }

        override val start: EPointMutable = E.PointMutable(x1, y1)
        override val end: EPointMutable = E.PointMutable(x2, y2)
    }

    override val start: EPointMutable
    override val end: EPointMutable

    override fun setCenter(x: Number, y: Number) {
        val xOffset = x.toFloat() - centerX
        val yOffset = y.toFloat() - centerY
        start.selfOffset(xOffset, yOffset)
        end.selfOffset(xOffset, yOffset)
    }

    // TODO FIX
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