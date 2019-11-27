package com.benoitthore.enamel.layout.android.extract.layout.shape

import android.text.TextPaint
import com.benoitthore.enamel.layout.android.extract.layout.ECanvasLayout

abstract class Shape : ECanvasLayout() {
    protected val paint: TextPaint = TextPaint()
}