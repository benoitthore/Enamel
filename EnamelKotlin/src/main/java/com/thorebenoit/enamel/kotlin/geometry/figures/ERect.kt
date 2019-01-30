package com.thorebenoit.enamel.kotlin.geometry.figures

import com.thorebenoit.enamel.kotlin.core.d
import com.thorebenoit.enamel.kotlin.core.f
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointImmutable
import com.thorebenoit.enamel.kotlin.geometry.alignement.*

/*
This class should be the example to follow in order to implement mutability

Note:

Immutable doesn't have any function requiring a buffer, but:
- Only toImmutable() allocates without providing a buffer
- Only toMutable() requires a buffer

Create the API without default arguments for buffer in order to make sure no allocation is done by the library itself, then the user can decide


 */
open class ERectImmutable(
    open val origin: EPointImmutable = EPointImmutable(),
    open val size: ESizeImmutable = ESizeImmutable()
) {
    fun toMutable(buffer: ERect) = buffer.set(origin.toMutable(), size.toMutable())
    fun toImmutable() = ERectImmutable(origin.toImmutable(), size.toImmutable())

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


    fun contains(p: EPointImmutable) = contains(p.x, p.y)

    fun contains(x: Number, y: Number): Boolean {
        val x = x.f
        val y = y.f
        val left = left
        val top = top
        val bottom = bottom
        val right = right
        return left < right && top < bottom && x >= left && x < right && y >= top && y < bottom
    }

    //contains Rect
    fun contains(other: ERectImmutable) = contains(
        top = other.top,
        left = other.left,
        right = other.right,
        bottom = other.bottom)

    fun contains(
        left: Number, top: Number, right: Number, bottom: Number
    ): Boolean {
        val left = left.f
        val top = top.f
        val right = right.f
        val bottom = bottom.f
        val thisleft = this.left
        val thistop = this.top
        val thisbottom = this.bottom
        val thisright = this.right

        return (thisleft < thisright && thistop < thisbottom
                && thisleft <= left && thistop <= top
                && thisright >= right && thisbottom >= bottom)
    }

    //intersects
    fun intersects(other: ERectImmutable) = intersects(
        top = other.top,
        left = other.left,
        right = other.right,
        bottom = other.bottom)

    fun intersects(left: Number, top: Number, right: Number,
                   bottom: Number): Boolean {
        val left = left.f
        val top = top.f
        val right = right.f
        val bottom = bottom.f
        val thisleft = this.left
        val thistop = this.top
        val thisbottom = this.bottom
        val thisright = this.right
        return (thisleft < right && left < thisright
                && thistop < bottom && top < thisbottom)
    }

}

class ERect(override var origin: EPoint = EPoint(), override var size: ESize = ESize()) :
    ERectImmutable(origin, size) {

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

    fun topLeft(): EPoint = origin


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
            size.height += value - bottom
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

    fun pointAtAnchor(anchor: EPointImmutable, buffer: EPoint) = pointAtAnchor(anchor.x, anchor.y, buffer)

    fun anchorAtPoint(x: Number, y: Number, buffer: EPoint): EPoint {
        val x = if (width == 0f) .5f else x.f / width
        val y = if (height == 0f) .5f else y.f / height
        return buffer.set(x, y)
    }

    fun center(buffer: EPoint): EPoint = pointAtAnchor(0.5f, 0.5f, buffer)

    // Shapes
    fun innerCircle(buffer: ECircle): ECircle {
        center(buffer.center) // set circles center to rect center
        buffer.radius = size.min / 2
        return buffer
    }


    fun outterCircle(buffer: ECircle): ECircle {
        center(buffer.center) // set circles center to rect center
        buffer.radius = size.diagonal / 2
        return buffer
    }

    fun set(top: Float, bottom: Float, left: Float, right: Float): ERect {
        this.top = top
        this.bottom = bottom
        this.left = left
        this.right = right
        return this
    }

    fun rectAlignedInside(
        aligned: EAlignment,
        size: ESizeImmutable,
        spacing: Number = 0,
        buffer: ERect
    ): ERect {
        val spacing = spacing.f

        val anchor = aligned.namedPoint
        val spacingSign = aligned.spacingSign

        val position = pointAtAnchor(aligned.namedPoint, buffer.origin)
            .offset(spacingSign.x * spacing, spacingSign.y * spacing)

        buffer.size.set(size)

        return ERectAnchorPos(anchor = anchor, position = position, size = buffer.size, buffer = buffer)
    }


    fun rectAlignedOutside(
        aligned: EAlignment,
        size: ESizeImmutable,
        spacing: Number = 0,
        buffer: ERect
    ): ERect {
        val spacing = spacing.f

        val anchor = aligned.flipped.namedPoint
        val spacingSign = aligned.flipped.spacingSign

        val position = pointAtAnchor(aligned.namedPoint, buffer.origin)
            .offset(spacingSign.x * spacing, spacingSign.y * spacing)

        buffer.size.set(size)

        return ERectAnchorPos(anchor = anchor, position = position, size = buffer.size, buffer = buffer)
    }
}


fun ERectCenter(
    x: Number = 0f, y: Number = 0f,
    width: Number, height: Number, buffer: ERect
): ERect {

    val width = width.f
    val height = height.f
    val x = x.f - width / 2
    val y = y.f - height / 2


    return buffer.set(x = x, y = y, width = width, height = height)
}

fun ERectCorners(
    corner1X: Number = 0,
    corner1Y: Number = 0,
    corner2X: Number = 0,
    corner2Y: Number = 0,
    buffer: ERect
): ERect {
    return ERectSides(
        top = Math.min(corner1Y.d, corner2Y.d),
        bottom = Math.max(corner1Y.d, corner2Y.d),
        left = Math.min(corner1X.d, corner2X.d),
        right = Math.max(corner1X.d, corner2X.d),
        buffer = buffer
    )
}

fun ERectSides(left: Number, top: Number, right: Number, bottom: Number, buffer: ERect): ERect {
    buffer.top = top.f
    buffer.left = left.f
    buffer.right = right.f
    buffer.bottom = bottom.f
    return buffer
}

fun ERectAnchorPos(anchor: EPointImmutable, position: EPointImmutable, size: ESize, buffer: ERect) = buffer.set(
    x = position.x - size.width * anchor.x,
    y = position.y - size.height * anchor.y,
    size = size
)


// TODO Not required but how would this work ?
/*
fun ERectSquareOrigin(width: Number, origin: EPointImmutable = EPointImmutable.zero) = ERect(origin, width, width)
fun ERectSquareCenter(width: Number, center: EPointImmutable = EPointImmutable.zero) = ERectCenter(center, width, width)
 */


/*
fun ERectSquareOrigin(width: Number, origin: EPointImmutable = XPoint.zero) = ERect(origin, width, width)
fun ERectSquareCenter(width: Number, center: EPointImmutable = XPoint.zero) = ERectCenter(center, width, width)




*/

