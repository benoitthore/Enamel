package com.benoitthore.enamel.layout.android.visualentity

import android.graphics.Canvas
import com.benoitthore.enamel.geometry.primitives.ETransformation
import com.benoitthore.enamel.layout.android.visualentity.style.EStyleable
import com.benoitthore.enamel.layout.android.visualentity.style.VisualEntityDrawer

abstract class BaseVisualEntity private constructor(protected val drawer: VisualEntityDrawer) :
    VisualEntity,
    EStyleable by drawer {

    constructor() : this(VisualEntityDrawer())

    override val transformation: ETransformation = ETransformation.Impl()

    override fun draw(canvas: Canvas) {
        canvas.withSave {
            canvas.withTransformation(transformation) {
                onDraw(canvas)
            }
        }
    }

    abstract fun onDraw(canvas: Canvas)

}