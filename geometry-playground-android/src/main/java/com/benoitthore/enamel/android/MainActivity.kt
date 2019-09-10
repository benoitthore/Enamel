package com.benoitthore.enamel.android

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.core.print
import com.benoitthore.enamel.core.time.ETimer
import com.benoitthore.enamel.geometry.AllocationTracker
import com.benoitthore.enamel.geometry.alignement.EAlignment.center
import com.benoitthore.enamel.geometry.alignement.EAlignment.rightCenter
import com.benoitthore.enamel.geometry.figures.ECircle
import com.benoitthore.enamel.geometry.innerCircle
import com.benoitthore.enamel.geometry.layout.dsl.*
import com.benoitthore.enamel.geometry.primitives.degrees
import com.benoitthore.enamel.geometry.primitives.radians
import com.benoitthore.enamel.geometry.toCircle
import com.benoitthore.enamel.layout.android.dp
import com.benoitthore.enamel.layout.android.eViewGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.contracts.ExperimentalContracts

fun Context.sleepCanvasView(sleep: Long? = null): CanvasTestView {
    val pool = GeometryPool(100)

    val paint = Paint()

    val timer = ETimer()
    val targetFrameDuration = 16f
    val frameIncrease = 1 / 60f

    val angle = 0.degrees()

    return canvasView { canvas ->

        val deltaTime = if (timer.isStarted) {
            timer.elapsed / targetFrameDuration
        } else 1f
        timer.start()

        pool.apply {

            canvas.drawColor(Color.RED)
            frame.innerCircle(circlePool())
                .selfInset(width * 0.05)
                .toListOfPoint(
                    pointPool.list(50),
                    angle.selfOffset((deltaTime * frameIncrease).radians(anglePool()))
                )
                .map { it.toCircle(width * 0.01, circlePool()) }
                .forEachIndexed { i, it: ECircle ->
                    paint.strokeWidth = 0.dp.f
                    paint.color =
                        if (i == 0) Color.WHITE else Color.BLACK
                    it.draw(paint)

                    linePool().let { line ->
                        paint.color = Color.CYAN
                        paint.strokeWidth = width * 0.005f
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


        val sleepView = sleepCanvasView(1000)
        val noSleepView = sleepCanvasView()


        setContentView(
            eViewGroup {

                listOf(sleepView, noSleepView).laid()
                    .map { it.padded(4.dp) }
                    .equallySized(rightCenter)
                    .arranged(center)
            }

        )
    }

}

