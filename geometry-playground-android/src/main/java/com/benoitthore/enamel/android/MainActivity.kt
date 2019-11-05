package com.benoitthore.enamel.android

import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color.*
import android.graphics.Paint
import android.os.Bundle
import android.text.TextPaint
import androidx.appcompat.app.AppCompatActivity
import com.benoitthore.enamel.android.demo.canvasView
import com.benoitthore.enamel.geometry.AllocationTracker
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.geometry.figures.ESize
import com.benoitthore.enamel.geometry.figures.ESizeMutable
import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.geometry.layout.dsl.padded
import com.benoitthore.enamel.geometry.layout.flowed
import com.benoitthore.enamel.layout.android.dp
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

val Context.isLandscape get() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

val loremIpsum =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum"

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


        val wordLayout = loremIpsum.toWordLayout(textPaint).padded(32.dp)

        GlobalScope.launch(Dispatchers.Main) {
            val image =
                "https://s3.amazonaws.com/cdn-origin-etr.akc.org/wp-content/uploads/2017/11/12234558/Chinook-On-White-03.jpg"
                    .downloadImage()

            setContentView(canvasView { canvas ->

                canvas.drawColor(DKGRAY)

                canvas.drawBitmap(image, 0f, 0f, paint)

//                wordLayout.arrange(frame)
//                wordLayout.getAllChildren().forEach { layout ->
//                    (layout as? EWordLayout)?.let {
//                        canvas.drawRect(it.frame, paint)
//                        it.draw(canvas)
//                    }
//                }

            })
        }

    }
}

// EXTRACT

suspend inline fun String.downloadImage(crossinline block: RequestCreator.() -> Unit = {}): Bitmap =
    withContext(Dispatchers.IO) {
        Picasso.get()
            .load(this@downloadImage)
            .apply(block)
            .get()
    }


//suspend fun String.downloadImage(
//    block: RequestCreator.() -> Unit = {}
//): Bitmap = suspendCoroutine { cont ->
//    GlobalScope.launch(Dispatchers.Main) {
//        Picasso.get()
//            .load(this@downloadImage)
//            .apply(block)
//            .into(object : Target {
//                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
//                    Log.d("Download", "Starting")
//                }
//
//                override fun onBitmapFailed(e: Exception, errorDrawable: Drawable?) {
//                    cont.resumeWith(Result.failure(e))
//                }
//
//                override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom?) {
//                    cont.resumeWith(Result.success(bitmap))
//                }
//            })
//
//
//    }
//}

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