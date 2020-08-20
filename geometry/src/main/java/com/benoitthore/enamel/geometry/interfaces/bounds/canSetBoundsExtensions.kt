package com.benoitthore.enamel.geometry.interfaces.bounds

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.offset.EOffset
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.Tuple2

// TODO once refactoring is done:
// TODO Rename to setBounds
// TODO Rename interface method to _setBounds
fun <T : EShape<*>> T.setBounds(other: EShape<*>): T = apply {
    setBounds(
        left = other.left,
        top = other.top,
        right = other.right,
        bottom = other.bottom
    )
}


fun <T : EShape<*>> T.setBounds(other: ERect): T =
    setOriginSize(other.origin, other.size)

fun <T : EShape<*>> T.setOriginSize(
    origin: EPoint?,
    size: ESize?
): T =
    setOriginSize(
        origin?.x ?: originX,
        origin?.y ?: originY,
        size?.width ?: width,
        size?.height ?: height
    )

fun <T : EShape<*>> T.setTopLeft(point: EPoint): T = apply {
    setBounds(
        left = point.x,
        top = point.y,
        bottom = point.y + height,
        right = point.x + width
    )
}

fun <T : EShape<*>> T.setTopRight(point: EPoint): T = apply {
    setBounds(
        top = point.y,
        right = point.x,
        bottom = point.y + height,
        left = point.x - width
    )
}

fun <T : EShape<*>> T.setBottomRight(point: EPoint): T = apply {
    setBounds(
        right = point.x,
        bottom = point.y,
        top = point.y - height,
        left = point.x - width
    )
}

fun <T : EShape<*>> T.setBottomLeft(point: EPoint): T = apply {
    setBounds(
        left = point.x,
        bottom = point.y,
        top = point.y - height,
        right = point.x + width
    )
}

fun <T : EShape<*>> T.setOriginSize(
    x: Number = this.originX, y: Number = this.originY,
    width: Number = this.width, height: Number = this.height
) = apply {
    setBounds(
        left = x,
        top = y,
        right = x.f + width.f,
        bottom = y.f + height.f
    )
}

fun <T : EShape<*>> T.setOrigin(
    originX: Number = this.originX, originY: Number = this.originY
) = setBounds(
    left = originX,
    top = originY,
    right = originX.f + width.f,
    bottom = originY.f + height.f
)

fun <T : EShape<*>> T.setOrigin(origin: EPoint) =
    setOrigin(origin.x, origin.y)

fun <T : EShape<*>> T.setBounds(
    x: Number = this.originX,
    y: Number = this.originY,
    size: ESize? = null
): T =
    setOriginSize(x, y, size?.width ?: width, size?.height ?: height)


fun <T : EShape<*>> T.setSize(size: ESize): T = apply {
    this.width = size.width.f
    this.height = size.height.f
}


fun <T : EShape<*>> T.setSize(
    width: Number = this.width,
    height: Number = this.height
): T = apply {
    this.width = width.f
    this.height = height.f
}

fun <T : EShape<*>> T.setCenter(point: EPoint) =
    setCenter(point.x, point.y)

fun <T : EShape<*>> T.setCenter(
    x: Number,
    y: Number
): T = apply {
    setBounds(
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
    setBounds(
        top = top.f,
        bottom = bottom.f,
        left = left.f,
        right = right.f
    )
}

//////
fun <T : EShape<T>> T.selfOffset(x: Number, y: Number): T =
    apply { offset(x, y, this) }

fun <T : EShape<T>> T.selfOffset(p: Tuple2): T =
    apply { offset(p.v1, p.v2, this) }

fun <T : EShape<T>> T.selfInset(margin: Number): T =
    apply { inset(margin, margin, this) }

fun <T : EShape<T>> T.selfInset(p: EPoint): T =
    apply { inset(p.x, p.y, this) }

fun <T : EShape<T>> T.selfInset(x: Number = 0, y: Number = 0): T =
    apply { inset(x, y, this) }

fun <T : EShape<T>> T.selfExpand(margin: Number): T =
    apply { expand(margin, margin, this) }

fun <T : EShape<T>> T.selfExpand(p: EPoint): T =
    apply { expand(p.x, p.y, this) }

fun <T : EShape<T>> T.selfExpand(x: Number, y: Number): T =
    apply { inset(-x.f, -y.f, this) }

fun <T : EShape<T>> T.selfPadding(padding: EOffset): T =
    apply { padding(padding, this) }

fun <T : EShape<T>> T.selfPadding(
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

fun <T : EShape<T>> T.selfExpand(padding: EOffset): T =
    apply { expand(padding, this) }

fun <T : EShape<T>> T.selfScaleAnchor(factor: Number, anchor: EPoint): T =
    apply { scaleAnchor(factor, anchor, this) }

fun <T : EShape<T>> T.selfScaleAnchor(
    factor: Number,
    anchorX: Number,
    anchorY: Number
) =
    apply { scaleAnchor(factor, anchorX, anchorY, this) }

fun <T : EShape<T>> T.selfScaleRelative(
    factor: Number,
    point: EPoint
) =

    scaleRelative(factor, point, this)

fun <T : EShape<T>> T.selfScaleRelative(
    factor: Number,
    pointX: Number,
    pointY: Number
) =
    scaleRelative(scaleFactor = factor, pointX = pointX, pointY = pointY, target = this)

fun <T : EShape<T>> T.selfMap(
    from: EShape<*>,
    to: EShape<*>
): T =
    apply { map(from, to, this) }

fun <T : EShape<T>> T.selfMap(
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