package com.benoitthore.enamel.geometry.svg

import com.benoitthore.enamel.geometry.figures.oval.EOval
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.primitives.EAngle
import com.benoitthore.enamel.geometry.primitives.EPoint


fun List<ESVG>.addTo(context: SVGContext) = forEach { it.addTo(context) }

interface ESVG {
    fun addTo(context: SVGContext)
}

interface SVGContext {
    fun reset()
    fun close()

    fun move(to: EPoint) = move(to.x, to.y)
    fun move(toX: Number, toY: Number)

    fun line(to: EPoint) = line(to.x, to.y)
    fun line(toX: Number, toY: Number)

    fun oval(oval: EOval, direction: EAngle.Direction = EAngle.Direction.CCW) =
        with(oval) { oval(left, top, right, bottom, direction) }

    fun oval(
        left: Number,
        top: Number,
        right: Number,
        bottom: Number,
        direction: EAngle.Direction = EAngle.Direction.CCW
    )

    fun rect(rect: ERect, direction: EAngle.Direction = EAngle.Direction.CCW) =
        with(rect) { rect(left, top, right, bottom, direction) }

    fun rect(
        left: Number,
        top: Number,
        right: Number,
        bottom: Number,
        direction: EAngle.Direction = EAngle.Direction.CCW
    )

    fun arc(
        center: EPoint,
        radius: Number,
        startAngle: EAngle,
        endAngle: EAngle,
        direction: EAngle.Direction = EAngle.Direction.CCW
    )

    fun curve(toX: Number, toY: Number, controlX: Number, controlY: Number)
    fun curve(
        toX: Number, toY: Number,
        control1X: Number, control1Y: Number,
        control2X: Number, control2Y: Number
    )
}

