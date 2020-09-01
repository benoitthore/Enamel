package com.benoitthore.enamel.processing.visualentity

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.line.ELine
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform
import com.benoitthore.enamel.processing.VisualEntityDrawer
import com.benoitthore.enamel.processing.pushPop
import com.benoitthore.visualentity.*
import com.benoitthore.visualentity.style.EStyleable


fun ELineVisualEntity.toProcessing(): ELineVisualEntityProcessing {
    val copy = _copy()
    return ELineVisualEntityProcessingImpl(
        copy,
        VisualEntityDrawer(
            style
        ) { applet ->
            applet.pushPop {
                copy.apply {
                    line(start.x, start.y, end.x, end.y)
                }
            }
        })
}

interface ELineVisualEntityProcessing : ProcessingVisualEntity,
    ELineVisualEntity {
    override fun _copy(): ELineVisualEntityProcessing
}


internal class ELineVisualEntityProcessingImpl(
    private val line: ELine,
    override val drawer: VisualEntityDrawer
) :
    ELineVisualEntityProcessing,
    ELine by line,
    EStyleable by drawer {

    override val transform: ETransform = E.Transform()
    override fun _copy(): ELineVisualEntityProcessing =
        line._copy().toVisualEntity(style).toProcessing()
}