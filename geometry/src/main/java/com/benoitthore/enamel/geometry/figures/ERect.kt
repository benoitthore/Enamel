package com.benoitthore.enamel.geometry.figures

import com.benoitthore.enamel.geometry.alignement.EAlignment
import com.benoitthore.enamel.geometry.Resetable
import com.benoitthore.enamel.core.math.*
import com.benoitthore.enamel.geometry.allocateDebugMessage
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.primitives.*
import com.benoitthore.enamel.geometry.svg.ESVG
import com.benoitthore.enamel.geometry.svg.SVGContext

interface ERect : ESVG {

    val origin: EPoint
    val size: ESize

    override fun addTo(context: SVGContext) {
        context.rect(this)
    }

    fun toMutable(target: ERectMutable = E.RectMutable()) =
        target.set(origin.toMutable(target.origin), size.toMutable())

    fun toImmutable() = E.Rect(this)
    fun copy(target: ERectMutable = E.RectMutable()) =
        E.RectMutable(origin.copy(target = target.origin), size.copy(target = target.size))

    val height get() = size.height
    val width get() = size.width

    val top: Float
        get() = origin.y
    val bottom: Float
        get() = top + height
    val left: Float
        get() = origin.x
    val right: Float
        get() = origin.x + width

    val x: Float
        get() = origin.x
    val y: Float
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
        target: EPointMutable = E.PointMutable()
    ): EPointMutable =
        target.set(x = pointAtAnchorX(x), y = pointAtAnchorY(y))

    fun pointAtAnchor(anchor: EPoint, target: EPointMutable = E.PointMutable()) =
        pointAtAnchor(anchor.x, anchor.y, target)

    fun pointAtAnchorX(x: Number) = origin.x + size.width * x.f
    fun pointAtAnchorY(y: Number) = origin.y + size.height * y.f

    fun anchorAtPoint(
        x: Number,
        y: Number,
        target: EPointMutable = E.PointMutable()
    ): EPointMutable {
        val x = if (width == 0f) .5f else x.f / width
        val y = if (height == 0f) .5f else y.f / height
        return target.set(x, y)
    }

    fun center(target: EPointMutable = E.PointMutable()): EPointMutable =
        pointAtAnchor(0.5f, 0.5f, target)

    fun topLeft(target: EPointMutable = E.PointMutable()): EPointMutable =
        pointAtAnchor(0f, 0f, target)

    fun topRight(target: EPointMutable = E.PointMutable()): EPointMutable =
        pointAtAnchor(1f, 0.0f, target)

    fun bottomRight(target: EPointMutable = E.PointMutable()): EPointMutable =
        pointAtAnchor(1f, 1f, target)

    fun bottomLeft(target: EPointMutable = E.PointMutable()): EPointMutable =
        pointAtAnchor(0f, 1f, target)


    // Alignement
    fun rectAlignedInside(
        alignment: EAlignment,
        size: ESize,
        spacing: Number = 0,
        target: ERectMutable = E.RectMutable(this)
    ): ERectMutable {
        target.set(this)
        val spacing = spacing.f

        val anchor = alignment.namedPoint
        val spacingSign = alignment.spacingSign

        val position = pointAtAnchor(alignment.namedPoint, target = target.origin)
            .offset(spacingSign.x * spacing, spacingSign.y * spacing, target = target.origin)

        target.size.set(size)

        return E.RectMutableAnchorPos(
            anchor = anchor,
            position = position,
            size = target.size,
            target = target
        )
    }


    fun rectAlignedOutside(
        alignment: EAlignment,
        size: ESize,
        spacing: Number = 0,
        target: ERectMutable = E.RectMutable()
    ): ERectMutable {
        target.set(this)
        val spacing = spacing.f

        val anchor = alignment.flipped.namedPoint
        val spacingSign = alignment.flipped.spacingSign

        val position = pointAtAnchor(alignment.namedPoint, target = target.origin)
            .offset(spacingSign.x * spacing, spacingSign.y * spacing, target = target.origin)

        target.size.set(size)

        return E.RectMutableAnchorPos(
            anchor = anchor,
            position = position,
            size = target.size,
            target = target
        )
    }


    // Changing
    fun offset(p: Tuple2, target: ERectMutable = E.RectMutable()): ERectMutable =
        offset(p.v1, p.v2, target)

    fun offset(
        x: Number = 0,
        y: Number = 0,
        target: ERectMutable = E.RectMutable()
    ): ERectMutable {
        target.set(this)
        target.origin.selfOffset(x, y)
        return target
    }

    // TODO Expand/Inset/Padding -> align
    fun inset(margin: Number, target: ERectMutable = E.RectMutable()) =
        inset(margin, margin, target)

    fun inset(p: Tuple2, target: ERectMutable = E.RectMutable()) = inset(p.v1, p.v2, target)
    fun inset(
        x: Number = 0,
        y: Number = 0,
        target: ERectMutable = E.RectMutable()
    ) = inset(left = x, top = y, right = x, bottom = y, target = target)

    fun inset(
        left: Number = 0,
        top: Number = 0,
        right: Number = 0,
        bottom: Number = 0,
        target: ERectMutable = E.RectMutable()
    ): ERectMutable {
        target.set(this)
        target.left += left.toFloat()
        target.top += top.toFloat()
        target.bottom -= bottom.toFloat()
        target.right -= right.toFloat()
        return target
    }

    fun expand(margin: Number, target: ERectMutable = E.RectMutable()) =
        expand(margin, margin, target)

    fun expand(p: Tuple2, target: ERectMutable = E.RectMutable()) = expand(p.v1, p.v2, target)
    fun expand(x: Number = 0f, y: Number = 0f, target: ERectMutable = E.RectMutable()) =
        inset(-x.f, -y.f, target)

    fun expand(
        left: Number = 0,
        top: Number = 0,
        right: Number = 0,
        bottom: Number = 0,
        target: ERectMutable = E.RectMutable()
    ): ERectMutable = inset(
        left = -left.toFloat(),
        top = -top.toFloat(),
        right = -right.toFloat(),
        bottom = -bottom.toFloat(),
        target = target
    )

    fun expand(padding: EOffset, target: ERectMutable = E.RectMutable()) = expand(
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
        , target: ERectMutable = E.RectMutable()
    ): ERectMutable {
        target.set(this)
        target.left += left.f
        target.top += top.f
        target.bottom -= bottom.f
        target.right -= right.f
        return target
    }

    fun padding(padding: EOffset, target: ERectMutable = E.RectMutable()): ERectMutable =
        padding(
            left = padding.left,
            top = padding.top,
            bottom = padding.bottom,
            right = padding.right,
            target = target
        )

    // TODO Make self version
    fun scale(t: Tuple2, target: ERectMutable = E.RectMutable()): ERectMutable =
        scale(t.v1, t.v2, target)

    fun scale(x: Number, y: Number, target: ERectMutable = E.RectMutable()): ERectMutable {
        target.x = this.x * x.f
        target.y = this.y * y.f
        target.width = this.width * x.f
        target.height = this.height * y.f

        return target
    }

    fun scaleAnchor(factor: Number, anchor: EPoint, target: ERectMutable = E.RectMutable()) =
        scaleAnchor(factor, anchor.x, anchor.y, target)


    fun scaleAnchor(
        factor: Number,
        anchorX: Number,
        anchorY: Number,
        target: ERectMutable = E.RectMutable()
    ) = scaleRelative(
        factor,
        pointAtAnchorX(anchorX),
        pointAtAnchorY(anchorY),
        target
    )


    fun scaleRelative(
        factor: Number,
        point: EPoint,
        target: ERectMutable = E.RectMutable()
    ) = scaleRelative(factor, point.x, point.y, target)

    fun scaleRelative(
        factor: Number,
        pointX: Number,
        pointY: Number,
        target: ERectMutable = E.RectMutable()
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
            E.PointMutable(),
            E.PointMutable(),
            E.PointMutable(),
            E.PointMutable()
        )
    ): List<EPointMutable> {
        require(target.size == 4) {
            "Needs 4 points in target"
        }
        target[0].set(top, left)
        target[1].set(top, right)
        target[2].set(bottom, right)
        target[3].set(bottom, left)
        return target
    }

    /***
     * @return the diagonal going from top right to bottom left
     */
    fun diagonalTRBL(target: ELineMutable = E.LineMutable()): ELineMutable {
        topRight(target.start)
        bottomLeft(target.end)
        return target
    }

    /***
     * @return the diagonal going from top left to bottom right
     */
    fun diagonalTLBR(target: ELineMutable = E.LineMutable()): ELineMutable {
        topLeft(target.start)
        bottomRight(target.end)
        return target
    }

}

interface ERectMutable : ERect, Resetable {
    override val origin: EPointMutable
    override val size: ESizeMutable

    class Impl internal constructor(x: Number, y: Number, width: Number, height: Number) :
        ERectMutable {
        init {
            allocateDebugMessage()
        }

        override val origin: EPointMutable = E.PointMutable(x, y)
        override val size: ESizeMutable = E.SizeMutable(width, height)
    }

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

    fun setCenter(point: EPoint) = setCenter(point.x, point.y)
    fun setCenter(x: Number, y: Number) = apply {
        origin.x = x.f - width / 2
        origin.y = y.f - height / 2
    }

    fun setSides(
        left: Number = this.left,
        top: Number = this.top,
        right: Number = this.right,
        bottom: Number = this.bottom
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
