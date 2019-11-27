package com.benoitthore.enamel.layout.android.extract.layout.shape

import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import com.benoitthore.enamel.geometry.figures.ESize
import com.benoitthore.enamel.layout.android.extract.layout.ECanvasLayout

abstract class Shape : ECanvasLayout() {

    protected val fillPaint: TextPaint = TextPaint().apply {
        style = Paint.Style.FILL
    }
    protected val strokePaint: TextPaint = TextPaint().apply {
        style = Paint.Style.STROKE
    }


}