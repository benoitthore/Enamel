package com.benoitthore.enamel.geometry.interfaces

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.*
import com.benoitthore.enamel.geometry.primitives.EOffset
import com.benoitthore.enamel.geometry.primitives.EPoint
import com.benoitthore.enamel.geometry.primitives.EPointMutable
import com.benoitthore.enamel.geometry.primitives.Tuple2

// TODO Add to Oval, Line
interface HasBounds {
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
}


// contains Point
fun HasBounds.contains(x: Number, y: Number) = contains(x, y, 0, 0)

fun HasBounds.contains(p: EPoint) = contains(p.x, p.y)

// contains Circle: When dealing with circles, use x and y as center
fun HasBounds.contains(p: EPoint, radius: Number): Boolean = contains(p.x, p.y, radius)

fun HasBounds.contains(c: ECircle): Boolean = contains(c.center, c.radius)

fun HasBounds.contains(x: Number, y: Number, radius: Number): Boolean =
    radius.f.let { radius ->
        contains(
            x.f - radius,
            y.f - radius,
            radius * 2,
            radius * 2
        )
    }

// contains Rect
fun HasBounds.contains(other: ERect) = contains(other.origin, other.size)

fun HasBounds.contains(origin: EPoint, size: ESize) =
    contains(origin.x, origin.y, size.width, size.height)

fun HasBounds.contains(x: Number, y: Number, width: Number, height: Number): Boolean {
    val x = x.f
    val y = y.f
    val width = width.f
    val height = height.f
    return x >= left && x + width < right && y >= top && y + height < bottom
}

fun HasBounds.containsFull(p: EPoint, radius: Number): Boolean = containsFull(p.x, p.y, radius)

fun HasBounds.containsFull(c: ECircle): Boolean = containsFull(c.center, c.radius)

// TODO The functions considers the circle to be a square which doesn't work on the edges
fun HasBounds.containsFull(x: Number, y: Number, radius: Number): Boolean =
    radius.f.let { radius ->
        containsFull(
            x.f - radius,
            y.f - radius,
            radius * 2,
            radius * 2
        )
    }

fun HasBounds.containsFull(other: ERect) = contains(other.origin, other.size)

fun HasBounds.containsFull(origin: EPoint, size: ESize) =
    containsFull(origin.x, origin.y, size.width, size.height)

fun HasBounds.containsFull(x: Number, y: Number, width: Number, height: Number): Boolean {
    val x = x.f
    val y = y.f
    val width = width.f
    val height = height.f
    return x + width >= left && x + width < right
            && y + height >= top && y + height < bottom
}


//intersects
fun HasBounds.intersects(other: ERect) = intersects(
    top = other.top,
    left = other.left,
    right = other.right,
    bottom = other.bottom
)

fun HasBounds.intersects(
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
fun HasBounds.pointAtAnchor(
    x: Number,
    y: Number,
    target: EPointMutable = E.PointMutable()
): EPointMutable =
    target.set(x = pointAtAnchorX(x), y = pointAtAnchorY(y))

fun HasBounds.pointAtAnchor(anchor: EPoint, target: EPointMutable = E.PointMutable()) =
    pointAtAnchor(anchor.x, anchor.y, target)

fun HasBounds.pointAtAnchorX(x: Number) = this.originX.f + width * x.f
fun HasBounds.pointAtAnchorY(y: Number) = this.originY.f + height * y.f

fun HasBounds.anchorAtPoint(
    x: Number,
    y: Number,
    target: EPointMutable = E.PointMutable()
): EPointMutable {
    val x = if (width == 0f) .5f else x.f / width
    val y = if (height == 0f) .5f else y.f / height
    return target.set(x, y)
}

fun HasBounds.center(target: EPointMutable = E.PointMutable()): EPointMutable =
    pointAtAnchor(0.5f, 0.5f, target)

fun HasBounds.topLeft(target: EPointMutable = E.PointMutable()): EPointMutable =
    pointAtAnchor(0f, 0f, target)

fun HasBounds.topRight(target: EPointMutable = E.PointMutable()): EPointMutable =
    pointAtAnchor(1f, 0.0f, target)

fun HasBounds.bottomRight(target: EPointMutable = E.PointMutable()): EPointMutable =
    pointAtAnchor(1f, 1f, target)

fun HasBounds.bottomLeft(target: EPointMutable = E.PointMutable()): EPointMutable =
    pointAtAnchor(0f, 1f, target)


// Changing
fun HasBounds.offset(p: Tuple2, target: CanSetBounds = E.RectMutable()): CanSetBounds =
    offset(p.v1, p.v2, target)

fun HasBounds.offset(
    x: Number = 0,
    y: Number = 0,
    target: CanSetBounds = E.RectMutable()
): CanSetBounds {
    target.set(x.f, y.f, width, height)
    return target
}

// TODO Expand/Inset/Padding -> align
fun HasBounds.inset(margin: Number, target: CanSetBounds = E.RectMutable()) =
    inset(margin, margin, target)

fun HasBounds.inset(p: Tuple2, target: CanSetBounds = E.RectMutable()) = inset(p.v1, p.v2, target)
fun HasBounds.inset(
    x: Number = 0,
    y: Number = 0,
    target: CanSetBounds = E.RectMutable()
) = inset(left = x, top = y, right = x, bottom = y, target = target)

fun HasBounds.inset(
    left: Number = 0,
    top: Number = 0,
    right: Number = 0,
    bottom: Number = 0,
    target: CanSetBounds = E.RectMutable()
): CanSetBounds {
    target.setBounds(
        left = this.left + left.toFloat(),
        top = this.top + top.toFloat(),
        bottom = this.bottom - bottom.toFloat(),
        right = this.right - right.toFloat()
    )
    return target
}

fun HasBounds.expand(margin: Number, target: CanSetBounds = E.RectMutable()) =
    expand(margin, margin, target)

fun HasBounds.expand(p: Tuple2, target: CanSetBounds = E.RectMutable()) = expand(p.v1, p.v2, target)
fun HasBounds.expand(x: Number = 0f, y: Number = 0f, target: CanSetBounds = E.RectMutable()) =
    inset(-x.f, -y.f, target)

fun HasBounds.expand(
    left: Number = 0,
    top: Number = 0,
    right: Number = 0,
    bottom: Number = 0,
    target: CanSetBounds = E.RectMutable()
): CanSetBounds = inset(
    left = -left.toFloat(),
    top = -top.toFloat(),
    right = -right.toFloat(),
    bottom = -bottom.toFloat(),
    target = target
)

fun HasBounds.expand(padding: EOffset, target: CanSetBounds = E.RectMutable()) = expand(
    left = padding.left,
    top = padding.top,
    right = padding.right,
    bottom = padding.bottom,
    target = target
)

fun HasBounds.padding(
    top: Number = this.top,
    bottom: Number = this.bottom,
    left: Number = this.left,
    right: Number = this.right,
    target: CanSetBounds = E.RectMutable()
): CanSetBounds = target.inset(
    left = left,
    top = top,
    bottom = bottom,
    right = right
)

fun HasBounds.padding(padding: EOffset, target: CanSetBounds = E.RectMutable()): CanSetBounds =
    padding(
        left = padding.left,
        top = padding.top,
        bottom = padding.bottom,
        right = padding.right,
        target = target
    )

// TODO Make self version
fun HasBounds.scale(t: Tuple2, target: CanSetBounds = E.RectMutable()): CanSetBounds =
    scale(t.v1, t.v2, target)

fun HasBounds.scale(x: Number, y: Number, target: CanSetBounds = E.RectMutable()): CanSetBounds {
    target.setOriginSize(
        originX = this.originX * x.f,
        originY = this.originY * y.f,
        width = this.width * x.f,
        height = this.height * y.f
    )

    return target
}

fun HasBounds.scaleAnchor(factor: Number, anchor: EPoint, target: CanSetBounds = E.RectMutable()) =
    scaleAnchor(factor, anchor.x, anchor.y, target)


fun HasBounds.scaleAnchor(
    factor: Number,
    anchorX: Number,
    anchorY: Number,
    target: CanSetBounds = E.RectMutable()
) = scaleRelative(
    factor,
    pointAtAnchorX(anchorX),
    pointAtAnchorY(anchorY),
    target
)


fun HasBounds.scaleRelative(
    factor: Number,
    point: EPoint,
    target: CanSetBounds = E.RectMutable()
) = scaleRelative(factor, point.x, point.y, target)

fun HasBounds.scaleRelative(
    factor: Number,
    pointX: Number,
    pointY: Number,
    target: CanSetBounds = E.RectMutable()
): CanSetBounds {
    target.set(this)

    val factor = factor.f

    target.setOriginSize(
        originX = originX + (pointX.f - originX) * (1f - factor),
        originY = originY + (pointY.f - originY) * (1f - factor),
        width = width * factor,
        height = height * factor
    )
    return target
}

fun HasBounds.toPointList(
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
fun HasBounds.diagonalTRBL(target: ELineMutable = E.LineMutable()): ELineMutable {
    topRight(target.start)
    bottomLeft(target.end)
    return target
}

/***
 * @return the diagonal going from top left to bottom right
 */
fun HasBounds.diagonalTLBR(target: ELineMutable = E.LineMutable()): ELineMutable {
    topLeft(target.start)
    bottomRight(target.end)
    return target
}