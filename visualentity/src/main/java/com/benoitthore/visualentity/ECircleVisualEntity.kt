package com.benoitthore.visualentity

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform
import com.benoitthore.visualentity.style.EStyle

internal class CircleVisualEntityImpl(
    private val circle: ECircle,
    override var style: EStyle
) :
    ECircleVisualEntity, ECircle by circle {
    override fun _copy(): ECircleVisualEntity = circle._copy().toVisualEntity(style)
    override val transform: ETransform = E.Transform()
}

interface ECircleVisualEntity : ECircle, VisualEntity {
    override fun _copy(): ECircleVisualEntity
}


