package com.benoitthore.enamel.geometry.functions

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.offset.EOffset
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.Tuple2
import com.benoitthore.enamel.geometry.primitives.point.point

fun <T : EShape<*>> T.setBounds(
    left: Number = this.left,
    top: Number = this.top,
    right: Number = this.right,
    bottom: Number = this.bottom
): T = apply {
    _setBounds(
        left = left,
        top = top,
        right = right,
        bottom = bottom
    )
}

/**
 * Set the shape's bounds to be equal to the other shapes bounds
 * @return the shape that the function was called on
 */
fun <T : EShape<*>> T.setBounds(other: EShape<*>): T = apply {
    _setBounds(
        left = other.left,
        top = other.top,
        right = other.right,
        bottom = other.bottom
    )
}

/**
 * Set the shape's origin and size
 *
 * If no size is specified, size is unchanged
 * If not origin is specified, the origin will remain the same after resize
 *
 * @return the shape that the function was called on
 */
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
 * Set the shape's origin and size
 *
 * If no size is specified, size is unchanged
 * If not origin is specified, the origin will remain the same after resize
 *
 * @return the shape that the function was called on
 */
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


/**
 * Set the shape's origin
 * @return the shape that the function was called on
 */
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

/**
 * Set the shape's origin
 * @return the shape that the function was called on
 */
fun <T : EShape<*>> T.setOrigin(origin: EPoint) = setOrigin(origin.x, origin.y)

/**
 * Set the shape's bounds
 * @return the shape that the function was called on
 */
fun <T : EShape<*>> T.setBounds(
    x: Number = this.originX,
    y: Number = this.originY,
    size: ESize? = null
): T =
    setOriginSize(x, y, size?.width ?: width, size?.height ?: height)

/**
 * Set the shape's size
 * Set the shape's size to the specified value, keeping its center at the same location
 * @return the shape that the function was called on
 */
fun <T : EShape<*>> T.setSize(size: ESize): T = setSize(size.width, size.height)

/**
 * Set the shape's size to the specified value, keeping its center at the same location
 * @return the shape that the function was called on
 */
fun <T : EShape<*>> T.setSize(
    width: Number = this.width,
    height: Number = this.height
): T = apply {
    this.width = width.f
    this.height = height.f
}


/**
 * Move the shape so its center is that the specified location
 * size isn't changed
 *
 * @return the shape that the function was called on
 */
fun <T : EShape<*>> T.setCenter(point: EPoint) = setCenter(point.x, point.y)

/**
 * Move the shape so its center is that the specified location
 * size isn't changed
 *
 * @return the shape that the function was called on
 */
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