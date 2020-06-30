package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.core.math.d
import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import kotlin.math.max
import kotlin.math.min

interface RectBuilder : SizeBuilder, PointBuilder, BaseBuilder {

    fun Rect(x: Number = 0f, y: Number = 0f, width: Number = 0f, height: Number = 0f) : ERect =
        RectMutable(x, y, width, height)

    //

    fun Rect(origin: EPoint = Point(), size: ESize = Size()): ERect =
        RectMutable(origin.toMutable(), size.toMutable())

    fun RectMutable(origin: EPoint = PointMutable(), size: ESize = SizeMutable()): ERect =
        RectMutable(origin.x, origin.y, size.width, size.height)

    fun Rect(size: ESize = Size()): ERect = RectMutable(size)

    fun RectMutable(size: ESize = SizeMutable()): ERect =
        RectMutable(width = size.width, height = size.height)

    //

    fun RectMutable(other: ERect): ERect = RectMutable(other.origin, other.size)
    fun Rect(other: ERect): ERect = RectMutable(other)

    //
    //TODO Add non mutable function
    fun RectMutableCenter(
        center: EPoint,
        size: ESize,
        target: ERect = RectMutable()
    ) = RectMutableCenter(center.x, center.y, size.width, size.height, target)

    //
    //TODO Add non mutable function
    fun RectMutableCenter(
        center: EPoint,
        width: Number,
        height: Number,
        target: ERect = RectMutable()
    ) = RectMutableCenter(center.x, center.y, width, height, target)

    //
    //TODO Add non mutable function
    fun RectMutableCenter(
        x: Number = 0f, y: Number = 0f,
        width: Number, height: Number,
        target: ERect = RectMutable()
    ): ERect {

        val width = width.f
        val height = height.f
        val x = x.f - width / 2
        val y = y.f - height / 2

        return target.setOriginSize(x = x, y = y, width = width, height = height)
    }

    //
    //TODO Add non mutable function
    fun RectMutableCorners(
        corner1: EPoint,
        corner2: EPoint,
        target: ERect = RectMutable()
    ) = RectMutableCorners(corner1.x, corner1.y, corner2.x, corner2.y, target)

    //
    //TODO Add non mutable function
    fun RectMutableCorners(
        corner1X: Number = 0,
        corner1Y: Number = 0,
        corner2X: Number = 0,
        corner2Y: Number = 0,
        target: ERect = RectMutable()
    ): ERect {
        return RectMutableSides(
            top = min(corner1Y.d, corner2Y.d),
            bottom = max(corner1Y.d, corner2Y.d),
            left = min(corner1X.d, corner2X.d),
            right = max(corner1X.d, corner2X.d),
            target = target
        )
    }

    //
    //TODO Add non mutable function
    fun RectMutableSides(
        left: Number,
        top: Number,
        right: Number,
        bottom: Number,
        target: ERect = RectMutable()
    ): ERect = target.apply {
        setBounds(
            top = top.f,
            left = left.f,
            right = right.f,
            bottom = bottom.f
        )
    }

    //
    //TODO Add non mutable function
    fun RectMutableAnchorPos(
        anchor: EPoint,
        position: EPoint,
        size: ESize,
        target: ERect = RectMutable()
    ) = target.setBounds(
        x = position.x - size.width * anchor.x,
        y = position.y - size.height * anchor.y,
        size = size
    )

    val Rect get() = _Rect

    object _Rect {
        val zero = E.Rect()
    }

    val RectMutable get() = _RectMutable

    object _RectMutable {
        val zero = E.RectMutable()
    }

}

