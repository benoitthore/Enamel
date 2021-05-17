package com.benoitthore.enamel.layout.android

import android.graphics.Path
import android.graphics.PathMeasure
import com.benoitthore.enamel.geometry.builders.*
import com.benoitthore.enamel.geometry.primitives.angle.EAngle
import com.benoitthore.enamel.geometry.primitives.angle.EAngleMutable
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable
import kotlin.math.atan2

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

        return data.set(posBuffer[0], posBuffer[1],
            atan2(tanBuffer[1], tanBuffer[0])
        )
    }

    private fun reset() {
        pathMeasure.setPath(path, false)
    }

    class Data {
        private val _position: EPointMutable = MutablePoint()
        val position: EPoint get() = _position

        private val _angle: EAngleMutable = MutableAngle()
        val angle: EAngle get() = _angle

        internal fun set(x: Float, y: Float, angleRadian: Float) = apply {
            _position.set(x, y)
            _angle.set(angleRadian,
                EAngle.AngleType.RADIAN
            )
        }
    }
}