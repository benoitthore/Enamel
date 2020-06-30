package com.benoitthore.enamel.geometry.interfaces.bounds

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.figures.line.ELine
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.offset.EOffset
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable
import com.benoitthore.enamel.geometry.primitives.Tuple2

fun EShape<*>.toRect(target: ERect = E.RectMutable()): ERect = target.setBounds(this)

// contains Point
fun EShape<*>.contains(x: Number, y: Number) = contains(x, y, 0, 0)

fun EShape<*>.contains(p: EPoint) = contains(p.x, p.y)

// contains Circle: When dealing with circles, use x and y as center
fun EShape<*>.contains(p: EPoint, radius: Number): Boolean = contains(p.x, p.y, radius)

fun EShape<*>.contains(c: ECircle): Boolean = contains(c.center, c.radius)

fun EShape<*>.contains(x: Number, y: Number, radius: Number): Boolean =
    radius.f.let { radius ->
        contains(
            x.f - radius,
            y.f - radius,
            radius * 2,
            radius * 2
        )
    }

// contains Rect
fun EShape<*>.contains(other: ERect) = contains(other.origin, other.size)

fun EShape<*>.contains(origin: EPoint, size: ESize) =
    contains(origin.x, origin.y, size.width, size.height)

fun EShape<*>.contains(x: Number, y: Number, width: Number, height: Number): Boolean {
    val x = x.f
    val y = y.f
    val width = width.f
    val height = height.f
    return x >= left && x + width < right && y >= top && y + height < bottom
}

fun EShape<*>.containsFull(p: EPoint, radius: Number): Boolean =
    containsFull(p.x, p.y, radius)

fun EShape<*>.containsFull(c: ECircle): Boolean = containsFull(c.center, c.radius)

// TODO The functions considers the circle to be a square which doesn't work on the edges
fun EShape<*>.containsFull(x: Number, y: Number, radius: Number): Boolean =
    radius.f.let { radius ->
        containsFull(
            x.f - radius,
            y.f - radius,
            radius * 2,
            radius * 2
        )
    }

fun EShape<*>.containsFull(other: EShape<*>) =
    contains(other.originX, other.originY, other.width, other.height)

fun EShape<*>.containsFull(origin: EPoint, size: ESize) =
    containsFull(origin.x, origin.y, size.width, size.height)

fun EShape<*>.containsFull(x: Number, y: Number, width: Number, height: Number): Boolean {
    val x = x.f
    val y = y.f
    val width = width.f
    val height = height.f
    return x + width >= left && x + width < right
            && y + height >= top && y + height < bottom
}


//intersects
fun EShape<*>.intersects(other: ERect) = intersects(
    top = other.top,
    left = other.left,
    right = other.right,
    bottom = other.bottom
)

fun EShape<*>.intersects(
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
fun EShape<*>.pointAtAnchor(
    x: Number,
    y: Number,
    target: EPointMutable = E.PointMutable()
): EPointMutable =
    target.set(x = pointAtAnchorX(x), y = pointAtAnchorY(y))

fun EShape<*>.pointAtAnchor(anchor: EPoint, target: EPointMutable = E.PointMutable()) =
    pointAtAnchor(anchor.x, anchor.y, target)

internal fun EShape<*>.pointAtAnchorX(x: Number) = this.originX.f + width * x.f
internal fun EShape<*>.pointAtAnchorY(y: Number) = this.originY.f + height * y.f
internal fun EShape<*>.anchorAtPointX(x: Number) = if (width == 0f) .5f else x.f / width
internal fun EShape<*>.anchorAtPointY(y: Number) = if (height == 0f) .5f else y.f / height

fun EShape<*>.anchorAtPoint(
    x: Number,
    y: Number,
    target: EPointMutable = E.PointMutable()
): EPointMutable {
    val x = anchorAtPointX(x)
    val y = anchorAtPointY(y)
    return target.set(x, y)
}

fun EShape<*>.center(target: EPointMutable = E.PointMutable()): EPointMutable =
    pointAtAnchor(0.5f, 0.5f, target)

fun EShape<*>.topLeft(target: EPointMutable = E.PointMutable()): EPointMutable =
    pointAtAnchor(0f, 0f, target)

fun EShape<*>.topRight(target: EPointMutable = E.PointMutable()): EPointMutable =
    pointAtAnchor(1f, 0.0f, target)

fun EShape<*>.bottomRight(target: EPointMutable = E.PointMutable()): EPointMutable =
    pointAtAnchor(1f, 1f, target)

fun EShape<*>.bottomLeft(target: EPointMutable = E.PointMutable()): EPointMutable =
    pointAtAnchor(0f, 1f, target)


// TODO Move
fun ERect.toPointList(
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
fun EShape<*>.diagonalTRBL(target: ELine = E.LineMutable()): ELine {
    topRight(target.start)
    bottomLeft(target.end)
    return target
}

/***
 * @return the diagonal going from top left to bottom right
 */
fun EShape<*>.diagonalTLBR(target: ELine = E.LineMutable()): ELine {
    topLeft(target.start)
    bottomRight(target.end)
    return target
}


fun <T : EShape<T>> T.map(
    fromX: Number,
    fromY: Number,
    fromWidth: Number,
    fromHeight: Number,
    toX: Number,
    toY: Number,
    toWidth: Number,
    toHeight: Number,
    target: T = copy()
): T {
    target.setBounds(this)

    val anchorLeft = if (fromWidth == 0f) .5f else (target.originX - fromX.f) / fromWidth.f
    val anchorTop = if (fromHeight == 0f) .5f else (target.originY - fromY.f) / fromHeight.f
    val anchorRight =
        if (fromWidth == 0f) .5f else (target.originX - fromX.f + target.width) / fromWidth.f
    val anchorBottom =
        if (fromHeight == 0f) .5f else (target.originY - fromY.f + target.height) / fromHeight.f

    val left = toX.f + toWidth.f * anchorLeft.f
    val top = toY.f + toHeight.f * anchorTop.f

    val right = toX.f + toWidth.f * anchorRight.f
    val bottom = toY.f + toHeight.f * anchorBottom.f

    return target.setSides(
        left = left,
        top = top,
        bottom = bottom,
        right = right
    )
}

fun List<EShape<*>>.union(target: ERect = E.RectMutable()): ERect {
    if (isEmpty()) {
        return E.RectMutable()
    }

    var left = Float.MAX_VALUE
    var top = Float.MAX_VALUE
    var right = Float.MIN_VALUE
    var bottom = Float.MIN_VALUE
    forEach {
        if (it.left < left) {
            left = it.left
        }

        if (it.top < top) {
            top = it.top
        }

        if (it.right > right) {
            right = it.right
        }

        if (it.bottom > bottom) {
            bottom = it.bottom
        }
    }
    return E.RectMutableSides(
        top = top,
        left = left,
        right = right,
        bottom = bottom,
        target = target
    )
}


// Changing
fun <T : EShape<T>> T.offset(
    p: Tuple2, target: T = copy()
): T {
    return offset(p.v1, p.v2, target)
}

fun <T : EShape<T>> T.offset(
    x: Number = 0,
    y: Number = 0,
    target: T = copy()
): T {
    target.setOriginSize(originX + x.f, originY + y.f, width, height)
    return target
}

// TODO Expand/Inset/Padding -> align
fun <T : EShape<T>> T.inset(margin: Number, target: T = copy()) = inset(margin, margin, target)

fun <T : EShape<T>> T.inset(p: Tuple2, target: T = copy()) = inset(p.v1, p.v2, target)

fun <T : EShape<T>> T.inset(
    x: Number = 0,
    y: Number = 0,
    target: T = copy()
) =
    inset(left = x, top = y, right = x, bottom = y, target = target)

fun <T : EShape<T>> T.inset(
    left: Number = 0,
    top: Number = 0,
    right: Number = 0,
    bottom: Number = 0,
    target: T = copy()
): T {
    target.setBounds(
        left = this.left + left.toFloat(),
        top = this.top + top.toFloat(),
        bottom = this.bottom - bottom.toFloat(),
        right = this.right - right.toFloat()
    )
    return target
}

fun <T : EShape<T>> T.expand(margin: Number, target: T = copy()) = expand(margin, margin, target)

fun <T : EShape<T>> T.expand(p: Tuple2, target: T = copy()) = expand(p.v1, p.v2, target)

fun <T : EShape<T>> T.expand(
    x: Number = 0f,
    y: Number = 0f,
    target: T = copy()
) = inset(-x.f, -y.f, target)

fun <T : EShape<T>> T.expand(
    left: Number = 0,
    top: Number = 0,
    right: Number = 0,
    bottom: Number = 0,
    target: T = copy()
) = inset(
    left = -left.toFloat(),
    top = -top.toFloat(),
    right = -right.toFloat(),
    bottom = -bottom.toFloat(),
    target = target
)

fun <T : EShape<T>> T.expand(padding: EOffset, target: T = copy()) = expand(
    left = padding.left,
    top = padding.top,
    right = padding.right,
    bottom = padding.bottom,
    target = target
)

fun <T : EShape<T>> T.padding(
    top: Number = this.top,
    bottom: Number = this.bottom,
    left: Number = this.left,
    right: Number = this.right,
    target: T = copy()
) = inset(
    left = left,
    top = top,
    bottom = bottom,
    right = right,
    target = target
)

fun <T : EShape<T>> T.padding(
    padding: EOffset,
    target: T = copy()
) =
    padding(
        left = padding.left,
        top = padding.top,
        bottom = padding.bottom,
        right = padding.right,
        target = target
    )


fun <T : EShape<T>> T.map(
    from: EShape<*>,
    to: EShape<*>,
    target: T = copy()
) = map(
    fromX = from.originX,
    fromY = from.originY,
    fromWidth = from.width,
    fromHeight = from.height,
    toX = to.originX,
    toY = to.originY,
    toWidth = to.width,
    toHeight = to.height,
    target = target
)
