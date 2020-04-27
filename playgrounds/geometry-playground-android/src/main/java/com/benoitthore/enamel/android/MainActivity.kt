package com.benoitthore.enamel.android

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.Color.*
import android.os.Bundle
import android.util.AttributeSet
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.benoitthore.enamel.R
import com.benoitthore.enamel.core.color
import com.benoitthore.enamel.core.math.*
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.size
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

    private val et1: RectVisualEntity
    private val et2: CircleVisualEntity

    init {
        val rect = E.rect(size = 100.dp size 100.dp)
        val diagonal = rect.diagonalTLBR()

        et1 = RectVisualEntity(
            EStyle(fill = Mesh(shader = diagonal.toLinearGradient(colors = listOf(RED, YELLOW)))),
            rect
        )

        et2 = CircleVisualEntity(
            et1.style.copy()
        ) {
            et1.rect.outterCircle(this)
        }

        singleTouch { _, current, _ ->

            if (current != null) {
                // Move rect to finger
                et1.transformation.translation.set(current)
                // Move circle under rect
                et2.circle.set(center = current)
                et2.circle.selfOffset(0, et1.rect.height)
            }
            invalidate()
            true
        }
    }


    private val paint = Paint().apply {
        color = Color.RED
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
    }

    private val bufferPoint = E.mpoint()
    private val bufferCircle = E.mcircle()


    var i = 0
    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(0xcccccc.color)
        et1.draw(canvas)
        et2.draw(canvas)
    }
}