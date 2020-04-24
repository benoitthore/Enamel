package com.benoitthore.enamel.layout.android.visualentity

import android.graphics.Canvas
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.layout.android.visualentity.style.EStyleable
import com.benoitthore.enamel.layout.android.visualentity.style.VisualEntityDrawer

abstract class DrawableVisualEntity private constructor(protected val drawer: VisualEntityDrawer) :
    VisualEntity by VisualEntity.Impl(),
    EStyleable by drawer,
    ELayout {

    constructor() : this(VisualEntityDrawer())

    private val _frame: ERectMutable =
        ERectMutable()
    val frame: ERect get() = _frame

    override val children: List<ELayout> get() = emptyList()

    override fun arrange(frame: ERect) {
        this._frame.set(frame)
        onFrameUpdated()
    }

    protected abstract fun onFrameUpdated()

    abstract fun draw(canvas: Canvas)
}