package com.benoitthore.enamel.android

import android.content.Context
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.graphics.*
import android.graphics.Color.*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.benoitthore.enamel.android.demo.CanvasTestView
import com.benoitthore.enamel.android.demo.canvasView
import com.benoitthore.enamel.android.extract.*
import com.benoitthore.enamel.core.math.Scale
import com.benoitthore.enamel.core.math.*
import com.benoitthore.enamel.core.time.ETimer
import com.benoitthore.enamel.geometry.AllocationTracker
import com.benoitthore.enamel.geometry.alignement.EAlignment.*
import com.benoitthore.enamel.geometry.figures.ECircle
import com.benoitthore.enamel.geometry.figures.ELine
import com.benoitthore.enamel.geometry.figures.line
import com.benoitthore.enamel.geometry.innerCircle
import com.benoitthore.enamel.geometry.innerRect
import com.benoitthore.enamel.geometry.layout.dsl.*
import com.benoitthore.enamel.geometry.primitives.degrees
import com.benoitthore.enamel.geometry.primitives.point
import com.benoitthore.enamel.geometry.primitives.radians
import com.benoitthore.enamel.geometry.primitives.rotations
import com.benoitthore.enamel.geometry.toCircle
import com.benoitthore.enamel.layout.android.dp
import com.benoitthore.enamel.layout.android.eViewGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import splitties.views.backgroundColor
import kotlin.contracts.ExperimentalContracts


operator fun OpenSimplexNoise.invoke(x: Number) = eval(x.d)
operator fun OpenSimplexNoise.invoke(x: Number, y: Number) = eval(x.d, y.d)
operator fun OpenSimplexNoise.invoke(x: Number, y: Number, z: Number) = eval(x.d, y.d, z.d)

val Context.isLandscape get() = resources.configuration.orientation == ORIENTATION_LANDSCAPE

fun Context.fancyView(): CanvasTestView {
    val pool = GeometryPool(100)

    val paint = Paint()
    paint.isAntiAlias = true

    val timer = ETimer()
    val targetFrameDuration = 16f
    val angleIncrease = 0.5 * 1 / 60f
    val noiseOffsetIncrease = 1 / 60f

    val angle = 0.degrees()


    val noise = OpenSimplexNoise(System.currentTimeMillis())

    var offset = 0f


    var shader: Shader? = null
    return canvasView { canvas ->


        canvas.drawColor(DKGRAY)

        val deltaTime = if (timer.isStarted) {
            timer.elapsed / targetFrameDuration
        } else 1f
        timer.start()


        offset += noiseOffsetIncrease * deltaTime

        pool.apply {

            paint.color = LTGRAY
            paint.style = Paint.Style.STROKE
            frame.innerCircle(circlePool()).draw(paint)
                .selfInset(width * 0.005)
                .also { circle ->
                    if (shader == null)
                        circle.innerRect(rectPool())
                            .selfScaleAnchor(0.75, 0.5, 0.5).let {
                                shader = (it.topLeft() line it.bottomRight())
                                    .toLinearGradient(
                                        (0 until 4).map { colorHSL(it / 4f) }
                                    )
                            }
                }
                .let { circle ->

                    circle.toListOfPoint(
                        pointPool.list(50),
                        angle.selfOffset((deltaTime * angleIncrease).radians(anglePool())),
                        distanceList = (0 until 50).map {
                            circle.radius *
                                    Scale.map((noise(offset + it / 10f)), -1, 1, 0.3, 1)
                        }
                    )
                }
                .map { it.toCircle(width * 0.01, circlePool()) }
                .forEachIndexed { i, it: ECircle ->
                    paint.style = Paint.Style.FILL
                    paint.color = WHITE

                    paint.shader = shader
                    it.draw(paint)

                    linePool().let { line ->
                        paint.color = CYAN
                        paint.strokeWidth = width * 0.0025f
                        line.set(it.center, frame.center(pointPool())).draw(paint)
                    }
                    paint.shader = null
                }


            invalidate()
        }

    }
}


class MainActivity : AppCompatActivity() {

    @ExperimentalContracts
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AllocationTracker.debugAllocations = true



        val paintRect = Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = 2.dp
            color = RED
        }

        val paintCircle = Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = 2.dp
            color = BLUE
        }

        setContentView(
            canvasView { canvas ->

                canvas.drawColor(LTGRAY)

                val margin = 64.dp
                canvas.drawRect(
                    margin,
                    margin,
                    width - margin,
                    height - margin,
                    paintRect
                )

                val circlePadding = 16.dp
                val centerX = width / 2f
                val centerY = height / 2f
                val radius = width / 2f - margin - circlePadding

                canvas.drawCircle(centerX, centerY, radius, paintCircle)


            }
        )


    }

}


