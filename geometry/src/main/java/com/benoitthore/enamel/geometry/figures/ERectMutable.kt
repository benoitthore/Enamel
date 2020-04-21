package com.benoitthore.enamel.geometry.figures

import com.benoitthore.enamel.geometry.alignement.EAlignment
import com.benoitthore.enamel.geometry.Resetable
import com.benoitthore.enamel.core.math.*
import com.benoitthore.enamel.geometry.allocateDebugMessage
import com.benoitthore.enamel.geometry.primitives.*

/*
This class should be the example to follow in order to implement mutability

Note:

Immutable doesn't have any function requiring a target, but:
- Only toImmutable() allocates without providing a target
- Only toMutable() requires a target

Create the API without default arguments for target in order to make sure no allocation is done by the library itself, then the user can decide


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

    fun toMutable(target: ERectMutable = ERectMutable()) =
        target.set(origin.toMutable(target.origin), size.toMutable())

    fun toImmutable() = ERect(origin.toImmutable(), size.toImmutable())
    fun copy(target: ERectMutable = ERectMutable()) =
        ERectMutable(origin.copy(target = target.origin), size.copy(target = target.size))

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
        target: EPointMutable = EPointMutable()
    ): EPointMutable =
        target.set(x = pointAtAnchorX(x), y = pointAtAnchorY(y))

    fun pointAtAnchor(anchor: EPoint, target: EPointMutable = EPointMutable()) =
        pointAtAnchor(anchor.x, anchor.y, target)

    fun pointAtAnchorX(x: Number) = origin.x + size.width * x.f
    fun pointAtAnchorY(y: Number) = origin.y + size.height * y.f

    fun anchorAtPoint(
        x: Number,
        y: Number,
        target: EPointMutable = EPointMutable()
    ): EPointMutable {
        val x = if (width == 0f) .5f else x.f / width
        val y = if (height == 0f) .5f else y.f / height
        return target.set(x, y)
    }

    fun center(target: EPointMutable = EPointMutable()): EPointMutable =
        pointAtAnchor(0.5f, 0.5f, target)

    fun topLeft(target: EPointMutable = EPointMutable()): EPointMutable =
        pointAtAnchor(0f, 0f, target)

    fun topRight(target: EPointMutable = EPointMutable()): EPointMutable =
        pointAtAnchor(1f, 0.0f, target)

    fun bottomRight(target: EPointMutable = EPointMutable()): EPointMutable =
        pointAtAnchor(1f, 1f, target)

    fun bottomLeft(target: EPointMutable = EPointMutable()): EPointMutable =
        pointAtAnchor(0f, 1f, target)


    // Alignement
    fun rectAlignedInside(
        aligned: EAlignment,
        size: ESize,
        spacing: Number = 0,
        target: ERectMutable = ERectMutable(this)
    ): ERectMutable {
        target.set(this)
        val spacing = spacing.f

        val anchor = aligned.namedPoint
        val spacingSign = aligned.spacingSign

        val position = pointAtAnchor(aligned.namedPoint, target = target.origin)
            .offset(spacingSign.x * spacing, spacingSign.y * spacing, target = target.origin)

        target.size.set(size)

        return ERectAnchorPos(
            anchor = anchor,
            position = position,
            size = target.size,
            target = target
        )
    }


    fun rectAlignedOutside(
        aligned: EAlignment,
        size: ESize,
        spacing: Number = 0,
        target: ERectMutable = ERectMutable()
    ): ERectMutable {
        target.set(this)
        val spacing = spacing.f

        val anchor = aligned.flipped.namedPoint
        val spacingSign = aligned.flipped.spacingSign

        val position = pointAtAnchor(aligned.namedPoint, target = target.origin)
            .offset(spacingSign.x * spacing, spacingSign.y * spacing, target = target.origin)

        target.size.set(size)

        return ERectAnchorPos(
            anchor = anchor,
            position = position,
            size = target.size,
            target = target
        )
    }


    // Changing
    fun offset(p: Tuple2, target: ERectMutable = ERectMutable()): ERectMutable =
        offset(p.v1, p.v2, target)

    fun offset(
        x: Number = 0,
        y: Number = 0,
        target: ERectMutable = ERectMutable()
    ): ERectMutable {
        target.set(this)
        target.origin.selfOffset(x, y)
        return target
    }

    // TODO Expand/Inset/Padding -> align
    fun inset(margin: Number, target: ERectMutable = ERectMutable()) =
        inset(margin, margin, target)
    fun inset(p: Tuple2, target: ERectMutable = ERectMutable()) = inset(p.v1, p.v2, target)
    fun inset(
        x: Number = 0,
        y: Number = 0,
        target: ERectMutable = ERectMutable()
    ) = inset(left = x, top = y, right = x, bottom = y, target = target)
    fun inset(
        left: Number = 0,
        top: Number = 0,
        right: Number = 0,
        bottom: Number = 0,
        target: ERectMutable = ERectMutable()
    ): ERectMutable {
        target.set(this)
        target.left += left.toFloat()
        target.top += top.toFloat()
        target.bottom -= bottom.toFloat()
        target.right -= right.toFloat()
        return target
    }

    fun expand(margin: Number, target: ERectMutable = ERectMutable()) =
        expand(margin, margin, target)

    fun expand(p: Tuple2, target: ERectMutable = ERectMutable()) = expand(p.v1, p.v2, target)
    fun expand(x: Number = 0f, y: Number = 0f, target: ERectMutable = ERectMutable()) =
        inset(-x.f, -y.f, target)

    fun expand(
        left: Number = 0,
        top: Number = 0,
        right: Number = 0,
        bottom: Number = 0,
        target: ERectMutable = ERectMutable()
    ): ERectMutable = inset(
        left = -left.toFloat(),
        top = -top.toFloat(),
        right = -right.toFloat(),
        bottom = -bottom.toFloat(),
        target = target
    )

    fun expand(padding: EOffset, target: ERectMutable = ERectMutable()) = expand(
        left = padding.left,
        top = padding.top,
        right = padding.right,
        bottom = padding.bottom,
        target = target
    )

    fun padding(
        top: Number = this.top,
        bottom: Number = this.bottom,
        left: Number = this.left,
        right: Number = this.right
        , target: ERectMutable = ERectMutable()
    ): ERectMutable {
        target.set(this)
        target.left += left.f
        target.top += top.f
        target.bottom -= bottom.f
        target.right -= right.f
        return target
    }

    fun padding(padding: EOffset, target: ERectMutable = ERectMutable()): ERectMutable =
        padding(
            left = padding.left,
            top = padding.top,
            bottom = padding.bottom,
            right = padding.right,
            target = target
        )

    // TODO Make self version
    fun scale(t: Tuple2, target: ERectMutable = ERectMutable()): ERectMutable =
        scale(t.v1, t.v2, target)

    fun scale(x: Number, y: Number, target: ERectMutable = ERectMutable()): ERectMutable {
        target.x = this.x * x.f
        target.y = this.y * y.f
        target.width = this.width * x.f
        target.height = this.height * y.f

        return target
    }

    fun scaleAnchor(factor: Number, anchor: EPoint, target: ERectMutable = ERectMutable()) =
        scaleAnchor(factor, anchor.x, anchor.y, target)


    fun scaleAnchor(
        factor: Number,
        anchorX: Number,
        anchorY: Number,
        target: ERectMutable = ERectMutable()
    ) = scaleRelative(
        factor,
        pointAtAnchorX(anchorX),
        pointAtAnchorY(anchorY),
        target
    )


    fun scaleRelative(
        factor: Number,
        point: EPoint,
        target: ERectMutable = ERectMutable()
    ) = scaleRelative(factor, point.x, point.y, target)

    fun scaleRelative(
        factor: Number,
        pointX: Number,
        pointY: Number,
        target: ERectMutable = ERectMutable()
    ): ERectMutable {
        target.set(this)

        val factor = factor.f
        val newX = origin.x + (pointX.f - origin.x) * (1f - factor)
        val newY = origin.y + (pointY.f - origin.y) * (1f - factor)

        target.origin.set(newX, newY)
        target.size.width *= factor
        target.size.height *= factor
        return target
    }

    fun toPointList(
        target: List<EPointMutable> = listOf(
            EPointMutable(),
            EPointMutable(),
            EPointMutable(),
            EPointMutable()
        )
    ): List<EPointMutable> {
        if (target.size != 4) {
            throw Exception("Needs 4 points in target")
        }
        target[0].set(top, left)
        target[1].set(top, right)
        target[2].set(bottom, right)
        target[3].set(bottom, left)
        return target
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
        top: Number = 0,
        bottom: Number = 0,
        left: Number = 0,
        right: Number = 0
    ) = padding(
        top = top,
        bottom = bottom,
        left = left,
        right = right,
        target = this
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
        scaleRelative(factor = factor, pointX = pointX, pointY = pointY, target = this)

}
