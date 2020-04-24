package com.benoitthore.enamel.android

import com.benoitthore.enamel.layout.android.visualentity.style.EGradient.*
import com.benoitthore.enamel.layout.android.visualentity.style.EStyle.*
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.*
import android.os.Bundle
import android.util.AttributeSet
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.benoitthore.enamel.R
import com.benoitthore.enamel.core.color
import com.benoitthore.enamel.core.math.constrain
import com.benoitthore.enamel.core.math.lerp
import com.benoitthore.enamel.geometry.innerCircle
import com.benoitthore.enamel.geometry.innerRect
import com.benoitthore.enamel.layout.android.EFrameView
import com.benoitthore.enamel.layout.android.visualentity.RectVisualEntity
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
        entity.setCornerRadius(progress.lerp(0, entity.rect.size.min / 2))
    }

    val entity = RectVisualEntity().apply {
        setCornerRadius(0.1)
    }

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        updateUI()
    }

    private fun updateUI() {
        val frame = frame.innerCircle().innerRect(target = entity.rect)
        val gradientCircle = frame.innerCircle()
        entity.style = EStyle(
            border = DiagonalConstrained(
                line = frame.diagonalTLBR(),
                colors = listOf(RED, YELLOW)
            ).asBorder(16.dp),
            fill = Radial(gradientCircle, listOf(RED, WHITE, BLUE)).asMesh(),
            shadow = Mesh.Color(RED).asShadow(4.dp)
        )
    }


    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(0xcccccc.color)
        entity.draw(canvas)
    }
}