package com.benoitthore.enamel.layout.android

import android.graphics.Rect
import android.graphics.RectF
import com.benoitthore.enamel.geometry.figures.ERect


fun Rect.set(rect: ERect) = apply {
    left = rect.left.toInt()
    top = rect.top.toInt()
    right = rect.right.toInt()
    bottom = rect.bottom.toInt()
}

fun RectF.set(rect: ERect) = apply {
    left = rect.left
    top = rect.top
    right = rect.right
    bottom = rect.bottom
}