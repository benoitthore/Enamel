package com.benoitthore.enamel.geometry.functions

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.offset.EOffset
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.Tuple2
import com.benoitthore.enamel.geometry.primitives.point.point

fun <T : EShape<*>> T.setBounds(other: EShape<*>): T = apply {
    _setBounds(
        left = other.left,
        top = other.top,
        right = other.right,
        bottom = other.bottom
    )
}

fun <T : EShape<*>> T.setOriginSize(
    origin: EPoint? = null,
    size: ESize? = null
): T =
    setOriginSize(
        origin?.x ?: originX,
        origin?.y ?: originY,
        size?.width ?: width,
        size?.height ?: height
    )

/**
 * Moves the shape, setting its top left to the current location
 */
fun <T : EShape<*>> T.setTopLeft(x: Number, y: Number): T = apply {
    val x = x.f
    val y = y.f
    _setBounds(
        left = x,
        top = y,
        bottom = y + height,
        right = x + width
    )
}

fun <T : EShape<*>> T.setTopLeft(point: EPoint): T = setTopLeft(point.x, point.y)

fun <T : EShape<*>> T.setTopRight(point: EPoint): T = setTopRight(point.x, point.y)
fun <T : EShape<*>> T.setTopRight(x: Number, y: Number): T = apply {
    val x = x.f
    val y = y.f
    _setBounds(
        top = y,
        right = x,
        bottom = y + height,
        left = x - width
    )
}

fun <T : EShape<*>> T.setBottomRight(point: EPoint): T = setBottomRight(point.x, point.y)
fun <T : EShape<*>> T.setBottomRight(x: Number, y: Number): T = apply {
    val x = x.f
    val y = y.f
    _setBounds(
        right = x,
        bottom = y,
        top = y - height,
        left = x - width
    )
}

fun <T : EShape<*>> T.setBottomLeft(point: EPoint): T = setBottomLeft(point.x, point.y)
fun <T : EShape<*>> T.setBottomLeft(x: Number, y: Number): T = apply {
    val x = x.f
    val y = y.f
    _setBounds(
        left = x,
        bottom = y,
        top = y - height,
        right = x + width
    )
}

fun <T : EShape<*>> T.setOriginSize(
    x: Number = this.originX, y: Number = this.originY,
    width: Number = this.width, height: Number = this.height
) = apply {
    _setBounds(
        left = x,
        top = y,
        right = x.f + width.f,
        bottom = y.f + height.f
    )
}

fun <T : EShape<*>> T.setOrigin(
    x: Number = this.originX, y: Number = this.originY
) = apply {
    _setBounds(
        left = x,
        top = y,
        right = x.f + width.f,
        bottom = y.f + height.f
    )
}

fun <T : EShape<*>> T.setOrigin(origin: EPoint) = setOrigin(origin.x, origin.y)

fun <T : EShape<*>> T.setBounds(
    x: Number = this.originX,
    y: Number = this.originY,
    size: ESize? = null
): T =
    setOriginSize(x, y, size?.width ?: width, size?.height ?: height)


fun <T : EShape<*>> T.setSize(size: ESize): T = setSize(size.width, size.height)
fun <T : EShape<*>> T.setSize(
    width: Number = this.width,
    height: Number = this.height
): T = apply {
    this.width = width.f
    this.height = height.f
}


fun <T : EShape<*>> T.setCenter(point: EPoint) = setCenter(point.x, point.y)
fun <T : EShape<*>> T.setCenter(
    x: Number,
    y: Number
): T = apply {
    _setBounds(
        top = y.f - height / 2,
        left = x.f - width / 2,
        bottom = y.f + height / 2,
        right = x.f + width / 2
    )
}

fun <T : EShape<*>> T.setSides(
    left: Number = this.left,
    top: Number = this.top,
    right: Number = this.right,
    bottom: Number = this.bottom
): T = apply {
    _setBounds(
        top = top.f,
        bottom = bottom.f,
        left = left.f,
        right = right.f
    )
}

//////
fun <T : EShape<*>> T.selfOffset(x: Number, y: Number): T =
    apply { offset(x, y, this) }

fun <T : EShape<*>> T.selfOffset(p: Tuple2): T =
    apply { offset(p.v1, p.v2, this) }

fun <T : EShape<*>> T.selfInset(margin: Number): T =
    apply { inset(margin, margin, this) }

fun <T : EShape<*>> T.selfInset(p: EPoint): T =
    apply { inset(p.x, p.y, this) }

fun <T : EShape<*>> T.selfInset(x: Number = 0, y: Number = 0): T =
    apply { inset(x, y, this) }

fun <T : EShape<*>> T.selfExpand(margin: Number): T =
    apply { expand(margin, margin, this) }

fun <T : EShape<*>> T.selfExpand(p: EPoint): T =
    apply { expand(p.x, p.y, this) }

fun <T : EShape<*>> T.selfExpand(x: Number, y: Number): T =
    apply { inset(-x.f, -y.f, this) }

fun <T : EShape<*>> T.selfPadding(padding: EOffset): T =
    apply { padding(padding, this) }

fun <T : EShape<*>> T.selfPadding(
    top: Number = 0,
    bottom: Number = 0,
    left: Number = 0,
    right: Number = 0
): T = apply {
    padding(
        top = top,
        bottom = bottom,
        left = left,
        right = right,
        target = this
    )
}

fun <T : EShape<*>> T.selfExpand(padding: EOffset): T =
    apply { expand(padding, this) }

fun <T : EShape<*>> T.selfScale(factor: Number, anchor: EPoint): T =
    apply { scale(factor, anchor, this) }

fun <T : EShape<*>> T.selfScale(
    factor: Number,
    anchorX: Number,
    anchorY: Number
) =
    apply { scale(factor, anchorX, anchorY, this) }

fun <T : EShape<*>> T.selfScaleRelative(
    factor: Number,
    point: EPoint
) =

    scaleRelative(factor, point, this)

fun <T : EShape<*>> T.selfScaleRelative(
    factor: Number,
    pointX: Number,
    pointY: Number
) =
    scaleRelative(scaleFactor = factor, pointX = pointX, pointY = pointY, target = this)

fun <T : EShape<*>> T.selfMap(
    from: EShape<*>,
    to: EShape<*>
): T =
    apply { map(from, to, this) }

fun <T : EShape<*>> T.selfMap(
    fromX: Number,
    fromY: Number,
    fromWidth: Number,
    fromHeight: Number,
    toX: Number,
    toY: Number,
    toWidth: Number,
    toHeight: Number
): T =
    apply {
        map(
            fromX = fromX,
            fromY = fromY,
            fromWidth = fromWidth,
            fromHeight = fromHeight,
            toX = toX,
            toY = toY,
            toWidth = toWidth,
            toHeight = toHeight,
            target = this
        )
    }