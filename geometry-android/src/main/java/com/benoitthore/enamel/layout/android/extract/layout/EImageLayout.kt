package com.benoitthore.enamel.layout.android.extract.layout

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.benoitthore.enamel.geometry.figures.ESize
import com.benoitthore.enamel.geometry.figures.size
import com.benoitthore.enamel.layout.android.extract.set

class EImageLayout(
    var image: Bitmap,
    val paint: Paint = Paint()
) : ECanvasLayout() {

    constructor(drawable: Drawable,paint: Paint = Paint()) : this(drawable.toBitmap(),paint)

    override fun size(toFit: ESize): ESize = image.width size image.height

    private val srcRectBuffer = Rect()
    private val dstRectBuffer = Rect()
    override fun draw(canvas: Canvas) {
        srcRectBuffer.set(0, 0, image.width, image.height)
        dstRectBuffer.set(frame)
        canvas.drawBitmap(image, srcRectBuffer, dstRectBuffer, paint)
    }
}



private fun Drawable.toBitmap(): Bitmap {
    val bitmap: Bitmap
    val drawable = this

    if (drawable is BitmapDrawable) {
        if (drawable.bitmap != null) {
            return drawable.bitmap
        }
    }

    bitmap = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
        // Single color bitmap will be created of 1x1 pixel
        Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    } else {
        Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
    }

    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}
