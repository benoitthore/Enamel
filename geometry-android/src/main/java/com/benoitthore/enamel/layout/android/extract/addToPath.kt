package com.benoitthore.enamel.layout.android.extract

import android.graphics.Path
import com.benoitthore.enamel.geometry.figures.ECircle
import com.benoitthore.enamel.geometry.figures.ELine
import com.benoitthore.enamel.geometry.figures.ERect

operator fun Path.plusAssign(rect: ERect) {
    rect.addToPath(this)
}

operator fun Path.plusAssign(circle: ECircle) {
    circle.addToPath(this)
}

operator fun Path.plusAssign(line: ELine) {
    line.addToPath(this)
}

fun ERect.addToPath(path: Path, dir: Path.Direction = Path.Direction.CW) {
    path.addRect(left, top, right, bottom, dir)
}

fun ECircle.addToPath(path: Path, dir: Path.Direction = Path.Direction.CW) {
    path.addCircle(x, y, radius, dir)
}

fun ELine.addToPath(path: Path, dir: Path.Direction = Path.Direction.CW) {
    path.moveTo(start.x, start.y)
    path.lineTo(end.x, end.y)
}