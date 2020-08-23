package com.benoitthore.enamel.processing.visualentity

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.oval.EOval
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform
import com.benoitthore.enamel.processing.VisualEntityDrawer
import com.benoitthore.enamel.processing.pushPop
import com.benoitthore.visualentity.*
import com.benoitthore.visualentity.style.EStyleable
import processing.core.PConstants


fun EOvalVisualEntity.toProcessing(): OvalVisualEntityProcessing {
    val copy = copy()
    return OvalVisualEntityProcessingImpl(
        copy,
        VisualEntityDrawer(
            style
        ) { applet ->
            applet.pushPop {
                copy.apply {
                    ellipseMode(PConstants.CENTER)
                    ellipse(centerX, centerY, rx * 2, ry * 2)
                }
            }
        })
}

interface OvalVisualEntityProcessing : ProcessingVisualEntity<EOval>,
    EOvalVisualEntity {
    override fun copy(): OvalVisualEntityProcessing
}


internal class OvalVisualEntityProcessingImpl(
    private val Oval: EOval,
    override val drawer: VisualEntityDrawer
) :
    OvalVisualEntityProcessing,
    EOval by Oval,
    EStyleable by drawer {

    override val transform: ETransform = E.Transform()
    override fun copy(): OvalVisualEntityProcessing =
        Oval.copy().toVisualEntity(style).toProcessing()
}