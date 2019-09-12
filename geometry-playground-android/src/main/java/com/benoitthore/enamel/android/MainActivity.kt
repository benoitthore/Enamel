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
import com.benoitthore.enamel.geometry.figures.line
import com.benoitthore.enamel.geometry.innerCircle
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

fun Context.sleepCanvasView(sleep: Long? = null): CanvasTestView {
    val pool = GeometryPool(100)

    val paint = Paint()
    paint.isAntiAlias = true

    val timer = ETimer()
    val targetFrameDuration = 16f
    val angleIncrease = 0.2 * 1 / 60f
    val noiseOffsetIncrease = 1 / 60f

    val angle = 0.degrees()


    val noise = OpenSimplexNoise(29311L)

    var offset = 0f
    return canvasView { canvas ->

        val deltaTime = if (timer.isStarted) {
            timer.elapsed / targetFrameDuration
        } else 1f
        timer.start()


        offset += noiseOffsetIncrease * deltaTime

        println("${hashCode()}\t\t$offset")

        pool.apply {

            paint.color = LTGRAY
            paint.style = Paint.Style.STROKE
            frame.innerCircle(circlePool()).draw(paint)
                .selfInset(width * 0.005)
                .let { circle ->

                    circle.toListOfPoint(
                        pointPool.list(50),
                        angle.selfOffset((deltaTime * angleIncrease).radians(anglePool())),
                        distanceList = (0 until 50).map {
                            circle.radius *
                                    Scale.map((noise(offset + it / 10f)), -1, 1, 0.1, 1)
                        }
                    )
                }
                .map { it.toCircle(width * 0.01, circlePool()) }
                .forEachIndexed { i, it: ECircle ->
                    paint.style = Paint.Style.FILL
                    paint.color =
                        WHITE
//                        if (i == 0) Color.WHITE else Color.BLACK
                    it.draw(paint)

                    linePool().let { line ->
                        paint.color = CYAN
                        paint.strokeWidth = width * 0.0025f
                        line.set(it.center, frame.center(pointPool())).draw(paint)
                    }
                }


        }

        GlobalScope.launch(Dispatchers.Main) {
            sleep?.let {
                delay(it)
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


        val sleepView = sleepCanvasView(100)
        val noSleepView = sleepCanvasView()

        val alignment = if (isLandscape) {
            rightCenter
        } else {
            bottomCenter
        }

        val demoView1 = eViewGroup {

            backgroundColor = Color.DKGRAY

            listOf(noSleepView, sleepView).laid()
                .equallySized(alignment, 4.dp)
                .padded(4.dp)
                .arranged(center)
        }

//        setContentView(demoView1)
//        setContentView(MyCanvasView(this))


        val paintRect = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = RED
        }

        val paintCircle = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = BLUE
        }

        AllocationTracker.debugAllocations = true


        ///


        val noise = OpenSimplexNoise()
        var noiseOffset = 0f

        var ratio = 0.5f

        val nbOfPoints = 20

        val pool = GeometryPool()

        setContentView(
            canvasView { canvas ->

                frame
                    .inset(width , pool.rectPool())
                    .innerCircle(pool.circlePool())
                    .let { circle ->
                        circle.toListOfPoint(
                            pool.pointPool(nbOfPoints),
                            distanceList = (0 until nbOfPoints).map {
                                Scale.map(
                                    noise(it + noiseOffset),
                                    -1,
                                    1,
                                    circle.radius * 0.1,
                                    circle.radius
                                )
                            },
                            startAt = ratio.rotations(pool.anglePool())
                        )
                    }
                    .map { it.toCircle(16.dp, pool.circlePool()) }
                    .forEachIndexed { i, circle ->
                        paintCircle.color = colorHSL(i / nbOfPoints.f)
                        circle.draw(paintCircle)
                    }

                noiseOffset += 0.05f
                invalidate()


            }.also { v ->
                v.backgroundColor = LTGRAY
                v.touchProgress { x, y ->
                    ratio = y
                    v.invalidate()
                }
            }
        )


    }

}


