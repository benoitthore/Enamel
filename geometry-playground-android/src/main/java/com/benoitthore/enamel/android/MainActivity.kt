package com.benoitthore.enamel.android

import android.animation.TimeInterpolator
import android.content.Context
import android.content.res.Configuration
import android.graphics.*
import android.graphics.Color.*
import android.os.Build
import android.os.Bundle
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.benoitthore.enamel.android.demo.CanvasTestView
import com.benoitthore.enamel.android.demo.canvasView
import com.benoitthore.enamel.core.animations.EasingInterpolators
import com.benoitthore.enamel.core.math.map
import com.benoitthore.enamel.core.math.noise.OpenSimplexNoise
import com.benoitthore.enamel.core.math.noise.invoke
import com.benoitthore.enamel.core.math.random
import com.benoitthore.enamel.core.time.ETimer
import com.benoitthore.enamel.geometry.AllocationTracker
import com.benoitthore.enamel.geometry.alignement.EAlignment.*
import com.benoitthore.enamel.geometry.alignement.EAlignment
import com.benoitthore.enamel.geometry.figures.*
import com.benoitthore.enamel.geometry.innerCircle
import com.benoitthore.enamel.geometry.innerRect
import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.geometry.layout.ELayoutLeaf
import com.benoitthore.enamel.geometry.layout.ESizingLayout
import com.benoitthore.enamel.geometry.layout.dsl.arranged
import com.benoitthore.enamel.geometry.layout.dsl.sized
import com.benoitthore.enamel.geometry.layout.flowed
import com.benoitthore.enamel.geometry.layout.refs.getAllChildren
import com.benoitthore.enamel.geometry.layout.refs.getLeaves
import com.benoitthore.enamel.geometry.primitives.degrees
import com.benoitthore.enamel.geometry.primitives.radians
import com.benoitthore.enamel.geometry.toCircle
import com.benoitthore.enamel.layout.android.dp
import com.benoitthore.enamel.layout.android.extract.*

val Context.isLandscape get() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

fun Context.fancyView(): CanvasTestView {
    val pool = GeometryPool(100)


    // Utils
    val noise = OpenSimplexNoise()
    val timer = ETimer()
    val paint = Paint()
    val debugPaint = Paint().apply {
        color = WHITE
    }
    paint.apply {
        isAntiAlias = true
    }

    // Settings
    val targetFrameDuration = 16f

    val angleIncrease = 0.5 * 1 / 60f

    val noiseOffsetIncrease = 5f
    val noiseZoom = 750f

    val speedUpMultiplier = 500

    // Keep track of
    val angle = 0.degrees()
    var noiseOffset = 0f


    var shader: Shader? = null
    return canvasView({
        setOnClickListener {
            var last = 0f
            prepareAnimation {
                noiseOffset += (it - last) * noiseOffsetIncrease * speedUpMultiplier
                last = it
            }.apply {
                duration = 4000
                interpolator = TimeInterpolator { input -> EasingInterpolators.cubicInOut(input) }
                start()
            }
        }
    }) { canvas ->

        canvas.drawColor(DKGRAY)

        val deltaTime = if (timer.isStarted) {
            timer.elapsed / targetFrameDuration
        } else 1f
        timer.start()


        noiseOffset += noiseOffsetIncrease * deltaTime

        pool.use {

            paint.color = LTGRAY
            paint.style = Paint.Style.STROKE

            // Define big circle
            frame.innerCircle(circlePool())
                .selfInset(width * 0.005)
                // Set gradient shader
                .also { circle: ECircle ->
                    if (shader == null)
                        circle.innerRect(rectPool())
                            .selfScaleAnchor(0.66, 0.5, 0.5).let {
                                shader = (it.topLeft() line it.bottomRight())
                                    .toLinearGradient(
                                        (0 until 4).map { colorHSL(it / 4f) }
                                    )
                            }
                }
                // Small circles positions
                .let { circle: ECircle ->

                    val points = circle.toListOfPoint(
                        pointPool.list(50),
                        angle.selfOffset((deltaTime * angleIncrease).radians(anglePool()))
                    )


                    // Point position with noise
                    points.forEachIndexed { i, point ->
                        val distance = noise(
                            x = (noiseOffset + point.x) / noiseZoom,
                            y = point.y / noiseZoom
                        )
                            .map(-1, 1, 0.33, 1) * circle.radius
                        point.set(
                            circle.center.offsetTowards(point, distance, pointPool())
                        )
                    }
                    points
                }
                // Convert points to small circle
                .map { it.toCircle(width * 0.01, circlePool()) }

                // Draw
                .forEachIndexed { i, circle: ECircle ->
                    paint.style = Paint.Style.FILL
                    paint.color = WHITE

                    paint.shader = shader
                    circle.draw(paint)

                    linePool().let { line ->
                        paint.color = CYAN
                        paint.strokeWidth = width * 0.0025f
                        line.set(circle.center, frame.center(pointPool())).draw(paint)
                    }
                    paint.shader = null
                }


            invalidate()
        }

    }
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AllocationTracker.debugAllocations = true
//        setContentView(fancyView())

        val paint = Paint().apply {
            style = Paint.Style.FILL
        }

        setContentView(canvasView { canvas ->

            val list = List(10) {
                ELayoutLeaf(randomColor())
                    .sized(ESize.RandomSquare(32.dp, 64.dp))

            }
            val layout = list.flowed()

            layout.arranged(topLeft).arrange(frame)

            layout.getLeaves().forEach { leaf ->
                paint.color = leaf.color
                canvas.drawRect(leaf.frame, paint)
            }
        })

    }
}

data class ETextStyle(val alignment: EAlignment = EAlignment.topLeft)
class ETextView(
    val paint: TextPaint,
    val style: ETextStyle = ETextStyle()
) : ELayout {
    var text: String = ""

    override val children: List<ELayout> = emptyList()

    private val rect: Rect = Rect()
    private val bufferSize = ESizeMutable()
    override fun size(toFit: ESize): ESize {
        rect.width()
        paint.getTextBounds(text, 0, 1, rect)

        return bufferSize.set(rect.width(), rect.height())
    }

    override fun arrange(frame: ERect) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun draw(canvas: Canvas) {

    }

}