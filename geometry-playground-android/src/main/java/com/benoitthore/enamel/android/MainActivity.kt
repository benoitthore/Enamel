package com.benoitthore.enamel.android

import android.content.Context
import android.content.res.Configuration
import android.graphics.*
import android.graphics.Color.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextPaint
import androidx.appcompat.app.AppCompatActivity
import com.benoitthore.enamel.android.demo.canvasView
import com.benoitthore.enamel.geometry.AllocationTracker
import com.benoitthore.enamel.geometry.figures.*
import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.geometry.layout.dsl.padded
import com.benoitthore.enamel.geometry.layout.flowed
import com.benoitthore.enamel.geometry.layout.refs.getAllChildren
import com.benoitthore.enamel.layout.android.dp
import com.benoitthore.enamel.layout.android.extract.set
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import com.squareup.picasso.Target
import kotlinx.coroutines.*
import kotlin.coroutines.resume
import android.graphics.Bitmap
import androidx.annotation.IntegerRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.benoitthore.enamel.R
import com.benoitthore.enamel.geometry.primitives.EPointMutable
import com.benoitthore.enamel.geometry.primitives.div
import com.benoitthore.enamel.layout.android.extract.GeometryPool
import com.benoitthore.enamel.layout.android.extract.layout.ECanvasLayout
import com.benoitthore.enamel.layout.android.extract.layout.EImageLayout
import com.benoitthore.enamel.layout.android.extract.layout.EWordLayout
import com.benoitthore.enamel.layout.android.extract.singleTouch


val Context.isLandscape get() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

val loremIpsum =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalScope.launch(Dispatchers.Main) {

            AllocationTracker.debugAllocations = false

//            val textPaint = TextPaint().apply {
//                textSize = 60f
//                color = WHITE
//            }
//            val wordLayout = loremIpsum.toWordLayout(textPaint).padded(32.dp)
//            val imageUrl =
//                "https://upload.wikimedia.org/wikipedia/en/b/be/Locator_Grid.png".downloadImage()

            val drawable =
                ContextCompat.getDrawable(applicationContext, R.drawable.ic_launcher_foreground)!!
            val image: ELayout = EImageLayout(drawable)

            val pool = GeometryPool(20)

            setContentView(canvasView(init = {
//                singleTouch { _, current, previous ->
//                    current?.let {
//                        if (lastTouch == null) {
//                            lastTouch = EPointMutable()
//                        }
//                        lastTouch!!.set(it)
//                    }
//                    invalidate()
//                    true
//                }
            }) { canvas ->

                canvas.drawColor(DKGRAY)

                val layout = image.padded(32.dp)


                layout.arrange(frame)

                layout.getAllChildren().forEach {
                    (it as? ECanvasLayout)?.let { _ ->
                        it.draw(canvas)
                    }
                }
                invalidate()
            })
        }

    }
}

// EXTRACT

// TODO Make 1 layout per line instead of 1 per word
fun CharSequence.toWordLayout(paint: Paint) =
    split(" ")
        .filter { it.isNotBlank() }
        .map { EWordLayout(paint, it) }
        .flowed(lineSpacing = 8.dp, childSpacing = 16.dp)


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


