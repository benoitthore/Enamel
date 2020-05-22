package com.benoitthore.enamel.android

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.*
import android.graphics.Paint
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnNextLayout
import com.benoitthore.enamel.geometry.*
import com.benoitthore.enamel.geometry.alignement.EAlignment
import com.benoitthore.enamel.geometry.alignement.selfAlignInside
import com.benoitthore.enamel.geometry.alignement.selfAlignOutside
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.interfaces.bounds.diagonalTLBR
import com.benoitthore.enamel.geometry.interfaces.bounds.scaleAnchor
import com.benoitthore.enamel.geometry.interfaces.bounds.setOriginSize
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.times
import com.benoitthore.enamel.layout.android.*
import com.benoitthore.visualentity.draw
import com.benoitthore.visualentity.style.Mesh
import com.benoitthore.visualentity.style.toShader
import com.benoitthore.visualentity.toVisualEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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

        setContentView(TestView(this))
    }
}

class TestView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val rect = E.Rect().toVisualEntity()
    private val circle = E.Circle().toVisualEntity()

    init {
        doOnNextLayout {
            putShape(E.Point.half * getBounds().size)
            putShape(E.Point.zero)

            rect.style = rect.style.copy(
                fill = Mesh(shader = rect.diagonalTLBR().toShader(BLUE, WHITE, RED))
            )
            circle.style = rect.style.copy(
                fill = Mesh(shader = (circle.innerRect().innerCircle().diagonalTLBR()).toShader(BLUE, WHITE, RED))
            )
        }

        singleTouch {
            putShape(it.position)
            invalidate()
            true
        }
    }

    private fun putShape(position: EPoint) {
        val bounds = getBounds().toImmutable()
        bounds.scaleAnchor(0.5, 0.5, 0.5, target = rect)
        rect.setOriginSize(position.x, position.y)
        rect.innerCircle(circle).selfAlignOutside(rect, EAlignment.rightCenter, 16.dp)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.draw(rect)
        canvas.draw(circle)
    }
}


class MyEnamelView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val rectanglePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 4.dp
        color = Color.RED
    }

    private val shapePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 4.dp
        color = Color.GREEN
    }

    // Creates a rectangle a 0,0 with a with of 200dp and a height of 400dp
    private val rectangle = E.RectMutable(0, 0, 200.dp, 400.dp)

    private val frame = E.RectMutable()
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        // Give our variable the same bounds as the View
        frame.setBounds(this)

        // align the rectangle inside
        rectangle.selfAlignInside(frame, EAlignment.center)
    }

    override fun onDraw(canvas: Canvas) {
        //get the circle
        val circle = rectangle.innerCircle()
        canvas.draw(circle, rectanglePaint)

        val circleList = circle
            // gets 5 points equally spaced around the circle
            .toListOfPoint(5)
            // convert these points to circles, with an 8dp radius
            .map { point -> point.toCircle(8.dp) }

        // Draw the circle list
        canvas.drawCircleList(circleList, shapePaint)
    }
}













