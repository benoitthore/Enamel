package com.benoitthore.enamel.android.prototypes

import android.graphics.Canvas

interface IPrototypingDrawer {
    fun draw(canvas: Canvas)
    fun onClick()
}