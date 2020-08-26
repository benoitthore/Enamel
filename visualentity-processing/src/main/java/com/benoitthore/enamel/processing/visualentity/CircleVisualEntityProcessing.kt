package com.benoitthore.enamel.processing.visualentity

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.functions.copy
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform
import com.benoitthore.enamel.processing.VisualEntityDrawer
import com.benoitthore.enamel.processing.pushPop
import com.benoitthore.visualentity.*
import com.benoitthore.visualentity.style.EStyleable
import processing.core.PConstants
import java.lang.Exception

fun ECircleVisualEntity.toProcessing(): CircleVisualEntityProcessing {
    val copy = copy()
    return CircleVisualEntityProcessingImpl(
        copy,
        VisualEntityDrawer(
            style
        ) { applet ->
            applet.pushPop {
                copy.apply {
                    ellipseMode(PConstants.CENTER)
                    ellipse(centerX, centerY, radius * 2, radius * 2)
                }
            }
        })
}

interface CircleVisualEntityProcessing : ProcessingVisualEntity<ECircle>,
    ECircleVisualEntity {
    override fun _copy(): CircleVisualEntityProcessing
}


internal class CircleVisualEntityProcessingImpl(
    private val Circle: ECircle,
    override val drawer: VisualEntityDrawer
) :
    CircleVisualEntityProcessing,
    ECircle by Circle,
    EStyleable by drawer {

    override val transform: ETransform = E.Transform()
    override fun _copy(): CircleVisualEntityProcessing =
        Circle.copy().toVisualEntity(style).toProcessing()
}