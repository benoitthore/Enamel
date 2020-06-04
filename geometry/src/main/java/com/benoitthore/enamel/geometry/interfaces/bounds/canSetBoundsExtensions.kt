package com.benoitthore.enamel.geometry.interfaces.bounds

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.figures.rect.ERectMutable
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.offset.EOffset
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.Tuple2

fun CanSetBounds<*, *>.toRect(target: ERectMutable = E.RectMutable()): ERectMutable =
    target.set(this)

fun <T, I, M> T.setOriginSize(
    originX: Number = this.originX, originY: Number = this.originY,
    width: Number = this.width, height: Number = this.height
) where  T : CanSetBounds<I, M>, I : HasBounds<I, M>, M : CanSetBounds<I, M> = setBounds(
    left = originX,
    top = originY,
    right = originX.f + width.f,
    bottom = originY.f + height.f
)

fun <T, I, M> T.setOrigin(
    originX: Number = this.originX, originY: Number = this.originY
) where  T : CanSetBounds<I, M>, I : HasBounds<I, M>, M : CanSetBounds<I, M> = setBounds(
    left = originX,
    top = originY,
    right = originX.f + width.f,
    bottom = originY.f + height.f
)

fun <T, I, M> T.setOrigin(origin: EPoint) where  T : CanSetBounds<I, M>, I : HasBounds<I, M>, M : CanSetBounds<I, M> =
    setOrigin(origin.x, origin.y)

fun CanSetBounds<*, *>.ensureRect(target: ERectMutable = E.RectMutable()): ERect =
    if (this is ERect) this else getBounds(target)

// TODO once refactoring is done:
// TODO Rename to setBounds
// TODO Rename interface method to _setBounds
fun <T, I, M> T.set(other: HasBounds<*, *>): T
        where  T : CanSetBounds<I, M>, I : HasBounds<I, M>, M : CanSetBounds<I, M> = apply {
    setBounds(
        left = other.left,
        top = other.top,
        right = other.right,
        bottom = other.bottom
    )
}


fun <T, I, M> T.set(other: ERect): T
        where  T : CanSetBounds<I, M>, I : HasBounds<I, M>, M : CanSetBounds<I, M> =
    set(other.origin, other.size)

fun <T, I, M> T.set(
    origin: EPoint?,
    size: ESize?
): T
        where  T : CanSetBounds<I, M>, I : HasBounds<I, M>, M : CanSetBounds<I, M> =
    set(origin?.x ?: originX, origin?.y ?: originY, size?.width ?: width, size?.height ?: height)

//TOOO Find why this won't compile
//fun <T, I, M> T.set(
//    origin: EPoint?,
//    width: Number = this.width,
//    height: Number = this.height
//): T =
//    set(origin?.x ?: 0, origin?.y ?: 0, width, height)

fun <T, I, M> T.set(
    x: Number = this.originX,
    y: Number = this.originY,
    size: ESize? = null
): T where  T : CanSetBounds<I, M>, I : HasBounds<I, M>, M : CanSetBounds<I, M> =
    set(x, y, size?.width ?: width, size?.height ?: height)

fun <T, I, M> T.set(
    x: Number = this.originX,
    y: Number = this.originY,
    width: Number = this.width,
    height: Number = this.height
): T
        where  T : CanSetBounds<I, M>, I : HasBounds<I, M>, M : CanSetBounds<I, M> = apply {
    setBounds(
        left = x,
        top = y,
        bottom = y.f + height.f,
        right = x.f + width.f
    )
}

fun <T, I, M> T.setSize(size: ESize): T
        where  T : CanSetBounds<I, M>, I : HasBounds<I, M>, M : CanSetBounds<I, M> = apply {
    this.width = size.width.f
    this.height = size.height.f
}


fun <T, I, M> T.setSize(
    width: Number = this.width,
    height: Number = this.height
): T
        where  T : CanSetBounds<I, M>, I : HasBounds<I, M>, M : CanSetBounds<I, M> = apply {
    this.width = width.f
    this.height = height.f
}

fun <T, I, M> T.setCenter(point: EPoint)
        where  T : CanSetBounds<I, M>, I : HasBounds<I, M>, M : CanSetBounds<I, M> =
    setCenter(point.x, point.y)

fun <T, I, M> T.setCenter(
    x: Number,
    y: Number
): T where  T : CanSetBounds<I, M>, I : HasBounds<I, M>, M : CanSetBounds<I, M> = apply {
    setBounds(
        top = y.f - height / 2,
        left = x.f - width / 2,
        bottom = y.f + height / 2,
        right = x.f + width / 2
    )
}

fun <T, I, M> T.setSides(
    left: Number = this.left,
    top: Number = this.top,
    right: Number = this.right,
    bottom: Number = this.bottom
): T
        where  T : CanSetBounds<I, M>, I : HasBounds<I, M>, M : CanSetBounds<I, M> = apply {
    setBounds(
        top = top.f,
        bottom = bottom.f,
        left = left.f,
        right = right.f
    )
}

//////
fun <T, I, M> T.selfOffset(x: Number = 0, y: Number = 0): T
        where  T : CanSetBounds<I, M>, I : HasBounds<I, M>, M : CanSetBounds<I, M> =
    apply { offset(x, y, this) }

fun <T, I, M> T.selfOffset(p: Tuple2): T
        where  T : CanSetBounds<I, M>, I : HasBounds<I, M>, M : CanSetBounds<I, M> =
    apply { offset(p.v1, p.v2, this) }

fun <T, I, M> T.selfInset(margin: Number): T
        where  T : CanSetBounds<I, M>, I : HasBounds<I, M>, M : CanSetBounds<I, M> =
    apply { inset(margin, margin, this) }

fun <T, I, M> T.selfInset(p: EPoint): T
        where  T : CanSetBounds<I, M>, I : HasBounds<I, M>, M : CanSetBounds<I, M> =
    apply { inset(p.x, p.y, this) }

fun <T, I, M> T.selfInset(x: Number = 0, y: Number = 0): T
        where  T : CanSetBounds<I, M>, I : HasBounds<I, M>, M : CanSetBounds<I, M> =
    apply { inset(x, y, this) }

fun <T, I, M> T.selfExpand(margin: Number): T
        where  T : CanSetBounds<I, M>, I : HasBounds<I, M>, M : CanSetBounds<I, M> =
    apply { expand(margin, margin, this) }

fun <T, I, M> T.selfExpand(p: EPoint): T
        where  T : CanSetBounds<I, M>, I : HasBounds<I, M>, M : CanSetBounds<I, M> =
    apply { expand(p.x, p.y, this) }

fun <T, I, M> T.selfExpand(x: Number = 0f, y: Number = 0f): T
        where  T : CanSetBounds<I, M>, I : HasBounds<I, M>, M : CanSetBounds<I, M> =
    apply { inset(-x.f, -y.f, this) }

fun <T, I, M> T.selfPadding(padding: EOffset): T
        where  T : CanSetBounds<I, M>, I : HasBounds<I, M>, M : CanSetBounds<I, M> =
    apply { padding(padding, this) }

fun <T, I, M> T.selfPadding(
    top: Number = 0,
    bottom: Number = 0,
    left: Number = 0,
    right: Number = 0
): T
        where  T : CanSetBounds<I, M>, I : HasBounds<I, M>, M : CanSetBounds<I, M> = apply {
    padding(
        top = top,
        bottom = bottom,
        left = left,
        right = right,
        target = this
    )
}

fun <T, I, M> T.selfExpand(padding: EOffset): T
        where  T : CanSetBounds<I, M>, I : HasBounds<I, M>, M : CanSetBounds<I, M> =
    apply { expand(padding, this) }

fun <T, I, M> T.selfScaleAnchor(factor: Number, anchor: EPoint): T
        where  T : CanSetBounds<I, M>, I : HasBounds<I, M>, M : CanSetBounds<I, M> =
    apply { scaleAnchor(factor, anchor, this) }

fun <T, I, M> T.selfScaleAnchor(
    factor: Number,
    anchorX: Number,
    anchorY: Number
) where  T : CanSetBounds<I, M>, I : HasBounds<I, M>, M : CanSetBounds<I, M> =
    apply { scaleAnchor(factor, anchorX, anchorY, this) }

fun <T, I, M> T.selfScaleRelative(
    factor: Number,
    point: EPoint
) where  T : CanSetBounds<I, M>, I : HasBounds<I, M>, M : CanSetBounds<I, M> =

    scaleRelative(factor, point, this)

fun <T, I, M> T.selfScaleRelative(
    factor: Number,
    pointX: Number,
    pointY: Number
) where  T : CanSetBounds<I, M>, I : HasBounds<I, M>, M : CanSetBounds<I, M> =
    scaleRelative(scaleFactor = factor, pointX = pointX, pointY = pointY, target = this)

fun <T, I, M> T.selfMap(
    from: HasBounds<*, *>,
    to: HasBounds<*, *>
): T where  T : CanSetBounds<I, M>, I : HasBounds<I, M>, M : CanSetBounds<I, M> =
    apply { map(from, to, this) }

fun <T, I, M> T.selfMap(
    fromX: Number,
    fromY: Number,
    fromWidth: Number,
    fromHeight: Number,
    toX: Number,
    toY: Number,
    toWidth: Number,
    toHeight: Number
): T where  T : CanSetBounds<I, M>, I : HasBounds<I, M>, M : CanSetBounds<I, M> =
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