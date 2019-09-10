package com.benoitthore.enamel.android

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.core.print
import com.benoitthore.enamel.core.time.ETimer
import com.benoitthore.enamel.geometry.AllocationTracker
import com.benoitthore.enamel.geometry.alignement.EAlignment.center
import com.benoitthore.enamel.geometry.figures.ECircle
import com.benoitthore.enamel.geometry.figures.ECircleMutable
import com.benoitthore.enamel.geometry.layout.dsl.arranged
import com.benoitthore.enamel.geometry.primitives.AngleType
import com.benoitthore.enamel.geometry.primitives.degrees
import com.benoitthore.enamel.geometry.primitives.radians
import com.benoitthore.enamel.geometry.toCircle
import com.benoitthore.enamel.layout.android.dp
import com.benoitthore.enamel.layout.android.eViewGroup
import kotlin.contracts.ExperimentalContracts

class MainActivity : AppCompatActivity() {

    @ExperimentalContracts
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AllocationTracker.debugAllocations = true

        val pool = GeometryPool(100)

        val paint = Paint()

        val timer = ETimer()
        val targetFrameDuration = 16f
        val frameIncrease = 1 / 60f

        val angle = 0.degrees()

        val canvasView = canvasView { canvas ->

            val deltaTime = if (timer.isStarted) {
                timer.elapsed / targetFrameDuration
            } else 1f
            timer.start()

            deltaTime.print
            pool.apply {

                paddedFrame { frame ->
                    frame.center(pointPool.next)
                        .toCircle(frame.width / 2, circlePool.next)
                        .selfInset(32.dp)
                        .toListOfPoint(
                            pointPool.list(50),
                            angle.selfOffset((deltaTime * frameIncrease).radians(anglePool.next))
                        )
                        .map { it.toCircle(10.dp, circlePool.next) }
                        .forEachIndexed { i, it: ECircle ->
                            paint.strokeWidth = 0.dp.f
                            paint.color = if (i == 0) Color.RED else Color.BLACK
                            it.draw(paint)

                            linePool().let { line ->
                                paint.color = Color.CYAN
                                paint.strokeWidth = 1.dp.f
                                line.set(it.center, frame.center(pointPool())).draw(paint)
                            }
                        }

                }
            }

            invalidate()
        }


        setContentView(
            eViewGroup {


                canvasView.laid().arranged(center)
            }

        )
    }

}

