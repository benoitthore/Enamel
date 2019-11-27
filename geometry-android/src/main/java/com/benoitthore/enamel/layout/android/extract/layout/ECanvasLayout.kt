package com.benoitthore.enamel.layout.android.extract.layout

import android.graphics.Canvas
import android.view.View
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.geometry.layout.refs.getAllChildren
import com.benoitthore.enamel.layout.android.extract.OnClickTouchListener

typealias ECanvasLayoutClickListener = (ECanvasLayout) -> Unit

fun <T : ECanvasLayout> T.onClick(listener: ECanvasLayoutClickListener) = apply {
    clickListeners += listener
}

abstract class ECanvasLayout : ELayout {

    override val children: List<ELayout> get() = emptyList()
    internal val touchListener =
        OnClickTouchListener(getFrame = { frame }, callback = ::notifyClickListeners)

    internal val clickListeners: MutableList<ECanvasLayoutClickListener> = mutableListOf()

    var viewParent: View? = null

    private val _frame = ERectMutable()
    val frame: ERect get() = _frame


    private fun notifyClickListeners() {
        clickListeners.forEach { it.invoke(this) }
    }


    override fun arrange(frame: ERect) {
        _frame.set(frame)
    }

    abstract fun draw(canvas: Canvas)
}

fun ECanvasLayout.invalidate() {
    viewParent?.invalidate()
}
