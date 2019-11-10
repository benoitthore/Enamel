package com.benoitthore.enamel.android

import android.content.Context
import android.content.res.Configuration
import android.graphics.*
import android.graphics.Color.*
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextPaint
import androidx.appcompat.app.AppCompatActivity
import com.benoitthore.enamel.geometry.AllocationTracker
import com.benoitthore.enamel.layout.android.dp
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import com.squareup.picasso.Target
import kotlinx.coroutines.*
import kotlin.coroutines.resume
import android.graphics.Bitmap
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.benoitthore.enamel.R
import com.benoitthore.enamel.core.math.random
import com.benoitthore.enamel.geometry.alignement.EAlignment.*
import com.benoitthore.enamel.geometry.layout.EFlowLayout
import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.layout.android.extract.CanvasLayoutView
import com.benoitthore.enamel.layout.android.extract.layout.*
import com.benoitthore.enamel.core.randomColor
import com.benoitthore.enamel.geometry.figures.ESize
import com.benoitthore.enamel.geometry.figures.size
import com.benoitthore.enamel.geometry.innerCircle
import com.benoitthore.enamel.geometry.layout.dsl.*
import com.benoitthore.enamel.geometry.toCircles
import com.benoitthore.enamel.layout.android.extract.drawCircles


val Context.isLandscape get() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

val loremIpsum =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum"

fun Context.toast(s: String) = Toast.makeText(this, s, Toast.LENGTH_SHORT).show()

fun generateText(numberOfWords: Int = 10): String = with(loremIpsum.split(" ").shuffled()) {
    (0 until numberOfWords).joinToString(separator = " ") { random() }
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AllocationTracker.debugAllocations = false

        val textPaint = TextPaint().apply {
            textSize = 60f
            color = WHITE
        }

        val view = CanvasLayoutView(this)
        view.setBackgroundColor(DKGRAY)

        val image1 = ContextCompat.getDrawable(this, R.drawable.ic_launcher_background)!!
            .imageLayout()
            .changeTintOnClick()
            .sizedSquare(64.dp)

        val image2 = ContextCompat.getDrawable(this, R.drawable.ic_launcher_background)!!
            .imageLayout()
            .changeTintOnClick()
            .sizedSquare(128.dp)

        val textview = generateText(10).wordLayout(textPaint).changeTextColorOnClick()

        val myCanvasLayout = object : ECanvasLayout() {
            val myPaint = Paint().apply {
                color = RED
                style = Paint.Style.FILL
            }

            override fun draw(canvas: Canvas) {

                val circles = frame
                    .innerCircle()
                    .toListOfPoint(10)
                    .toCircles(frame.size.min * 0.05)

                canvas.drawCircles(circles, myPaint)
            }

            override fun size(toFit: ESize): ESize = toFit.min size toFit.min
        }
            .onClick { myPaint.color = randomColor(); invalidate() }
            .sizedSquare(128.dp)


        val l1 = listOf(myCanvasLayout, image1, textview, image2).stackedBottomCenter(32.dp)
            .arranged(center)
        view.layout = l1
        setContentView(view)
    }

}

private fun EImageLayout.changeTintOnClick(): ELayout =
    let { layout ->
        layout.onClick {
            paint.colorFilter =
                PorterDuffColorFilter(randomColor(), PorterDuff.Mode.MULTIPLY)
            layout.viewParent?.invalidate()
        }
    }

private fun EFlowLayout.changeTextColorOnClick(): ELayout =
    let { layout ->
        layout.onClick {
            val color = randomColor()
            children.filterIsInstance<EWordLayout>().forEach {
                it.paint.color = color
                it.viewParent?.invalidate()
            }
        }
    }

/////
/////
/////
/////
/////

// KEEP HERE - Picasso shouldn't be put into the geometry module
suspend inline fun String.imageLayout(
    paint: Paint = Paint(), crossinline block: RequestCreator.() -> Unit = {}
) = EImageLayout(downloadImage(block), paint)

suspend inline fun String.downloadImage(crossinline block: RequestCreator.() -> Unit = {}): Bitmap =
    withContext(Dispatchers.Main) {
        suspendCancellableCoroutine<Bitmap> { cont ->

            var done = false
            Picasso.get()
                .load(this@downloadImage)
                .apply(block)
                .into(object : Target {
                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

                    }

                    override fun onBitmapFailed(e: Exception, errorDrawable: Drawable?) {
                        done = true
                        cont.cancel(e)
                    }

                    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom?) {
                        done = true
                        cont.resume(bitmap)
                    }
                })


            GlobalScope.launch {
                delay(5000L)
                if (!done) {
                    cont.cancel(Exception("TIMEOUT"))
                }
            }
        }
    }


