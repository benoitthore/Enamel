package com.benoitthore.enamel.android

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.text.TextPaint
import android.util.AttributeSet
import androidx.appcompat.app.AppCompatActivity
import com.benoitthore.enamel.android.prototypes.PrototypeView
import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.figures.ELineMutable
import com.benoitthore.enamel.geometry.figures.line
import com.benoitthore.enamel.geometry.innerCircle
import com.benoitthore.enamel.geometry.innerRect
import com.benoitthore.enamel.geometry.primitives.point
import com.benoitthore.enamel.geometry.toCircle
import com.benoitthore.enamel.layout.android.EFrameView
import com.benoitthore.enamel.layout.android.extract.draw
import com.benoitthore.enamel.layout.android.visualentity.EGradient
import com.benoitthore.enamel.layout.android.visualentity.EStyle
import com.benoitthore.enamel.layout.android.visualentity.RectVisualEntity

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
        style = EStyle(
//            fill = EStyle.Mesh.Color(Color.RED)
        )
    }

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        frame.innerCircle().innerRect(target = entity.rect)

        entity.style.border = EStyle.Border(
            mesh = EStyle.Mesh.Gradient(
                EGradient.Diagonal(
                    entity.rect.topLeft() line entity.rect.bottomRight(),
                    listOf(Color.RED, Color.YELLOW)
                )
            ),
            width = 4.dp.f
        )
        entity.updateStyle()
    }

    private val paint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = 8.dp
        textSize = 20.dp
    }

    override fun onDraw(canvas: Canvas) {
//        canvas.drawColor(0xcccccc.color)
//        entity.draw(canvas)


        val greenLine = (0 point height / 2) line (width point height / 2)
        paint.color = Color.GREEN
        canvas.draw(greenLine, paint)

        val redLine = greenLine
            .perpendicular(0.5, greenLine.length, ELineMutable())

        val blueLine = redLine.expanded(32.dp, from = .5f, target = ELineMutable())

        paint.color = Color.BLUE
        canvas.draw(blueLine, paint)
        canvas.draw(blueLine.start.toCircle(16.dp), paint)


        paint.color = Color.RED
        canvas.draw(redLine, paint)
        canvas.draw(redLine.start.toCircle(16.dp), paint)

        // OFFSET
//        off += 0.0025f
//        if (off > 1) {
//            off = 0f
//        }
//        paint.color = Color.BLACK
//        canvas.drawText(off._2dec, 0f, paint.textSize, paint)
//        invalidate()

    }
}