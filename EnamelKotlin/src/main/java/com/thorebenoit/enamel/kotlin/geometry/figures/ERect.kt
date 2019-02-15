package com.thorebenoit.enamel.kotlin.geometry.figures

import com.thorebenoit.enamel.kotlin.core.Resetable
import com.thorebenoit.enamel.kotlin.core.math.d
import com.thorebenoit.enamel.kotlin.core.math.f
import com.thorebenoit.enamel.kotlin.geometry.GeometryBufferProvider
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType
import com.thorebenoit.enamel.kotlin.geometry.alignement.*
import com.thorebenoit.enamel.kotlin.geometry.allocateDebugMessage
import com.thorebenoit.enamel.kotlin.geometry.primitives.EOffset
import com.thorebenoit.enamel.kotlin.geometry.primitives.point
import java.lang.Exception

/*
This class should be the example to follow in order to implement mutability

Note:

Immutable doesn't have any function requiring a buffer, but:
- Only toImmutable() allocates without providing a buffer
- Only toMutable() requires a buffer

Create the API without default arguments for buffer in order to make sure no allocation is done by the library itself, then the user can decide


 */
open class ERectType(
    open val origin: EPointType = EPointType(),
    open val size: ESizeType = ESizeType()
) {
    companion object {
        val zero = ERectType()
    }

    init {
        allocateDebugMessage()
    }

    fun toMutable(buffer: ERect) = buffer.set(origin.toMutable(buffer.origin), size.toMutable())
    fun toImmutable() = ERectType(origin.toImmutable(), size.toImmutable())
    fun copy(buffer: ERect = ERect()) = ERect(origin.copy(buffer.origin), size.copy(buffer.size))

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

    fun contains(p: EPointType) = contains(p.x, p.y)


    // contains Circle: When dealing with circles, use x and y as center
    fun contains(p: EPointType, radius: Number): Boolean = contains(p.x, p.y, radius)

    fun contains(c: ECircleType): Boolean = contains(c.center, c.radius)

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
    fun contains(other: ERectType) = contains(other.origin, other.size)

    fun contains(origin: EPointType, size: ESizeType) = contains(origin.x, origin.y, size.width, size.height)

    fun contains(x: Number, y: Number, width: Number, height: Number): Boolean {
        val x = x.f
        val y = y.f
        val width = width.f
        val height = height.f
        return x >= left && x + width < right && y >= top && y + height < bottom
    }

    fun containsFull(p: EPointType, radius: Number): Boolean = containsFull(p.x, p.y, radius)

    fun containsFull(c: ECircleType): Boolean = containsFull(c.center, c.radius)

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

    fun containsFull(other: ERectType) = contains(other.origin, other.size)

    fun containsFull(origin: EPointType, size: ESizeType) = containsFull(origin.x, origin.y, size.width, size.height)

    fun containsFull(x: Number, y: Number, width: Number, height: Number): Boolean {
        val x = x.f
        val y = y.f
        val width = width.f
        val height = height.f
        return x + width >= left && x < right && y + height >= top && y < bottom
    }


    //intersects
    fun intersects(other: ERectType) = intersects(
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
    fun pointAtAnchor(x: Number, y: Number, buffer: EPoint = EPoint()): EPoint =
        buffer.set(x = origin.x + size.width * x.f, y = origin.y + size.height * y.f)

    fun pointAtAnchor(anchor: EPointType, buffer: EPoint = EPoint()) = pointAtAnchor(anchor.x, anchor.y, buffer)

    fun anchorAtPoint(x: Number, y: Number, buffer: EPoint = EPoint()): EPoint {
        val x = if (width == 0f) .5f else x.f / width
        val y = if (height == 0f) .5f else y.f / height
        return buffer.set(x, y)
    }

    fun center(buffer: EPoint = EPoint()): EPoint = pointAtAnchor(0.5f, 0.5f, buffer)


    // Alignement
    fun rectAlignedInside(
        aligned: EAlignment,
        size: ESizeType,
        spacing: Number = 0,
        buffer: ERect = ERect(this)
    ): ERect {
        val spacing = spacing.f

        val anchor = aligned.namedPoint
        val spacingSign = aligned.spacingSign

        val position = pointAtAnchor(aligned.namedPoint, buffer = buffer.origin)
            .offset(spacingSign.x * spacing, spacingSign.y * spacing, buffer = buffer.origin)

        buffer.size.set(size)

        return ERectAnchorPos(anchor = anchor, position = position, size = buffer.size, buffer = buffer)
    }


    fun rectAlignedOutside(
        aligned: EAlignment,
        size: ESizeType,
        spacing: Number = 0,
        buffer: ERect = ERect(this)
    ): ERect {
        val spacing = spacing.f

        val anchor = aligned.flipped.namedPoint
        val spacingSign = aligned.flipped.spacingSign

        val position = pointAtAnchor(aligned.namedPoint, buffer = buffer.origin)
            .offset(spacingSign.x * spacing, spacingSign.y * spacing, buffer = buffer.origin)

        buffer.size.set(size)

        return ERectAnchorPos(anchor = anchor, position = position, size = buffer.size, buffer = buffer)
    }


    // Changing
    fun offset(x: Number = 0, y: Number = 0, buffer: ERect = ERect(this)): ERect {
        buffer.origin.selfOffset(x, y)
        return buffer
    }

    fun inset(margin: Number, buffer: ERect = ERect(this)) = inset(margin, margin, buffer)
    fun inset(p: EPointType, buffer: ERect = ERect(this)) = inset(p.x, p.y, buffer)
    fun inset(x: Number = 0, y: Number = 0, buffer: ERect = ERect(this)): ERect {
        val x = x.f
        val y = y.f
        buffer.left += x
        buffer.top += y
        buffer.bottom -= y
        buffer.right -= x
        return buffer
    }

    fun expand(margin: Number, buffer: ERect = ERect(this)) = expand(margin, margin, buffer)
    fun expand(p: EPointType, buffer: ERect = ERect(this)) = expand(p.x, p.y, buffer)
    fun expand(x: Number = 0f, y: Number = 0f, buffer: ERect = ERect(this)) = inset(-x.f, -y.f, buffer)

    fun padding(padding: EOffset, buffer: ERect = ERect(this)): ERect {
        buffer.left += padding.left
        buffer.top += padding.top
        buffer.bottom -= padding.bottom
        buffer.right -= padding.right
        return buffer
    }

    fun scaleAnchor(factor: Number, anchor: EPointType, buffer: ERect = ERect()) =
        scaleRelative(factor, pointAtAnchor(anchor, GeometryBufferProvider.point()), buffer)

    fun scaleRelative(factor: Number, point: EPointType, buffer: ERect = ERect()): ERect {
        buffer.set(this)

        val factor = factor.f
        val newX = origin.x + (point.x - origin.x) * (1f - factor)
        val newY = origin.y + (point.y - origin.y) * (1f - factor)

        buffer.origin.set(newX, newY)
        buffer.size.width *= factor
        buffer.size.height *= factor
        return buffer
    }

    fun toPointList(buffer: List<EPoint> = listOf(EPoint(), EPoint(), EPoint(), EPoint())): List<EPoint> {
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
        return (other as? ERectType)?.let { other.origin == origin && other.size == size } ?: false
    }


    override fun toString(): String {
        return "ERect(origin=$origin, size=$size)"
    }

}

class ERect(override var origin: EPoint = EPoint(), override var size: ESize = ESize()) :
    ERectType(origin, size), Resetable {

    // TODO Copy in ERectType
    constructor(other: ERectType) : this(other.origin.copy(), other.size.copy())

    constructor(x: Number = 0f, y: Number = 0f, width: Number, height: Number) : this(x point y, width size height)

    override fun reset() {
        origin.reset(); size.reset()
    }

    fun set(other: ERectType) = set(other.origin, other.size)
    fun set(
        origin: EPointType = this.origin,
        size: ESizeType = this.size
    ) =
        set(origin.x, origin.y, size.width, size.height)

    fun set(
        origin: EPointType = this.origin,
        width: Number = this.width,
        height: Number = this.height
    ) =
        set(origin.x, origin.y, width, height)

    fun set(
        x: Number = this.x,
        y: Number = this.y,
        size: ESizeType = this.size
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

    fun setSides(top: Float, bottom: Float, left: Float, right: Float): ERect {
        this.top = top
        this.bottom = bottom
        this.left = left
        this.right = right
        return this
    }

    fun topLeft(): EPoint = origin


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

    var center: EPointType
        @Deprecated("User center() instead", level = DeprecationLevel.WARNING)
        get() = center()
        set(value) {
            origin.x = value.x - width / 2
            origin.y = value.y - height / 2
        }


    //////
    fun selfOffset(x: Number = 0, y: Number = 0): ERect = offset(x, y, this)

    fun selfInset(margin: Number) = inset(margin, margin, this)
    fun selfInset(p: EPointType) = inset(p.x, p.y, this)
    fun selfInset(x: Number = 0, y: Number = 0) = inset(x, y, this)

    fun selfExpand(margin: Number) = expand(margin, margin, this)
    fun selfExpand(p: EPointType) = expand(p.x, p.y, this)
    fun selfExpand(x: Number = 0f, y: Number = 0f) = inset(-x.f, -y.f, this)

    fun selfPadding(padding: EOffset) = padding(padding, this)


    fun selfScaleAnchor(factor: Number, anchor: EPointType) = scaleAnchor(factor, anchor, this)

    fun selfScaleRelative(factor: Number, point: EPointType) = scaleRelative(factor, point, this)

}

fun ERectCenter(
    position: EPointType,
    width: Number, height: Number, buffer: ERect = ERect()
) = ERectCenter(position.x, position.y, width, height, buffer)

fun ERectCenter(
    x: Number = 0f, y: Number = 0f,
    width: Number, height: Number, buffer: ERect = ERect()
): ERect {

    val width = width.f
    val height = height.f
    val x = x.f - width / 2
    val y = y.f - height / 2


    return buffer.set(x = x, y = y, width = width, height = height)
}


fun ERectCorners(
    corner1: EPointType,
    corner2: EPointType,
    buffer: ERect = ERect()
) = ERectCorners(corner1.x, corner1.y, corner2.x, corner2.y, buffer)

fun ERectCorners(
    corner1X: Number = 0,
    corner1Y: Number = 0,
    corner2X: Number = 0,
    corner2Y: Number = 0,
    buffer: ERect = ERect()
): ERect {
    return ERectSides(
        top = Math.min(corner1Y.d, corner2Y.d),
        bottom = Math.max(corner1Y.d, corner2Y.d),
        left = Math.min(corner1X.d, corner2X.d),
        right = Math.max(corner1X.d, corner2X.d),
        buffer = buffer
    )
}

fun ERectSides(left: Number, top: Number, right: Number, bottom: Number, buffer: ERect = ERect()): ERect {
    buffer.top = top.f
    buffer.left = left.f
    buffer.right = right.f
    buffer.bottom = bottom.f
    return buffer
}

fun ERectAnchorPos(anchor: EPointType, position: EPointType, size: ESize, buffer: ERect = ERect()) =
    buffer.set(
        x = position.x - size.width * anchor.x,
        y = position.y - size.height * anchor.y,
        size = size
    )




