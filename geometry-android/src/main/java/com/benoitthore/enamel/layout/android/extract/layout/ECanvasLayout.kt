package com.benoitthore.enamel.layout.android.extract.layout

import android.graphics.Canvas
import android.view.View
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.geometry.layout.refs.getAllChildren

abstract class ECanvasLayout : ELayout {

    override val children: List<ELayout> get() = emptyList()

    var viewParent: View? = null

    private val _frame = ERectMutable()
    val frame: ERect get() = _frame

    override fun arrange(frame: ERect) {
        _frame.set(frame)
    }

    abstract fun draw(canvas: Canvas)
}

fun ECanvasLayout.invalidate() {
    viewParent?.invalidate()
}

// TODO Allocation
fun ELayout.draw(canvas: Canvas) {
    if (this is ECanvasLayout) {
        draw(canvas)
    }
    if (children.isEmpty()) {
        return
    }
    getAllChildren().asSequence()
        .filterIsInstance<ECanvasLayout>()
        .forEach {
            it.draw(canvas)
        }
}