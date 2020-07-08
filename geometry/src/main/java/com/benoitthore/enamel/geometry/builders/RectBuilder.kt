package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.core.math.d
import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.interfaces.bounds.setOriginSize
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import kotlin.math.max
import kotlin.math.min

interface RectBuilder : SizeBuilder, PointBuilder, BaseBuilder {


    fun Rect(origin: EPoint = Point(), size: ESize = Size()): ERect =
        Rect(origin.x, origin.y, size.width, size.height)


    fun Rect(size: ESize = Size()): ERect =
        Rect(width = size.width, height = size.height)

    //

    fun Rect(other: ERect): ERect = Rect(other.origin, other.size)

    //
    
    fun RectCenter(
        center: EPoint,
        size: ESize,
        target: ERect = Rect()
    ) = RectCenter(center.x, center.y, size.width, size.height, target)

    //
    
    fun RectCenter(
        center: EPoint,
        width: Number,
        height: Number,
        target: ERect = Rect()
    ) = RectCenter(center.x, center.y, width, height, target)

    //
    
    fun RectCenter(
        x: Number = 0f, y: Number = 0f,
        width: Number, height: Number,
        target: ERect = Rect()
    ): ERect {

        val width = width.f
        val height = height.f
        val x = x.f - width / 2
        val y = y.f - height / 2

        return target.setOriginSize(x = x, y = y, width = width, height = height)
    }

    //
    
    fun RectCorners(
        corner1: EPoint,
        corner2: EPoint,
        target: ERect = Rect()
    ) = RectCorners(corner1.x, corner1.y, corner2.x, corner2.y, target)

    //
    
    fun RectCorners(
        corner1X: Number = 0,
        corner1Y: Number = 0,
        corner2X: Number = 0,
        corner2Y: Number = 0,
        target: ERect = Rect()
    ): ERect {
        return RectSides(
            top = min(corner1Y.d, corner2Y.d),
            bottom = max(corner1Y.d, corner2Y.d),
            left = min(corner1X.d, corner2X.d),
            right = max(corner1X.d, corner2X.d),
            target = target
        )
    }

    //
    
    fun RectSides(
        left: Number,
        top: Number,
        right: Number,
        bottom: Number,
        target: ERect = Rect()
    ): ERect = target.apply {
        setBounds(
            top = top.f,
            left = left.f,
            right = right.f,
            bottom = bottom.f
        )
    }

    fun RectAnchorPos(
        anchor: EPoint,
        position: EPoint,
        size: ESize,
        target: ERect = Rect()
    ): ERect = TODO()
//        target.setBounds(
//        x = position.x - size.width * anchor.x,
//        y = position.y - size.height * anchor.y,
//        size = size
//    )

    val Rect get() = _Rect

    object _Rect {
        val zero = E.Rect()
    }


}

