package com.benoitthore.enamel.geometry.functions

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.size.ESize


fun <T, I, M> T.setBounds(
    left: Number = this.left,
    top: Number = this.top,
    right: Number = this.right,
    bottom: Number = this.bottom
) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =
    apply {
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
fun <T, I, M> T.setBounds(other: EShape<*, *>) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =
    apply {
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
fun <T, I, M> T.setOriginSize(
    origin: EPoint? = null,
    size: ESize? = null
) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =
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
fun <T, I, M> T.setOriginSize(
    x: Number = this.originX, y: Number = this.originY,
    width: Number = this.width, height: Number = this.height
) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> = apply {
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
fun <T, I, M> T.setOrigin(
    x: Number = this.originX, y: Number = this.originY
) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> = apply {
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
fun <T, I, M> T.setOrigin(origin: EPoint) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =
    setOrigin(origin.x, origin.y)

/**
 * Set the shape's bounds
 * @return the shape that the function was called on
 */
fun <T, I, M> T.setBounds(
    x: Number = this.originX,
    y: Number = this.originY,
    size: ESize? = null
) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =
    setOriginSize(x, y, size?.width ?: width, size?.height ?: height)

/**
 * Set the shape's size
 * Set the shape's size to the specified value, keeping its center at the same location
 * @return the shape that the function was called on
 */
fun <T, I, M> T.setSize(size: ESize) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =
    setSize(size.width, size.height)

/**
 * Set the shape's size to the specified value, keeping its center at the same location
 * @return the shape that the function was called on
 */
fun <T, I, M> T.setSize(
    width: Number = this.width,
    height: Number = this.height
) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> = apply {
    this.width = width.f
    this.height = height.f
}


/**
 * Move the shape so its center is that the specified location
 * size isn't changed
 *
 * @return the shape that the function was called on
 */
fun <T, I, M> T.setCenter(point: EPoint) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =
    setCenter(point.x, point.y)

/**
 * Move the shape so its center is that the specified location
 * size isn't changed
 *
 * @return the shape that the function was called on
 */
fun <T, I, M> T.setCenter(
    x: Number,
    y: Number
) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =
    apply {
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
fun <T, I, M> T.setTopLeft(x: Number, y: Number) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =
    apply {
        val x = x.f
        val y = y.f
        _setBounds(
            left = x,
            top = y,
            bottom = y + height,
            right = x + width
        )
    }

fun <T, I, M> T.setTopLeft(point: EPoint) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =
    setTopLeft(point.x, point.y)

fun <T, I, M> T.setTopRight(point: EPoint) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> = setTopRight(point.x, point.y)
fun <T, I, M> T.setTopRight(x: Number, y: Number) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =
    apply {
        val x = x.f
        val y = y.f
        _setBounds(
            top = y,
            right = x,
            bottom = y + height,
            left = x - width
        )
    }

fun <T, I, M> T.setBottomRight(point: EPoint) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =
    setBottomRight(point.x, point.y)

fun <T, I, M> T.setBottomRight(x: Number, y: Number) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =
    apply {
        val x = x.f
        val y = y.f
        _setBounds(
            right = x,
            bottom = y,
            top = y - height,
            left = x - width
        )
    }

fun <T, I, M> T.setBottomLeft(point: EPoint) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =
    setBottomLeft(point.x, point.y)

fun <T, I, M> T.setBottomLeft(x: Number, y: Number) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =
    apply {
        val x = x.f
        val y = y.f
        _setBounds(
            left = x,
            bottom = y,
            top = y - height,
            right = x + width
        )
    }