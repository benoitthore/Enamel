package com.benoitthore.enamel.android

// TODO Add these imports to the doc:
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
import com.benoitthore.enamel.geometry.alignement.EAlignment
import com.benoitthore.enamel.geometry.alignement.selfAlignInside
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.innerCircle
import com.benoitthore.enamel.geometry.interfaces.bounds.diagonalTLBR
import com.benoitthore.enamel.geometry.interfaces.bounds.diagonalTRBL
import com.benoitthore.enamel.geometry.outerCircle
import com.benoitthore.enamel.geometry.toCircle
import com.benoitthore.enamel.geometry.toListOfPoint
import com.benoitthore.enamel.layout.android.draw
import com.benoitthore.enamel.layout.android.drawCircleList
import com.benoitthore.enamel.layout.android.setBounds


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

        setContentView(MyEnamelView(this))
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













