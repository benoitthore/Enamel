package com.benoitthore.enamel.geometry.figures

import com.benoitthore.enamel.geometry.alignement.EAlignment
import com.benoitthore.enamel.geometry.Resetable
import com.benoitthore.enamel.core.math.*
import com.benoitthore.enamel.geometry.allocateDebugMessage
import com.benoitthore.enamel.geometry.primitives.*
import java.lang.Exception

/*
This class should be the example to follow in order to implement mutability

Note:

Immutable doesn't have any function requiring a buffer, but:
- Only toImmutable() allocates without providing a buffer
- Only toMutable() requires a buffer

Create the API without default arguments for buffer in order to make sure no allocation is done by the library itself, then the user can decide


 */
open class ERect(
    open val origin: EPoint = EPoint(),
    open val size: ESize = ESize()
) {
    companion object {
        val zero = ERect()
    }

    init {
        allocateDebugMessage()
    }

    fun toMutable(buffer: ERectMutable = ERectMutable()) =
        buffer.set(origin.toMutable(buffer.origin), size.toMutable())

    fun toImmutable() = ERect(origin.toImmutable(), size.toImmutable())
    fun copy(buffer: ERectMutable = ERectMutable()) =
        ERectMutable(origin.copy(buffer = buffer.origin), size.copy(buffer = buffer.size))

    open val height get() = size.height
    open val width get() = size.width

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


    // contains Point
    fun contains(x: Number, y: Number) = contains(x, y, 0, 0)

    fun contains(p: EPoint) = contains(p.x, p.y)


    // contains Circle: When dealing with circles, use x and y as center
    fun contains(p: EPoint, radius: Number): Boolean = contains(p.x, p.y, radius)

    fun contains(c: ECircle): Boolean = contains(c.center, c.radius)

    fun contains(x: Number, y: Number, radius: Number): Boolean =
        radius.f.let { radius ->
            contains(
                x.f - radius,
                y.f - radius,
                radius * 2,
                radius * 2
            )
        }

    // contains Rect
    fun contains(other: ERect) = contains(other.origin, other.size)

    fun contains(origin: EPoint, size: ESize) =
        contains(origin.x, origin.y, size.width, size.height)

    fun contains(x: Number, y: Number, width: Number, height: Number): Boolean {
        val x = x.f
        val y = y.f
        val width = width.f
        val height = height.f
        return x >= left && x + width < right && y >= top && y + height < bottom
    }

    fun containsFull(p: EPoint, radius: Number): Boolean = containsFull(p.x, p.y, radius)

    fun containsFull(c: ECircle): Boolean = containsFull(c.center, c.radius)

    // TODO The functions considers the circle to be a square which doesn't work on the edges
    fun containsFull(x: Number, y: Number, radius: Number): Boolean =
        radius.f.let { radius ->
            containsFull(
                x.f - radius,
                y.f - radius,
                radius * 2,
                radius * 2
            )
        }

    fun containsFull(other: ERect) = contains(other.origin, other.size)

    fun containsFull(origin: EPoint, size: ESize) =
        containsFull(origin.x, origin.y, size.width, size.height)

    fun containsFull(x: Number, y: Number, width: Number, height: Number): Boolean {
        val x = x.f
        val y = y.f
        val width = width.f
        val height = height.f
        return x + width >= left && x + width < right
                && y + height >= top && y + height < bottom
    }


    //intersects
    fun intersects(other: ERect) = intersects(
        top = other.top,
        left = other.left,
        right = other.right,
        bottom = other.bottom
    )

    fun intersects(
        left: Number, top: Number, right: Number,
        bottom: Number
    ): Boolean {
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


    // Points
    fun pointAtAnchor(
        x: Number,
        y: Number,
        buffer: EPointMutable = EPointMutable()
    ): EPointMutable =
        buffer.set(x = pointAtAnchorX(x), y = pointAtAnchorY(y))

    fun pointAtAnchor(anchor: EPoint, buffer: EPointMutable = EPointMutable()) =
        pointAtAnchor(anchor.x, anchor.y, buffer)

    fun pointAtAnchorX(x: Number) = origin.x + size.width * x.f
    fun pointAtAnchorY(y: Number) = origin.y + size.height * y.f

    fun anchorAtPoint(
        x: Number,
        y: Number,
        buffer: EPointMutable = EPointMutable()
    ): EPointMutable {
        val x = if (width == 0f) .5f else x.f / width
        val y = if (height == 0f) .5f else y.f / height
        return buffer.set(x, y)
    }

    fun center(buffer: EPointMutable = EPointMutable()): EPointMutable =
        pointAtAnchor(0.5f, 0.5f, buffer)

    fun topLeft(buffer: EPointMutable = EPointMutable()): EPointMutable =
        pointAtAnchor(0f, 0f, buffer)

    fun topRight(buffer: EPointMutable = EPointMutable()): EPointMutable =
        pointAtAnchor(1f, 0.0f, buffer)

    fun bottomRight(buffer: EPointMutable = EPointMutable()): EPointMutable =
        pointAtAnchor(1f, 1f, buffer)

    fun bottomLeft(buffer: EPointMutable = EPointMutable()): EPointMutable =
        pointAtAnchor(0f, 1f, buffer)


    // Alignement
    fun rectAlignedInside(
        aligned: EAlignment,
        size: ESize,
        spacing: Number = 0,
        buffer: ERectMutable = ERectMutable(this)
    ): ERectMutable {
        buffer.set(this)
        val spacing = spacing.f

        val anchor = aligned.namedPoint
        val spacingSign = aligned.spacingSign

        val position = pointAtAnchor(aligned.namedPoint, buffer = buffer.origin)
            .offset(spacingSign.x * spacing, spacingSign.y * spacing, buffer = buffer.origin)

        buffer.size.set(size)

        return ERectAnchorPos(
            anchor = anchor,
            position = position,
            size = buffer.size,
            buffer = buffer
        )
    }


    fun rectAlignedOutside(
        aligned: EAlignment,
        size: ESize,
        spacing: Number = 0,
        buffer: ERectMutable = ERectMutable()
    ): ERectMutable {
        buffer.set(this)
        val spacing = spacing.f

        val anchor = aligned.flipped.namedPoint
        val spacingSign = aligned.flipped.spacingSign

        val position = pointAtAnchor(aligned.namedPoint, buffer = buffer.origin)
            .offset(spacingSign.x * spacing, spacingSign.y * spacing, buffer = buffer.origin)

        buffer.size.set(size)

        return ERectAnchorPos(
            anchor = anchor,
            position = position,
            size = buffer.size,
            buffer = buffer
        )
    }


    // Changing
    fun offset(p: Tuple2, buffer: ERectMutable = ERectMutable()): ERectMutable =
        offset(p.v1, p.v2, buffer)

    fun offset(
        x: Number = 0,
        y: Number = 0,
        buffer: ERectMutable = ERectMutable()
    ): ERectMutable {
        buffer.set(this)
        buffer.origin.selfOffset(x, y)
        return buffer
    }

    fun inset(margin: Number, buffer: ERectMutable = ERectMutable()) =
        inset(margin, margin, buffer)

    fun inset(p: Tuple2, buffer: ERectMutable = ERectMutable()) = inset(p.v1, p.v2, buffer)
    fun inset(
        x: Number = 0,
        y: Number = 0,
        buffer: ERectMutable = ERectMutable()
    ): ERectMutable {
        buffer.set(this)
        val x = x.f
        val y = y.f
        buffer.left += x
        buffer.top += y
        buffer.bottom -= y
        buffer.right -= x
        return buffer
    }

    fun expand(margin: Number, buffer: ERectMutable = ERectMutable()) =
        expand(margin, margin, buffer)

    fun expand(p: Tuple2, buffer: ERectMutable = ERectMutable()) = expand(p.v1, p.v2, buffer)
    fun expand(x: Number = 0f, y: Number = 0f, buffer: ERectMutable = ERectMutable()) =
        inset(-x.f, -y.f, buffer)

    fun expand(padding: EOffset, buffer: ERectMutable = ERectMutable()): ERectMutable {
        buffer.set(this)
        buffer.left -= padding.left
        buffer.top -= padding.top
        buffer.bottom += padding.bottom
        buffer.right += padding.right
        return buffer
    }

    fun padding(
        top: Number = this.top,
        bottom: Number = this.bottom,
        left: Number = this.left,
        right: Number = this.right
        , buffer: ERectMutable = ERectMutable()
    ): ERectMutable {
        buffer.set(this)
        buffer.left += left.f
        buffer.top += top.f
        buffer.bottom -= bottom.f
        buffer.right -= right.f
        return buffer
    }

    fun padding(padding: EOffset, buffer: ERectMutable = ERectMutable()): ERectMutable =
        padding(
            left = padding.left,
            top = padding.top,
            bottom = padding.bottom,
            right = padding.right,
            buffer = buffer
        )

    // TODO Make self version
    fun scale(t: Tuple2, buffer: ERectMutable = ERectMutable()): ERectMutable =
        scale(t.v1, t.v2, buffer)

    fun scale(x: Number, y: Number, buffer: ERectMutable = ERectMutable()): ERectMutable {
        buffer.x = this.x * x.f
        buffer.y = this.y * y.f
        buffer.width = this.width * x.f
        buffer.height = this.height * y.f

        return buffer
    }

    fun scaleAnchor(factor: Number, anchor: EPoint, buffer: ERectMutable = ERectMutable()) =
        scaleAnchor(factor, anchor.x, anchor.y, buffer)


    fun scaleAnchor(
        factor: Number,
        anchorX: Number,
        anchorY: Number,
        buffer: ERectMutable = ERectMutable()
    ) = scaleRelative(
        factor,
        pointAtAnchorX(anchorX),
        pointAtAnchorY(anchorY),
        buffer
    )


    fun scaleRelative(
        factor: Number,
        point: EPoint,
        buffer: ERectMutable = ERectMutable()
    ) = scaleRelative(factor, point.x, point.y, buffer)

    fun scaleRelative(
        factor: Number,
        pointX: Number,
        pointY: Number,
        buffer: ERectMutable = ERectMutable()
    ): ERectMutable {
        buffer.set(this)

        val factor = factor.f
        val newX = origin.x + (pointX.f - origin.x) * (1f - factor)
        val newY = origin.y + (pointY.f - origin.y) * (1f - factor)

        buffer.origin.set(newX, newY)
        buffer.size.width *= factor
        buffer.size.height *= factor
        return buffer
    }

    fun toPointList(
        buffer: List<EPointMutable> = listOf(
            EPointMutable(),
            EPointMutable(),
            EPointMutable(),
            EPointMutable()
        )
    ): List<EPointMutable> {
        if (buffer.size != 4) {
            throw Exception("Needs 4 points in buffer")
        }
        buffer[0].set(top, left)
        buffer[1].set(top, right)
        buffer[2].set(bottom, right)
        buffer[3].set(bottom, left)
        return buffer
    }


    override fun equals(other: Any?): Boolean {
        return (other as? ERect)?.let { other.origin == origin && other.size == size } ?: false
    }


    override fun toString(): String {
        return "ERectMutable(origin=$origin, size=$size)"
    }

}

class ERectMutable(
    override var origin: EPointMutable = EPointMutable(),
    override var size: ESizeMutable = ESizeMutable()
) :
    ERect(origin, size), Resetable {

    // TODO Copy in ERect
    constructor(other: ERect) : this(other.origin.copy(), other.size.copy())

    constructor(x: Number = 0f, y: Number = 0f, width: Number, height: Number) : this(
        x point y,
        width size height
    )

    override fun reset() {
        origin.reset(); size.reset()
    }

    fun set(other: ERect) = set(other.origin, other.size)
    fun set(
        origin: EPoint = this.origin,
        size: ESize = this.size
    ) =
        set(origin.x, origin.y, size.width, size.height)

    fun set(
        origin: EPoint = this.origin,
        width: Number = this.width,
        height: Number = this.height
    ) =
        set(origin.x, origin.y, width, height)

    fun set(
        x: Number = this.x,
        y: Number = this.y,
        size: ESize = this.size
    ) =
        set(x, y, size.width, size.height)

    fun set(
        x: Number = this.x,
        y: Number = this.y,
        width: Number = this.width,
        height: Number = this.height
    ): ERectMutable {
        origin.x = x.f
        origin.y = y.f
        size.width = width.f
        size.height = height.f
        return this
    }

    fun setSides(
        top: Number = this.top,
        bottom: Number = this.bottom,
        left: Number = this.left,
        right: Number = this.right
    ): ERectMutable {
        this.top = top.f
        this.bottom = bottom.f
        this.left = left.f
        this.right = right.f
        return this
    }

    override var width: Float
        get() = super.width
        set(value) {
            size.width = value
        }

    override var height: Float
        get() = super.height
        set(value) {
            size.height = value
        }

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

    var center: EPoint
        @Deprecated("User center() instead", level = DeprecationLevel.WARNING)
        get() = center()
        set(value) {
            origin.x = value.x - width / 2
            origin.y = value.y - height / 2
        }


    //////
    fun selfOffset(x: Number = 0, y: Number = 0): ERectMutable = offset(x, y, this)

    fun selfOffset(p: Tuple2): ERectMutable = offset(p.v1, p.v2, this)

    fun selfInset(margin: Number) = inset(margin, margin, this)
    fun selfInset(p: EPoint) = inset(p.x, p.y, this)
    fun selfInset(x: Number = 0, y: Number = 0) = inset(x, y, this)

    fun selfExpand(margin: Number) = expand(margin, margin, this)
    fun selfExpand(p: EPoint) = expand(p.x, p.y, this)
    fun selfExpand(x: Number = 0f, y: Number = 0f) = inset(-x.f, -y.f, this)

    fun selfPadding(padding: EOffset) = padding(padding, this)
    fun selfPadding(
        top: Number = this.top,
        bottom: Number = this.bottom,
        left: Number = this.left,
        right: Number = this.right
    ) = padding(
        top = top,
        bottom = bottom,
        left = left,
        right = right,
        buffer = this
    )

    fun selfExpand(padding: EOffset) = expand(padding, this)

    fun selfScaleAnchor(factor: Number, anchor: EPoint) = scaleAnchor(factor, anchor, this)
    fun selfScaleAnchor(
        factor: Number,
        anchorX: Number,
        anchorY: Number
    ) = scaleAnchor(factor, anchorX, anchorY, this)

    fun selfScaleRelative(factor: Number, point: EPoint) = scaleRelative(factor, point, this)
    fun selfScaleRelative(factor: Number, pointX: Number, pointY: Number) =
        scaleRelative(factor = factor, pointX = pointX, pointY = pointY, buffer = this)

}
