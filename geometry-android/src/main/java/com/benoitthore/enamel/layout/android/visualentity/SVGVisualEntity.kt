package com.benoitthore.enamel.layout.android.visualentity

import android.graphics.Canvas
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.primitives.ETransform
import com.benoitthore.enamel.geometry.primitives.ETransformMutable
import com.benoitthore.enamel.geometry.svg.ESVG
import com.benoitthore.enamel.layout.android.visualentity.style.EStyleable
import com.benoitthore.enamel.layout.android.visualentity.style.VisualEntityDrawer

interface ISVGVisualEntity : VisualEntity, ESVG, EStyleable

abstract class SVGVisualEntity private constructor(protected val drawer: VisualEntityDrawer) :
    ISVGVisualEntity, EStyleable by drawer {

    constructor() : this(VisualEntityDrawer())

    override val transform: ETransformMutable = E.ETransformMutable()

    override fun draw(canvas: Canvas) {
        canvas.withSave {
            canvas.withTransformation(transform) {
                onDraw(canvas)
            }
        }
    }

    abstract fun onDraw(canvas: Canvas)

}