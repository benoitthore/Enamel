package com.benoitthore.enamel.android

import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color.*
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextPaint
import androidx.appcompat.app.AppCompatActivity
import com.benoitthore.enamel.android.demo.canvasView
import com.benoitthore.enamel.geometry.AllocationTracker
import com.benoitthore.enamel.geometry.alignement.EAlignment
import com.benoitthore.enamel.geometry.alignement.EAlignment.*
import com.benoitthore.enamel.geometry.figures.*
import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.geometry.layout.dsl.filling
import com.benoitthore.enamel.geometry.layout.dsl.justified
import com.benoitthore.enamel.geometry.layout.dsl.padded
import com.benoitthore.enamel.geometry.layout.dsl.stackedBottomRight
import com.benoitthore.enamel.geometry.layout.flowed
import com.benoitthore.enamel.geometry.layout.refs.getAllChildren
import com.benoitthore.enamel.layout.android.dp
import com.benoitthore.enamel.layout.android.extract.drawRect
import com.benoitthore.enamel.layout.android.extract.set
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import com.squareup.picasso.Target
import kotlinx.coroutines.*
import kotlin.coroutines.resume

val Context.isLandscape get() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

val loremIpsum =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AllocationTracker.debugAllocations = false
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
            //            val image = List(3) {
//                "https://s3.amazonaws.com/cdn-origin-etr.akc.org/wp-content/uploads/2017/11/12234558/Chinook-On-White-03.jpg"
//                    .imageLayout()
//            }
//                .justified(bottomCenter)
//                .padded(16.dp)

            val image: ELayout =
                "https://s3.amazonaws.com/cdn-origin-etr.akc.org/wp-content/uploads/2017/11/12234558/Chinook-On-White-03.jpg".imageLayout()
            setContentView(canvasView { canvas ->

                canvas.drawColor(DKGRAY)

                val layout = image.padded(32.dp)

                layout.arrange(frame)

                layout.getAllChildren().forEach {
                    (it as? ECanvasLayout)?.let { _ ->
                        it.draw(canvas)
//                        canvas.drawRect(it.frame, paint)
                    }
                }

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
    withContext(Dispatchers.Main) {
        suspendCancellableCoroutine<Bitmap> { cont ->
            Picasso.get()
                .load(this@downloadImage)
                .apply(block)
                .into(object : Target {
                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

                    }

                    override fun onBitmapFailed(e: Exception, errorDrawable: Drawable?) {
                        cont.cancel(e)
                    }

                    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom?) {
                        cont.resume(bitmap)
                    }
                })
        }
    }


suspend fun String.imageLayout(paint: Paint = Paint()) = EImageLayout(downloadImage(), paint)

fun CharSequence.toWordLayout(paint: Paint) =
    split(" ")
        .filter { it.isNotBlank() }
        .map { EWordLayout(paint, it) }
        .flowed(lineSpacing = 8.dp, childSpacing = 16.dp)

abstract class ECanvasLayout : ELayout {
    override val children: List<ELayout> get() = emptyList()

    private val _frame = ERectMutable()
    val frame: ERect get() = _frame

    override fun arrange(frame: ERect) {
        _frame.set(frame)
    }

    abstract fun draw(canvas: Canvas)


}

class EImageLayout(
    var image: Bitmap,
    val paint: Paint
) : ECanvasLayout() {

    override fun size(toFit: ESize): ESize = image.width size image.height

    private val srcRectBuffer = Rect()
    private val dstRectBuffer = Rect()
    override fun draw(canvas: Canvas) {
        srcRectBuffer.set(0, 0, image.width, image.height)
        dstRectBuffer.set(frame)
        canvas.drawBitmap(image, srcRectBuffer, dstRectBuffer, paint)
    }
}

class EWordLayout(
    val paint: Paint,
    text: CharSequence = ""
) : ECanvasLayout() {

    var text: CharSequence = text
        set(value) {
            field = value
            stringText = value.toString()
        }

    // Avoid allocation
    private var stringText = this.text.toString()

    override val children: List<ELayout> = emptyList()


    private val bufferSize = ESizeMutable()

    override fun size(toFit: ESize): ESize = bufferSize.set(
        paint.measureText(stringText),
        paint.textSize + paint.descent()
    )


    override fun draw(canvas: Canvas) {
        canvas.drawText(stringText, frame.x, frame.y - paint.ascent(), paint)
    }

}