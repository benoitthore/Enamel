package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.core.math.d
import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.geometry.figures.ESize
import com.benoitthore.enamel.geometry.figures.ESizeMutable
import com.benoitthore.enamel.geometry.primitives.EPoint
import kotlin.math.max
import kotlin.math.min

interface RectBuilder : SizeBuilder, PointBuilder, BaseBuilder {

    fun Rect(x: Number = 0f, y: Number = 0f, width: Number = 0f, height: Number = 0f) =
        mRect(x, y, width, height)

    //

    fun Rect(origin: EPoint = Point(), size: ESize = Size()): ERect =
        mRect(origin.toMutable(), size.toMutable())

    fun mRect(origin: EPoint = mPoint(), size: ESize = mSize()): ERectMutable =
        mRect(origin.x, origin.y, size.width, size.height)

    //

    fun mRect(other: ERect): ERectMutable = mRect(other.origin, other.size)
    fun Rect(other: ERect): ERect = mRect(other)

    //
    //TODO Add non mutable function
    fun mrectCenter(
        center: EPoint,
        size: ESize,
        target: ERectMutable = mRect()
    ) = mrectCenter(center.x, center.y, size.width, size.height, target)

    //
    //TODO Add non mutable function
    fun mrectCenter(
        center: EPoint,
        width: Number,
        height: Number,
        target: ERectMutable = mRect()
    ) = mrectCenter(center.x, center.y, width, height, target)

    //
    //TODO Add non mutable function
    fun mrectCenter(
        x: Number = 0f,y: Number = 0f,
        width: Number, height: Number,
        target: ERectMutable = mRect()
    ): ERectMutable {

        val width = width.f
        val height = height.f
        val x = x.f - width / 2
        val y = y.f - height / 2

        return target.set(x = x, y = y, width = width, height = height)
    }

    //
    //TODO Add non mutable function
    fun mRectCorners(
        corner1: EPoint,
        corner2: EPoint,
        target: ERectMutable = mRect()
    ) = mRectCorners(corner1.x, corner1.y, corner2.x, corner2.y, target)

    //
    //TODO Add non mutable function
    fun mRectCorners(
        corner1X: Number = 0,
        corner1Y: Number = 0,
        corner2X: Number = 0,
        corner2Y: Number = 0,
        target: ERectMutable = mRect()
    ): ERectMutable {
        return mrectSides(
            top = min(corner1Y.d, corner2Y.d),
            bottom = max(corner1Y.d, corner2Y.d),
            left = min(corner1X.d, corner2X.d),
            right = max(corner1X.d, corner2X.d),
            target = target
        )
    }

    //
    //TODO Add non mutable function
    fun mrectSides(
        left: Number,
        top: Number,
        right: Number,
        bottom: Number,
        target: ERectMutable = mRect()
    ): ERectMutable {
        target.top = top.f
        target.left = left.f
        target.right = right.f
        target.bottom = bottom.f
        return target
    }

    //
    //TODO Add non mutable function
    fun mrectAnchorPos(
        anchor: EPoint,
        position: EPoint,
        size: ESizeMutable,
        target: ERectMutable = mRect()
    ) = target.set(
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
        val zero = E.mRect()
    }

}

