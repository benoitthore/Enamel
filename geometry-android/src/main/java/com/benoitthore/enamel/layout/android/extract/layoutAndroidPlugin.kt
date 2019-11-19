package com.benoitthore.enamel.layout.android.extract

import android.graphics.*
import com.benoitthore.enamel.geometry.figures.ECircle
import com.benoitthore.enamel.geometry.figures.ELine
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.geometry.layout.ELayoutLeaf
import com.benoitthore.enamel.geometry.layout.refs.getAllChildren
import com.benoitthore.enamel.geometry.layout.refs.getAllChildrenWithType
import com.benoitthore.enamel.geometry.primitives.EPoint
import com.benoitthore.enamel.layout.android.extract.layout.ECanvasLayout

private val leavesPaint = Paint().apply { style = Paint.Style.FILL }
fun Canvas.drawLeaf(leaf: ELayoutLeaf, paint: Paint = leavesPaint) {
    synchronized(paint) {
        paint.color = leaf.color
        drawRect(leaf.frame, paint)
    }
}

fun Canvas.drawLayout(rootLayout: ELayout) {
    if (rootLayout is ECanvasLayout) {
        rootLayout.draw(this)
    }
    if (rootLayout.children.isEmpty()) {
        return
    }
    rootLayout.getAllChildren().forEach {
        if (it is ECanvasLayout)
            it.draw(this)
        else if (it is ELayoutLeaf)
            drawLeaf(it)
    }
}