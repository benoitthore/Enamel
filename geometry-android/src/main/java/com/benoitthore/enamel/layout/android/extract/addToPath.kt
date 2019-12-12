package com.benoitthore.enamel.layout.android.extract

import android.graphics.Path
import com.benoitthore.enamel.geometry.figures.ERect

operator fun Path.plusAssign(rect: ERect) {
    rect.addToPath(this)
}

fun ERect.addToPath(path: Path, dir: Path.Direction = Path.Direction.CW) {
    path.addRect(left, top, right, bottom,dir)
}