package com.benoitthore.enamel.geometry.interfaces.bounds

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.figures.rect.ERectMutable
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.offset.EOffset
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.Tuple2

fun <T : CanSetBounds> T.setOriginSize(
    originX: Number = this.originX, originY: Number = this.originY,
    width: Number = this.width, height: Number = this.height
) = setBounds(
    left = originX,
    top = originY,
    right = originX.f + width.f,
    bottom = originY.f + height.f
)

fun CanSetBounds.ensureRect(target: ERectMutable = E.RectMutable()): ERect =
    if (this is ERect) this else getBounds(target)

fun <T : CanSetBounds> T.set(other: HasBounds): T = apply {
    setBounds(
        left = other.left,
        top = other.top,
        right = other.right,
        bottom = other.bottom
    )
}


fun <T : CanSetBounds> T.set(other: ERect): T = set(other.origin, other.size)

fun <T : CanSetBounds> T.set(
    origin: EPoint?,
    size: ESize?
): T =
    set(origin?.x ?: originX, origin?.y ?: originY, size?.width ?: width, size?.height ?: height)

//TOOO Find why this won't compile
//fun <T : CanSetBounds> T.set(
//    origin: EPoint?,
//    width: Number = this.width,
//    height: Number = this.height
//): T =
//    set(origin?.x ?: 0, origin?.y ?: 0, width, height)

fun <T : CanSetBounds> T.set(
    x: Number = this.originX,
    y: Number = this.originY,
    size: ESize? = null
): T =
    set(x, y, size?.width ?: width, size?.height ?: height)

fun <T : CanSetBounds> T.set(
    x: Number = this.originX,
    y: Number = this.originY,
    width: Number = this.width,
    height: Number = this.height
): T = apply {
    setBounds(
        left = x,
        top = y,
        bottom = y.f + height.f,
        right = x.f + width.f
    )
}

fun <T : CanSetBounds> T.setSize(size: ESize): T = apply {
    this.width = size.width.f
    this.height = size.height.f
}


fun <T : CanSetBounds> T.setSize(
    width: Number = this.width,
    height: Number = this.height
): T = apply {
    this.width = width.f
    this.height = height.f
}

fun <T : CanSetBounds> T.setCenter(point: EPoint) = setCenter(point.x, point.y)
fun <T : CanSetBounds> T.setCenter(x: Number, y: Number) = apply {
    setBounds(
        top = y.f - height / 2,
        left = x.f - width / 2,
        bottom = y.f + height / 2,
        right = x.f + width / 2
    )
}

fun <T : CanSetBounds> T.setSides(
    left: Number = this.left,
    top: Number = this.top,
    right: Number = this.right,
    bottom: Number = this.bottom
): T = apply {
    setBounds(
        top = top.f,
        bottom = bottom.f,
        left = left.f,
        right = right.f
    )
}

//////
fun <T : CanSetBounds> T.selfOffset(x: Number = 0, y: Number = 0): T = apply { offset(x, y, this) }

fun <T : CanSetBounds> T.selfOffset(p: Tuple2): T = apply { offset(p.v1, p.v2, this) }

fun <T : CanSetBounds> T.selfInset(margin: Number) = apply { inset(margin, margin, this) }
fun <T : CanSetBounds> T.selfInset(p: EPoint) = apply { inset(p.x, p.y, this) }
fun <T : CanSetBounds> T.selfInset(x: Number = 0, y: Number = 0) = apply { inset(x, y, this) }

fun <T : CanSetBounds> T.selfExpand(margin: Number) = apply { expand(margin, margin, this) }
fun <T : CanSetBounds> T.selfExpand(p: EPoint) = apply { expand(p.x, p.y, this) }
fun <T : CanSetBounds> T.selfExpand(x: Number = 0f, y: Number = 0f) =
    apply { inset(-x.f, -y.f, this) }

fun <T : CanSetBounds> T.selfPadding(padding: EOffset) = apply { padding(padding, this) }
fun <T : CanSetBounds> T.selfPadding(
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

fun <T : CanSetBounds> T.selfExpand(padding: EOffset) = expand(padding, this)

fun <T : CanSetBounds> T.selfScaleAnchor(factor: Number, anchor: EPoint) =
    scaleAnchor(factor, anchor, this)

fun <T : CanSetBounds> T.selfScaleAnchor(
    factor: Number,
    anchorX: Number,
    anchorY: Number
) = apply { scaleAnchor(factor, anchorX, anchorY, this) }

fun <T : CanSetBounds> T.selfScaleRelative(factor: Number, point: EPoint) =
    scaleRelative(factor, point, this)

fun <T : CanSetBounds> T.selfScaleRelative(factor: Number, pointX: Number, pointY: Number) =
    scaleRelative(scaleFactor = factor, pointX = pointX, pointY = pointY, target = this)

fun <T : CanSetBounds> T.selfMap(from: HasBounds, to: HasBounds) = apply { map(from, to, this) }
fun <T : CanSetBounds> T.selfMap(
    fromX: Number,
    fromY: Number,
    fromWidth: Number,
    fromHeight: Number,
    toX: Number,
    toY: Number,
    toWidth: Number,
    toHeight: Number
) = apply {
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