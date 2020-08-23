package com.benoitthore.enamel.processing.visualentity

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.line.ELine
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform
import com.benoitthore.enamel.processing.VisualEntityDrawer
import com.benoitthore.enamel.processing.pushPop
import com.benoitthore.visualentity.*
import com.benoitthore.visualentity.style.EStyleable
import processing.core.PConstants


fun LineVisualEntity.toProcessing(): LineVisualEntityProcessing {
    val copy = copy()
    return LineVisualEntityProcessingImpl(
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

interface LineVisualEntityProcessing : ProcessingVisualEntity<ELine>,
    LineVisualEntity {
    override fun copy(): LineVisualEntityProcessing
}


internal class LineVisualEntityProcessingImpl(
    private val line: ELine,
    override val drawer: VisualEntityDrawer
) :
    LineVisualEntityProcessing,
    ELine by line,
    EStyleable by drawer {

    override val transform: ETransform = E.Transform()
    override fun copy(): LineVisualEntityProcessing =
        line.copy().toVisualEntity(style).toProcessing()
}