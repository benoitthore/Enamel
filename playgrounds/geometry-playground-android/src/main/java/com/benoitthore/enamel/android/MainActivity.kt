package com.benoitthore.enamel.android

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
import androidx.core.graphics.withTranslation
import androidx.core.view.doOnLayout
import com.benoitthore.enamel.R
import com.benoitthore.enamel.android.demo.*
import com.benoitthore.enamel.android.demo.DemoDrawer
import com.benoitthore.enamel.android.demo.DemoView
import com.benoitthore.enamel.core.colorHSL
import com.benoitthore.enamel.core.math.i
import com.benoitthore.enamel.core.math.lerp
import com.benoitthore.enamel.core.math.map
import com.benoitthore.enamel.core.math.noise.OpenSimplexNoise
import com.benoitthore.enamel.core.withAlpha
import com.benoitthore.enamel.geometry.alignement.EAlignment.*
import com.benoitthore.enamel.geometry.alignement.selfAlignInside
import com.benoitthore.enamel.geometry.alignement.selfAlignOutside
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.innerCircle
import com.benoitthore.enamel.geometry.innerRect
import com.benoitthore.enamel.geometry.interfaces.bounds.*
import com.benoitthore.enamel.geometry.primitives.minus
import com.benoitthore.enamel.geometry.primitives.radians
import com.benoitthore.enamel.geometry.primitives.rotations
import com.benoitthore.enamel.geometry.primitives.times
import com.benoitthore.enamel.geometry.svg.ESVG
import com.benoitthore.enamel.geometry.svg.addTo
import com.benoitthore.enamel.geometry.toCircle
import com.benoitthore.enamel.geometry.toListOfPoint
import com.benoitthore.enamel.layout.android.createContext
import com.benoitthore.enamel.layout.android.extract.multiTouch
import com.benoitthore.enamel.layout.android.extract.singleTouch
import com.benoitthore.enamel.layout.android.setBounds
import com.benoitthore.enamel.layout.android.visualentity.VisualEntityView
import com.benoitthore.enamel.layout.android.visualentity.style.*
import com.benoitthore.enamel.layout.android.visualentity.toVisualEntity

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

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = VisualEntityView(this)
        setContentView(view)


        view.doOnLayout {

            val viewFrame = E.RectMutable().setBounds(view)
            val rect = E.RectMutable(E.SizeSquare(viewFrame.size.min / 2)).diagonalTLBR()
//                .innerCircle()
            rect.selfAlignInside(viewFrame, center)

            val shader = rect.diagonalTLBR().apply { setOriginSize(0, 0) }
                .toShader(RED, YELLOW, resetOrigin = true)

            val mesh = Mesh(shader = shader)
            val style = EStyle(border = EStyle.Border(mesh, 20.dp))

            val entity = rect.toVisualEntity(style)


            view.singleTouch {

//                entity.setCenter(it.position)
                entity.transformation.translation.set(it.position - entity.getCenter())

                val normalizedPosition = it.position.normalizeIn(viewFrame)

                entity.transformation.rotation.set(
                    normalizedPosition.y.rotations()
                )

                view.show(entity)

                true
            }
        }

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















