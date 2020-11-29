package com.benoitthore.enamel.processing.visualentity

import com.benoitthore.enamel.geometry.builders.*
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform
import com.benoitthore.enamel.processing.VisualEntityDrawer
import com.benoitthore.enamel.processing.pushPop
import com.benoitthore.visualentity.*
import com.benoitthore.visualentity.style.EStyleable
import processing.core.PConstants


fun ERectVisualEntity.toProcessing(): RectVisualEntityProcessing {
    val copy = _copy()
    return RectVisualEntityProcessingImpl(
        copy,
        VisualEntityDrawer(
            style
        ) { applet ->
            applet.pushPop {
                copy.apply {
                    rectMode(PConstants.CORNERS)
                    rect(left, top, right, bottom)
                }
            }
        })
}

interface RectVisualEntityProcessing : ProcessingVisualEntity,
    ERectVisualEntity {
    override fun _copy(): RectVisualEntityProcessing
}


internal class RectVisualEntityProcessingImpl(
    private val rect: ERect,
    override val drawer: VisualEntityDrawer
) :
    RectVisualEntityProcessing,
    ERect by rect,
    EStyleable by drawer {

    override val transform: ETransform = Transform()
    override fun _copy(): RectVisualEntityProcessing =
        rect._copy().toVisualEntity(style).toProcessing()
}