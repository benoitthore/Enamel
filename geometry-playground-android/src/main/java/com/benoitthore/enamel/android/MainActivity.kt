package com.benoitthore.enamel.android

import android.animation.TimeInterpolator
import android.content.Context
import android.content.res.Configuration
import android.graphics.*
import android.graphics.Color.*
import android.os.Build
import android.os.Bundle
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.benoitthore.enamel.android.demo.CanvasTestView
import com.benoitthore.enamel.android.demo.canvasView
import com.benoitthore.enamel.core.animations.EasingInterpolators
import com.benoitthore.enamel.core.math.map
import com.benoitthore.enamel.core.math.noise.OpenSimplexNoise
import com.benoitthore.enamel.core.math.noise.invoke
import com.benoitthore.enamel.core.math.random
import com.benoitthore.enamel.core.time.ETimer
import com.benoitthore.enamel.geometry.AllocationTracker
import com.benoitthore.enamel.geometry.alignement.EAlignment.*
import com.benoitthore.enamel.geometry.alignement.EAlignment
import com.benoitthore.enamel.geometry.figures.*
import com.benoitthore.enamel.geometry.innerCircle
import com.benoitthore.enamel.geometry.innerRect
import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.geometry.layout.ELayoutLeaf
import com.benoitthore.enamel.geometry.layout.ESizingLayout
import com.benoitthore.enamel.geometry.layout.dsl.arranged
import com.benoitthore.enamel.geometry.layout.dsl.padded
import com.benoitthore.enamel.geometry.layout.dsl.sized
import com.benoitthore.enamel.geometry.layout.flowed
import com.benoitthore.enamel.geometry.layout.refs.getAllChildren
import com.benoitthore.enamel.geometry.layout.refs.getLeaves
import com.benoitthore.enamel.geometry.primitives.degrees
import com.benoitthore.enamel.geometry.primitives.radians
import com.benoitthore.enamel.geometry.toCircle
import com.benoitthore.enamel.layout.android.dp
import com.benoitthore.enamel.layout.android.extract.*

val Context.isLandscape get() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AllocationTracker.debugAllocations = true
//        setContentView(fancyView())

        val paint = TextPaint().apply {
            style = Paint.Style.FILL
            color = BLACK
        }

        val textPaint = TextPaint().apply {
            textSize = 60f
            color = WHITE
        }
        var text = """
    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor
        incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris
        nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum
        dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia
        deserunt mollit anim id est laborum
""".trimIndent()

        val wordLayout = text.toWordLayout(textPaint).padded(32.dp)
        setContentView(canvasView { canvas ->

            canvas.drawColor(DKGRAY)

            wordLayout.arrange(frame)
            wordLayout.getAllChildren().forEach { layout ->
                (layout as? EWordLayout)?.let {
                    canvas.drawRect(it.frame, paint)
                    it.draw(canvas)
                }

            }

//            val list = List(10) {
//                ELayoutLeaf(colorHSL(random(0, 0.8)))
//                    .sized(ESize.RandomSquare(128.dp, 32.dp))
//
//            }
//            val layout = list
//                .flowed(lineSpacing = 8.dp, childSpacing = 16.dp)
//                .padded(8.dp)
//
//            layout.arranged(topLeft).arrange(frame)
//
//            layout.getLeaves().forEach { leaf ->
//                paint.color = leaf.color
//                canvas.drawRect(leaf.frame, paint)
//            }
        })

    }
}

fun CharSequence.toWordLayout(paint: Paint) =
    split(" ")
        .filter { it.isNotBlank() }
        .map { EWordLayout(paint, it) }
        .flowed(lineSpacing = 8.dp, childSpacing = 16.dp)

class EWordLayout(
    val paint: Paint,
    text: CharSequence = ""
) : ELayout {

    var text: CharSequence = text
        set(value) {
            field = value
            stringText = value.toString()
        }
    // Avoid allocation
    private var stringText = this.text.toString()

    override val children: List<ELayout> = emptyList()
    private val _frame = ERectMutable()
    val frame: ERect get() = _frame

    private val bufferSize = ESizeMutable()

    override fun size(toFit: ESize): ESize {

        return bufferSize.set(
            paint.measureText(stringText),
            paint.textSize + paint.descent()
        )
    }


    override fun arrange(frame: ERect) {
        _frame.set(frame)
    }

    fun draw(canvas: Canvas) {
        canvas.drawText(stringText, _frame.x, _frame.y - paint.ascent(), paint)
    }

}