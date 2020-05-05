package com.benoitthore.visualentity

import android.graphics.Canvas
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransformMutable
import com.benoitthore.enamel.geometry.svg.ESVG
import com.benoitthore.visualentity.style.*

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