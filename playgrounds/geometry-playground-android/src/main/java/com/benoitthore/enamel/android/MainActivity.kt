package com.benoitthore.enamel.android

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnNextLayout
import com.benoitthore.enamel.core.randomColor
import com.benoitthore.enamel.geometry.*
import com.benoitthore.enamel.geometry.alignement.EAlignment
import com.benoitthore.enamel.geometry.alignement.selfAlignInside
import com.benoitthore.enamel.geometry.alignement.selfAlignOutside
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.times
import com.benoitthore.enamel.layout.android.*
import com.benoitthore.visualentity.android.draw
import com.benoitthore.visualentity.android.toAndroid
import com.benoitthore.visualentity.style.EMesh
import com.benoitthore.visualentity.style.style
import com.benoitthore.visualentity.toVisualEntity


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

//val debugPaint: Paint = Paint()

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(TestView(this))
    }
}

//fun main() {
//    println("1")
//}

class TestView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val style1 = E.style { fillColor = Color.BLUE }
    private val style2 = E.style { borderColor = Color.RED; borderWidth = 4.dp }
    private val circle =
        E.CircleMutable()
            .toVisualEntity(style1)
            .apply { radius = 64.dp }
            .toAndroid()
    private val other =
        E.RectMutable(width = circle.radius, height = circle.radius)
            .toVisualEntity(style2)
//            .apply { end.set(circle.radius * 2, circle.radius * 2) }
            .toAndroid()

    private var alignement = EAlignment.values().random()

    init {
        doOnNextLayout {
            putShape(E.Point.half * getViewBounds().size)
        }

        singleTouch { touch ->
            if (touch.isDown) {
                alignement = EAlignment.values().random()
                circle.style = circle.style.copy(fill = EMesh(randomColor()))
            }
            putShape(touch.position)
            true
        }
    }

    private fun putShape(position: EPoint) {
        val bounds = getViewBounds().toImmutable()
        circle.setCenter(position)
        other.selfAlignOutside(circle, alignement, other.width)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.draw(circle.toImmutable())
        canvas.draw(other.toImmutable())
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













