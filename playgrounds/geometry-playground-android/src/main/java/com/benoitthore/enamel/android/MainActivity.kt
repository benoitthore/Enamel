package com.benoitthore.enamel.android

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.Color.*
import android.os.Bundle
import android.util.AttributeSet
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnLayout
import com.benoitthore.enamel.R
import com.benoitthore.enamel.core.color
import com.benoitthore.enamel.core.math.*
import com.benoitthore.enamel.geometry.alignement.EAlignment.*
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.size
import com.benoitthore.enamel.geometry.innerCircle
import com.benoitthore.enamel.geometry.outterCircle
import com.benoitthore.enamel.layout.android.EFrameView
import com.benoitthore.enamel.layout.android.extract.singleTouch
import com.benoitthore.enamel.layout.android.visualentity.*
import com.benoitthore.enamel.layout.android.visualentity.style.*

inline val Number.dp get() = toFloat() * Resources.getSystem().displayMetrics.density

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(PrototypeView(this))
        setContentView(
            R.layout.activity_main
        )

        val seekbar = findViewById<SeekBar>(R.id.seekbar)
        val testView = findViewById<TestView>(R.id.testView)

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val progress = progress / 100f
                testView.progress = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }
}


class TestView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : EFrameView(context, attrs, defStyleAttr) {

    var progress: Float = 0f
        set(value) {
            field = value.constrain(0, 1)
            onProgressUpdate()
            invalidate()
        }

    private fun onProgressUpdate() {
        progress
    }

    private val ve1: RectVisualEntity = RectVisualEntity()

    init {
        doOnLayout {
            frame.rectAlignedInside(center, 100.dp size 100.dp, target = ve1)

            ve1.style = ve1.style.copy(
                fill = Mesh(shader = ve1.innerCircle().toRadialGradient(RED, YELLOW))
            )

            singleTouch { isDown, current, previous ->
                if (current != null) {
                    ve1.center = current
                    invalidate()
                }
                true
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(0xcccccc.color)
        ve1.draw(canvas)
    }
}


























