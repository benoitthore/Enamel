package com.benoitthore.enamel.android

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color.*
import android.os.Bundle
import android.util.AttributeSet
import androidx.appcompat.app.AppCompatActivity
import com.benoitthore.enamel.core.animations.sinInterpolator
import com.benoitthore.enamel.core.color
import com.benoitthore.enamel.geometry.figures.ELineMutable
import com.benoitthore.enamel.geometry.innerCircle
import com.benoitthore.enamel.geometry.innerRect
import com.benoitthore.enamel.geometry.primitives.rotations
import com.benoitthore.enamel.layout.android.EFrameView
import com.benoitthore.enamel.layout.android.extract.prepareAnimation
import com.benoitthore.enamel.layout.android.visualentity.RectVisualEntity
import com.benoitthore.enamel.layout.android.visualentity.style.*

inline val Number.dp get() = toFloat() * Resources.getSystem().displayMetrics.density

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(PrototypeView(this))
        setContentView(TestView(this))
    }
}


class TestView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : EFrameView(context, attrs, defStyleAttr) {

    val entity = RectVisualEntity().apply {

    }

    init {
        setOnClickListener {
            prepareAnimation(1500L) {
                offset1 = sinInterpolator(it)
                updateUI()
                invalidate()
            }.start()
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        updateUI()
    }

    var offset1 = 0f
    private fun updateUI() {
        val frame = frame.innerCircle().innerRect(target = entity.rect)

        entity.style = buildEStyle {
            border = gradient
                .diagonalConstrainedMesh(
                    line = frame.diagonalTLBR()
                        .rotate(offset1.rotations(), target = ELineMutable()),
                    colors = listOf(RED, YELLOW)
                ).asBorder(frame.size.min / 3)

            val gradientCircle = frame.innerCircle().selfInset(16.dp)
            fill = gradient.radialMesh(gradientCircle, listOf(RED, WHITE, BLUE))
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(0xcccccc.color)
        entity.draw(canvas)
    }
}