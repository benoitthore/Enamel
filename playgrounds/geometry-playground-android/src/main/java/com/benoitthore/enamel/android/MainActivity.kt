package com.benoitthore.enamel.android

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.benoitthore.enamel.core.animations.endInterpolator
import com.benoitthore.enamel.core.color
import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.core.randomColor
import com.benoitthore.enamel.geometry.alignement.EAlignment
import com.benoitthore.enamel.geometry.figures.ESize
import com.benoitthore.enamel.geometry.figures.line
import com.benoitthore.enamel.geometry.figures.size
import com.benoitthore.enamel.geometry.innerCircle
import com.benoitthore.enamel.geometry.innerRect
import com.benoitthore.enamel.geometry.primitives.degrees
import com.benoitthore.enamel.layout.android.EFrameView
import com.benoitthore.enamel.layout.android.visualentity.EGradient
import com.benoitthore.enamel.layout.android.visualentity.EStyle
import com.benoitthore.enamel.layout.android.visualentity.RectVisualEntity

inline val Number.dp get() = (toFloat() * Resources.getSystem().displayMetrics.density).toInt()

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(TestView(this))
    }
}


class TestView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : EFrameView(context, attrs, defStyleAttr) {

    val entity = RectVisualEntity().apply {
        style = EStyle(
//            fill = EStyle.Mesh.Color(Color.RED),
            fill = EStyle.Mesh.Gradient(
//                EGradient.DiagonalFill(45.degrees(), (0 until 5).map { randomColor() })
                EGradient.Diagonal(
                    frame.topLeft() line frame.bottomRight(),
                    listOf(Color.RED, Color.YELLOW)
                )
            ),
            border = EStyle.Border(Color.BLACK, 4.dp.f)
        )
    }

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        frame.innerCircle().innerRect(target = entity.rect)

        (entity.style.fill as EStyle.Mesh.Gradient).gradient = EGradient.Diagonal(
            entity.rect.topLeft() line entity.rect.bottomRight(),
            listOf(Color.RED, Color.YELLOW)
        )
        entity.updateStyle()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(0xcccccc.color)
        entity.draw(canvas)

    }
}