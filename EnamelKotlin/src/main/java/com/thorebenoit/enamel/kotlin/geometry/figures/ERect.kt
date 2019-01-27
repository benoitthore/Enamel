package com.thorebenoit.enamel.kotlin.geometry.figures

import com.thorebenoit.enamel.kotlin.f
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointImmutable

/*
This class should be the example to follow in order to implement mutability

Note:

Immutable doesn't have any function requiring a buffer, but:
- Only toImmutable() allocates without providing a buffer
- Only toMutable() requires a buffer

Create the API without default arguments for buffer in order to make sure no allocation is done by the library itself, then the user can decide


 */
open class ImmutableERect(
    open val origin: EPointImmutable = EPointImmutable(),
    open val size: ESizeImmutable = ESizeImmutable()
) {
    fun toMutable(buffer: ERect) = buffer.set(origin.toMutable(), size.toMutable())
    fun toImmutable() = ImmutableERect(origin.toImmutable(), size.toImmutable())

    val height get() = size.height
    val width get() = size.width

    open val top: Float
        get() = origin.y
    open val bottom: Float
        get() = top + height
    open val left: Float
        get() = origin.x
    open val right: Float
        get() = origin.x + width

    open val x: Float
        get() = origin.x
    open val y: Float
        get() = origin.y

    open fun topLeft(): EPointImmutable = origin
}

class ERect(override var origin: EPoint = EPoint(), override var size: ESize = ESize()) :
    ImmutableERect(origin, size) {

    fun copy() = ERect(origin.copy(), size.copy())

    fun set(
        origin: EPointImmutable = this.origin,
        size: ESizeImmutable = this.size
    ) =
        set(origin.x, origin.y, size.width, size.height)

    fun set(
        origin: EPointImmutable = this.origin,
        width: Number = this.width,
        height: Number = this.height
    ) =
        set(origin.x, origin.y, width, height)

    fun set(
        x: Number = this.x,
        y: Number = this.y,
        size: ESizeImmutable = this.size
    ) =
        set(x, y, size.width, size.height)

    fun set(
        x: Number = this.x,
        y: Number = this.y,
        width: Number = this.width,
        height: Number = this.height
    ): ERect {
        origin.x = x.f
        origin.y = y.f
        size.width = width.f
        size.height = height.f
        return this
    }

    override fun topLeft(): EPoint = origin


    override var x: Float
        get() = super.x
        set(value) {
            origin.x = value
        }
    override var y: Float
        get() = super.y
        set(value) {
            origin.y = value
        }

    override var top: Float
        get() = super.top
        set(value) {
            size.height -= value - top
            origin.y = value
        }
    override var left: Float
        get() = super.left
        set(value) {
            size.width -= value - left
            origin.x = value
        }

    override var right: Float
        get() = super.right
        set(value) {
            size.width += value - right
        }

    override var bottom: Float
        get() = super.bottom
        set(value) {
            size.height +=  value - bottom
        }


    // Changing
    fun offset(x: Number = 0, y: Number = 0): ERect = set(origin = origin.offset(x, y))

    fun inset(margin: Number) = inset(margin, margin)
    fun inset(p: EPointImmutable) = inset(p.x, p.y)
    fun inset(x: Number = 0, y: Number = 0): ERect {
        val x = x.f
        val y = y.f
        left += x
        top += y
        bottom -= y
        right -= x
        return this
    }

    fun expand(margin: Number) = expand(margin, margin)
    fun expand(p: EPointImmutable) = expand(p.x, p.y)
    fun expand(x: Number = 0f, y: Number = 0f) = inset(-x.f, -y.f)


    // Points
    fun pointAtAnchor(x: Number, y: Number, buffer: EPoint): EPoint =
        buffer.set(x = origin.x + size.width * x.f, y = origin.y + size.height * y.f)

    fun pointAtAnchor(anchor: EPoint, buffer: EPoint) = pointAtAnchor(anchor.x, anchor.y, buffer)

    fun anchorAtPoint(x: Number, y: Number, buffer: EPoint): EPoint {
        val x = if (width == 0f) .5f else x.f / width
        val y = if (height == 0f) .5f else y.f / height
        return buffer.set(x, y)
    }

    fun center(buffer: EPoint): EPoint {
        buffer.set(
            origin.x + (size.width / 2),
            origin.y + (size.height / 2)
        )
        return buffer
    }


    // Shapes
    fun innerCircle(buffer: ECircle): ECircle {
        center(buffer.center)
        buffer.radius = size.min / 2f
        return buffer
    }


    fun outterCircle(buffer: ECircle): ECircle {
        center(buffer.center)
        buffer.radius = size.max
        return buffer
    }
}




