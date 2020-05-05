package com.benoitthore.enamel.android

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.*
import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.Space
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.withRotation
import androidx.core.graphics.withTranslation
import androidx.core.view.doOnNextLayout
import com.benoitthore.enamel.R

// TODO Add these imports to the doc:
import com.benoitthore.enamel.android.demo.*
import com.benoitthore.enamel.core.math.*
import com.benoitthore.enamel.core.math.noise.OpenSimplexNoise
import com.benoitthore.enamel.core.*
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.*
import com.benoitthore.enamel.geometry.alignement.EAlignment.*
import com.benoitthore.enamel.geometry.alignement.selfAlignInside
import com.benoitthore.enamel.geometry.clipping.clipOut
import com.benoitthore.enamel.geometry.figures.rect.ERectMutable
import com.benoitthore.enamel.geometry.figures.rectgroup.ERectGroupImpl
import com.benoitthore.enamel.geometry.figures.rectgroup.ERectGroupMutable
import com.benoitthore.enamel.geometry.figures.rectgroup.rectGroup
import com.benoitthore.enamel.geometry.interfaces.bounds.*
import com.benoitthore.enamel.geometry.primitives.angle.rotations
import com.benoitthore.enamel.geometry.primitives.size.size
import com.benoitthore.enamel.geometry.svg.*
import com.benoitthore.enamel.layout.android.*
import com.benoitthore.enamel.layout.android.singleTouch

import com.benoitthore.visualentity.style.*
import com.benoitthore.visualentity.*


inline val Number.dp get() = toFloat() * Resources.getSystem().displayMetrics.density

fun SeekBar.onSeekChanged(block: SeekBar.(progress: Int) -> Unit) =
    setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            seekBar.block(progress)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }
    })

val debugPaint: Paint = Paint()

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view2 = object : View(this) {
            var rectGroup: ERectGroupMutable = ERectGroupImpl(emptyList())
            val frame1 = E.RectMutable()
            val frame2 = E.RectMutable()

            init {
                setOnClickListener {
                    bool != bool
                    invalidate()
                }
            }

            var bool = true
            override fun onDraw(canvas: Canvas) {

                canvas.scale(3f, 3f)
                frame2.set(150, 300, 200, 200)

                rectGroup = listOf(
                    10 size 10,
                    100 size 100,
                    200 size 200
                ).rectGroup(bottomRight)

                frame1.set(rectGroup)



                if (bool) {
                    rectGroup.set(frame2)
//                    rects.forEach { rect ->
//                        rect.selfMap(frame1, frame2)
//                    }
                }


                debugPaint.strokeWidth = 2.5f
                debugPaint.style = Paint.Style.STROKE
                debugPaint.color = RED
                canvas.draw(frame1, debugPaint)

                debugPaint.color = GREEN
                canvas.draw(frame2, debugPaint)

                rectGroup.rects.forEach { rect ->
                    debugPaint.color = YELLOW
                    canvas.draw(rect.getBounds(), debugPaint)
                }

                bool = !bool

            }
        }

        setContentView(view2)
        return

        val debugStyle =
            EStyle(fill = Mesh(shader = E.Circle(radius = 16.dp).toShader(RED, YELLOW, RED)))
        val view = object : View(this) {

            private val circleTarget = E.PointMutable()
            private val circlePosition = E.PointMutable()
            private var rotationProgress = 0f

            init {
                doOnNextLayout {
                    getBounds().center(circlePosition)
                    getBounds().center(circleTarget)
                }
                singleTouch {
                    circleTarget.set(it.position)
                    true
                }
            }

            @SuppressLint("DrawAllocation")
            override fun onDraw(canvas: Canvas) {
                circlePosition.lerp(0.125, circlePosition, circleTarget)
                rotationProgress += 1

                val bounds = getBounds().apply { setCenter(circlePosition) }

                val shaderBounds = getBounds()
                shaderBounds.setSize(shaderBounds.size.max, shaderBounds.size.max)


                val shader = shaderBounds.diagonalTLBR()
                    .toLinearGradient((0..10).map { colorHSL(it / 10f) })


                canvas.withRotation(rotationProgress, width / 2f, height / 2f) {
                    canvas.drawBackground(Paint().also { it.shader = shader })
                }

                val entity = bounds
                    .innerCircle()
                    .apply { radius /= 2 }
                    .toVisualEntity(debugStyle)
                val mask = entity.scaleAnchor(0.5, 0.5, 0.5).getBounds().innerCircle()


                entity.clipOut(mask).toVisualEntity().draw(canvas)
                invalidate()

            }


        }
        setContentView(view)
//        val view = VisualEntityView(this)
//        setContentView(view)
//
//
//        view.doOnLayout {
//            val bounds = view.getBounds()
//            val circle = view.getBounds().innerCircle().selfScaleAnchor(0.75, 0.5, 0.5)
//            val line = bounds.diagonalTLBR()
//
//
//
//
//            view.show(
//                circle.toVisualEntity(debugStyle).clipOut(line).toVisualEntity()
////                circle.toVisualEntity(debugStyle)
//            )
//
//        }

        return

        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.previousButton).setOnClickListener {
            currentDemo--
        }
        findViewById<Button>(R.id.nextButton).setOnClickListener {
            currentDemo++
        }
        demoView(RectAlignmentAnchor_Rect)

        currentDemo = 0

    }

    private var currentDemo = 0
        set(value) {
            val max = Demos.size - 1
            field = when {
                value > max -> 0
                value < 0 -> 0
                else -> value
            }
            demoView(Demos[field])
        }

    private fun demoView(runner: DemoDrawer) {
        val demoView = findViewById<DemoView>(R.id.testView)

        demoView.demoRunner = runner

        findViewById<LinearLayout>(R.id.seekbar_holder).apply {

            removeAllViews()
            runner.progressLabels.forEachIndexed { i, label ->

                addView(SeekBar(context).apply {
                    onSeekChanged { progress ->
                        demoView.setAnimatedValue(i, progress / 100f)
                    }

                    progress = 50
                })


                if (i < runner.progressLabels.size - 1) {
                    addView(Space(context), LinearLayout.LayoutParams(16.dp.i, 16.dp.i))
                }

            }
        }

    }
}


class JolieVue @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val pathContext = Path().createContext()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.GREEN
    }

    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = 2.dp
        style = Paint.Style.STROKE
    }

    private val shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = 2.dp
        color = Color.BLACK.withAlpha(0.25)
        style = Paint.Style.FILL_AND_STROKE
    }

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    private val noise = OpenSimplexNoise()
    private var offset = 0.0
    private val offsetIncrease = 0.03

    override fun onDraw(canvas: Canvas) {

        val color = noise.eval(offset / 10f).noiseRemap()
        val invColor = ((color * 100 + 50) % 100) / 100.0

        paint.color = colorHSL(invColor)
        canvas.drawColor(colorHSL(color).withAlpha(0.5))
        pathContext.reset()

        val frame = E.RectMutable().setBounds(this)
        val center = frame.center()

        val mainCircle = frame.innerCircle()

        val circleList = mainCircle.toListOfPoint(
            distanceList = (0 until 20).map {
                val radiusRatio =
                    noise.eval(offset, 100 * it.toDouble()).noiseRemap().lerp(0.3, 0.6)
                mainCircle.radius * radiusRatio
            },
            startAt = (offset / 10f).rotations()
        )

        circleList.flatMap {
            listOf<ESVG>(
                it.toCircle(10.dp),
                E.LineMutable(center, it.selfOffsetTowards(center, 10.dp))
            )
        }
            .addTo(pathContext)

        canvas.withTranslation(4.dp, 4.dp) {
            canvas.drawPath(pathContext.path, shadowPaint)
        }
        canvas.drawPath(pathContext.path, paint)
        canvas.drawPath(pathContext.path, strokePaint)

        offset += offsetIncrease
        postInvalidateOnAnimation()
    }

    private fun Double.noiseRemap() = map(-1, 1, 0, 1)
}















