package com.benoitthore.enamel.layout.android

import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.RectF
import android.os.Build
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.primitives.angle.EAngle
import com.benoitthore.enamel.geometry.primitives.angle.EAngleMutable
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable
import com.benoitthore.enamel.geometry.svg.ESVG
import com.benoitthore.enamel.geometry.svg.SVGContext
import kotlin.math.atan2

val EAngle.Direction.pathDirection
    get() :Path.Direction = when (this) {
        EAngle.Direction.CW -> Path.Direction.CW
        EAngle.Direction.CCW -> Path.Direction.CCW
    }


fun Path.createSVGContext(): PathSVGContext =
    PathSVGContextImpl(this)

fun ESVG.createPathSVGContext(): PathSVGContext = PathSVGContextImpl()
    .also {
        addTo(it)
    }

private class PathSVGContextImpl(override val path: Path = Path()) : PathSVGContext {
    override val pathMeasure: EPathMeasure = EPathMeasure(path)
}

interface PathSVGContext : SVGContext {

    val path: Path
    val pathMeasure: EPathMeasure

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

class EPathMeasure(private val path: Path) {
    private val pathMeasure = PathMeasure()
    private val posBuffer = floatArrayOf(0f, 0f)
    private val tanBuffer = floatArrayOf(0f, 0f)
    private val data = Data()

    fun getTotalDistance(): Float {
        reset()
        var totalLength = pathMeasure.length

        while (pathMeasure.nextContour()) {
            totalLength += pathMeasure.length
        }
        reset()
        return totalLength
    }

    fun getAbsolute(distance: Number) = get(distance.toFloat() / getTotalDistance())

    operator fun get(distance: Number): Data {
        val totalDistance = getTotalDistance()
        val desiredDistance = distance.toFloat() * totalDistance

        var contourStartAt = 0f
        var hasNext = true
        while (hasNext && desiredDistance > contourStartAt + pathMeasure.length) {
            contourStartAt += pathMeasure.length
            hasNext = pathMeasure.nextContour()
        }

        pathMeasure.getPosTan(
            desiredDistance - contourStartAt,
            posBuffer,
            tanBuffer
        )

        return data.set(posBuffer[0], posBuffer[1], atan2(tanBuffer[1], tanBuffer[0]))
    }

    private fun reset() {
        pathMeasure.setPath(path, false)
    }

    class Data {
        private val _position: EPointMutable = E.PointMutable()
        val position: EPoint get() = _position

        private val _angle: EAngleMutable = E.AngleMutable()
        val angle: EAngle get() = _angle

        internal fun set(x: Float, y: Float, angleRadian: Float) = apply {
            _position.set(x, y)
            _angle.set(angleRadian, EAngle.AngleType.RADIAN)
        }
    }
}