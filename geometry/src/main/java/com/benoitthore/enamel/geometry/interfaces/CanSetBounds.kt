package com.benoitthore.enamel.geometry.interfaces

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.geometry.figures.ESize
import com.benoitthore.enamel.geometry.primitives.EOffset
import com.benoitthore.enamel.geometry.primitives.EPoint
import com.benoitthore.enamel.geometry.primitives.Tuple2
import com.benoitthore.enamel.geometry.interfaces.*

interface CanSetBounds : HasBounds {
    override var left: Float
    override var top: Float
    override var right: Float
    override var bottom: Float

    override var x: Float
        get() = super.x
        set(value) {
            val xOff = value - x
            left -= xOff
            right -= xOff
        }
    override var y: Float
        get() = super.y
        set(value) {
            val yOff = value - y
            top -= yOff
            bottom -= yOff
        }
    override var width: Float
        get() = super.width
        set(value) {
            right += value - width
        }
    override var height: Float
        get() = super.height
        set(value) {
            bottom += value - height
        }

}

fun <T : CanSetBounds> T.set(other: HasBounds): T = apply {
    this.left = other.left
    this.top = other.top
    this.right = other.right
    this.bottom = other.bottom
}


fun <T : CanSetBounds> T.set(other: ERect): T = set(other.origin, other.size)

fun <T : CanSetBounds> T.set(
    origin: EPoint?,
    size: ESize?
): T =
    set(origin?.x ?: x, origin?.y ?: y, size?.width ?: width, size?.height ?: height)

fun <T : CanSetBounds> T.set(
    origin: EPoint?,
    width: Number = this.width,
    height: Number = this.height
): T =
    set(origin?.x ?: 0, origin?.y ?: 0, width, height)

fun <T : CanSetBounds> T.set(
    x: Number = this.x,
    y: Number = this.y,
    size: ESize? = null
): T =
    set(x, y, size?.width ?: width, size?.height ?: height)

fun <T : CanSetBounds> T.set(
    x: Number = this.x,
    y: Number = this.y,
    width: Number = this.width,
    height: Number = this.height
): T = apply {
    this.x = x.f
    this.y = y.f
    this.width = width.f
    this.height = height.f
}

fun <T : CanSetBounds> T.setCenter(point: EPoint) = setCenter(point.x, point.y)
fun <T : CanSetBounds> T.setCenter(x: Number, y: Number) = apply {
    this.x = x.f - width / 2
    this.y = y.f - height / 2
}

fun <T : CanSetBounds> T.setSides(
    left: Number = this.left,
    top: Number = this.top,
    right: Number = this.right,
    bottom: Number = this.bottom
): T {
    this.top = top.f
    this.bottom = bottom.f
    this.left = left.f
    this.right = right.f
    return this
}

//////
fun <T : CanSetBounds> T.selfOffset(x: Number = 0, y: Number = 0): T = apply { offset(x, y, this) }

fun <T : CanSetBounds> T.selfOffset(p: Tuple2): T = apply { offset(p.v1, p.v2, this) }

fun <T : CanSetBounds> T.selfInset(margin: Number) = inset(margin, margin, this)
fun <T : CanSetBounds> T.selfInset(p: EPoint) = inset(p.x, p.y, this)
fun <T : CanSetBounds> T.selfInset(x: Number = 0, y: Number = 0) = inset(x, y, this)

fun <T : CanSetBounds> T.selfExpand(margin: Number) = expand(margin, margin, this)
fun <T : CanSetBounds> T.selfExpand(p: EPoint) = expand(p.x, p.y, this)
fun <T : CanSetBounds> T.selfExpand(x: Number = 0f, y: Number = 0f) = inset(-x.f, -y.f, this)

fun <T : CanSetBounds> T.selfPadding(padding: EOffset) = padding(padding, this)
fun <T : CanSetBounds> T.selfPadding(
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

fun <T : CanSetBounds> T.selfExpand(padding: EOffset) = expand(padding, this)

fun <T : CanSetBounds> T.selfScaleAnchor(factor: Number, anchor: EPoint) =
    scaleAnchor(factor, anchor, this)

fun <T : CanSetBounds> T.selfScaleAnchor(
    factor: Number,
    anchorX: Number,
    anchorY: Number
) = scaleAnchor(factor, anchorX, anchorY, this)

fun <T : CanSetBounds> T.selfScaleRelative(factor: Number, point: EPoint) =
    scaleRelative(factor, point, this)

fun <T : CanSetBounds> T.selfScaleRelative(factor: Number, pointX: Number, pointY: Number) =
    scaleRelative(factor = factor, pointX = pointX, pointY = pointY, target = this)
