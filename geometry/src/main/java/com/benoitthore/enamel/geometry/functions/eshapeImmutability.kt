package com.benoitthore.enamel.geometry.functions

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.alignement.NamedPoint
import com.benoitthore.enamel.geometry.builders.*
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.figures.line.ELine
import com.benoitthore.enamel.geometry.figures.line.ELineMutable
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.offset.EOffset
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.Tuple2
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable

fun EShape.toRect(target: ERect = Rect()): ERect = target.setBounds(this)


// Points
fun EShape.pointAtAnchor(
    x: Number,
    y: Number,
    target: EPointMutable = MutablePoint()
): EPoint =
    target.set(x = pointAtAnchorX(x), y = pointAtAnchorY(y))

fun EShape.pointAtAnchor(anchor: EPoint, target: EPointMutable = MutablePoint()) =
    pointAtAnchor(anchor.x, anchor.y, target)

internal fun EShape.pointAtAnchorX(x: Number) = this.originX.f + width * x.f
internal fun EShape.pointAtAnchorY(y: Number) = this.originY.f + height * y.f
internal fun EShape.anchorAtPointX(x: Number) = if (width == 0f) .5f else x.f / width
internal fun EShape.anchorAtPointY(y: Number) = if (height == 0f) .5f else y.f / height

fun EShape.anchorAtPoint(
    x: Number,
    y: Number,
    target: EPointMutable = MutablePoint()
): EPoint {
    val x = anchorAtPointX(x)
    val y = anchorAtPointY(y)
    return target.set(x, y)
}

fun EShape.getCenter(target: EPointMutable = MutablePoint()): EPoint =
    pointAtAnchor(NamedPoint.center, target)

fun EShape.getTopLeft(target: EPointMutable = MutablePoint()): EPoint =
    pointAtAnchor(NamedPoint.topLeft, target)

fun EShape.getTopRight(target: EPointMutable = MutablePoint()): EPoint =
    pointAtAnchor(NamedPoint.topRight, target)

fun EShape.getBottomRight(target: EPointMutable = MutablePoint()): EPoint =
    pointAtAnchor(NamedPoint.bottomRight, target)

fun EShape.getBottomLeft(target: EPointMutable = MutablePoint()): EPoint =
    pointAtAnchor(NamedPoint.bottomLeft, target)


// TODO Move
fun ERect.toPointList(
    target: List<EPointMutable> = listOf(
        MutablePoint(),
        MutablePoint(),
        MutablePoint(),
        MutablePoint()
    )
): List<EPointMutable> {
    require(target.size > 4) {
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
fun EShape.diagonalTRBL(target: ELineMutable = MutableLine()): ELine {
    getTopRight(target.start)
    getBottomLeft(target.end)
    return target
}

/***
 * @return the diagonal going from top left to bottom right
 */
fun EShape.diagonalTLBR(target: ELineMutable = MutableLine()): ELine {
    getTopLeft(target.start)
    getBottomRight(target.end)
    return target
}


fun <T : EShape> T.map(
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

    return target.setBounds(
        left = left,
        top = top,
        bottom = bottom,
        right = right
    )
}

fun List<EShape>.union(target: ERect = Rect()): ERect {
    if (isEmpty()) {
        return Rect()
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
    return RectSides(
        top = top,
        left = left,
        right = right,
        bottom = bottom,
        target = target
    )
}

