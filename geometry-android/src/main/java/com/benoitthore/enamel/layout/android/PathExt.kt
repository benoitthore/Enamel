package com.benoitthore.enamel.layout.android

import android.graphics.Path
import android.graphics.RectF
import android.os.Build
import com.benoitthore.enamel.geometry.primitives.EAngle
import com.benoitthore.enamel.geometry.primitives.EPoint
import com.benoitthore.enamel.geometry.svg.SVGContext

val EAngle.Direction.pathDirection
    get() :Path.Direction = when (this) {
        EAngle.Direction.CW -> Path.Direction.CW
        EAngle.Direction.CCW -> Path.Direction.CCW
    }


fun Path.createContext() =
    PathSVGContext(this)

class PathSVGContext(val path: Path = Path()) : SVGContext {
    override fun reset() {
        path.reset()
    }

    override fun close() {
        path.close()
    }

    override fun move(toX: Number, toY: Number) {
        path.moveTo(toX.toFloat(), toY.toFloat())
    }

    override fun line(toX: Number, toY: Number) {
        path.lineTo(toX.toFloat(), toY.toFloat())
    }

    override fun rect(
        left: Number,
        top: Number,
        right: Number,
        bottom: Number,
        direction: EAngle.Direction
    ) {
        path.addRect(
            left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(),
            direction.pathDirection
        )
    }

    override fun oval(
        left: Number,
        top: Number,
        right: Number,
        bottom: Number,
        direction: EAngle.Direction
    ) {
        val left = left.toFloat()
        val top = top.toFloat()
        val right = right.toFloat()
        val bottom = bottom.toFloat()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            path.addOval(left, top, right, bottom, direction.pathDirection)
        } else {
            path.addOval(RectF(left, top, right, bottom), direction.pathDirection)
        }

    }

    override fun arc(
        center: EPoint,
        radius: Number,
        startAngle: EAngle,
        endAngle: EAngle,
        direction: EAngle.Direction
    ) {
        TODO("Not yet implemented")
    }

    override fun curve(toX: Number, toY: Number, controlX: Number, controlY: Number) {
        TODO("Not yet implemented")
    }

    override fun curve(
        toX: Number,
        toY: Number,
        control1X: Number,
        control1Y: Number,
        control2X: Number,
        control2Y: Number
    ) {
        TODO("Not yet implemented")
    }

}
