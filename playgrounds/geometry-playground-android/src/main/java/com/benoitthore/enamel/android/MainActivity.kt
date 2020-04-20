package com.benoitthore.enamel.android

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.*
import android.graphics.Color.*
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextPaint
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import com.squareup.picasso.Target
import kotlinx.coroutines.*
import kotlin.coroutines.resume
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.benoitthore.enamel.R
import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.alignement.EAlignment.*
import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.layout.android.extract.CanvasLayoutView
import com.benoitthore.enamel.layout.android.extract.layout.*
import com.benoitthore.enamel.core.randomColor
import com.benoitthore.enamel.geometry.*
import com.benoitthore.enamel.geometry.figures.ESize
import com.benoitthore.enamel.geometry.figures.size
import com.benoitthore.enamel.geometry.layout.dsl.*
import com.benoitthore.enamel.layout.android.extract.drawCircle
import com.benoitthore.enamel.layout.android.extract.drawCircles
import com.benoitthore.enamel.layout.android.extract.drawRect

inline val Number.dp get() = (toFloat() * Resources.getSystem().displayMetrics.density).toInt()

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(TestView(this))
    }
}

class TestView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CanvasLayoutView(context, attrs, defStyleAttr) {
    private val paint = TextPaint()
        .apply {
            style = Paint.Style.STROKE
            strokeWidth = 8.dp.f
            color = randomColor()
        }

    init {
        setOnClickListener {
            paint.color = randomColor()
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val circle = frame.center().toCircle(width * 0.25)
        canvas.drawCircle(circle, paint)
        canvas.drawRect(circle.innerRect().selfInset(8.dp), paint)
    }
}