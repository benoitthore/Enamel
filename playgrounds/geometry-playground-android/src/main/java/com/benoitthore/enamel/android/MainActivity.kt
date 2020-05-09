package com.benoitthore.enamel.android

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color.*
import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.Space
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.withTranslation
import androidx.core.view.doOnLayout
import com.benoitthore.animation.prepareAnimation
import com.benoitthore.enamel.R

// TODO Add these imports to the doc:
import com.benoitthore.enamel.android.demo.*
import com.benoitthore.enamel.core.math.*
import com.benoitthore.enamel.core.*
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.line.toListOfLines
import com.benoitthore.enamel.geometry.innerCircle
import com.benoitthore.enamel.geometry.interfaces.bounds.*
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.toImmutable
import com.benoitthore.enamel.geometry.toRect
import com.benoitthore.enamel.layout.android.*
import com.benoitthore.visualentity.withPathMeasureData
import com.benoitthore.visualentity.withRotation
import com.benoitthore.visualentity.withTranslation
import com.benoitthore.enamel.geometry.svg.*
import com.benoitthore.enamel.geometry.toListOfPoint


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

        setContentView(JolieVue(this))
    }


    fun runDemo() {
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


    val pathContext = Path().createSVGContext()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = GREEN
    }

    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = 2.dp
        color = RED
        style = Paint.Style.STROKE
    }

    private val shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = 2.dp
        color = BLACK.withAlpha(0.25)
        style = Paint.Style.FILL_AND_STROKE
    }

    private var progress = 0f

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)

        doOnLayout {


        }

        singleTouch {
            if (it.isDown) {
                points.clear()
            }
            points += it.position.toImmutable()

            pathContext.reset()
            (points.toListOfLines()).addTo(pathContext)
            true
        }
    }

    private val points = mutableListOf<EPoint>()

    private val rect = E.RectMutableCenter(0, 0, 100, 100)
    override fun onDraw(canvas: Canvas) {

        val data =
            pathContext.pathMeasure.getAbsolute(progress % pathContext.pathMeasure.getTotalDistance())

        canvas.drawPath(pathContext.path, strokePaint)

        canvas.withPathMeasureData(data) {
            canvas.draw(rect, paint)
        }

        progress += width  / 60f // the width per second
        postInvalidateOnAnimation()
    }
}

private fun Double.noiseRemap() = map(-1, 1, 0, 1)















