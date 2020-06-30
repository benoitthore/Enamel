package com.benoitthore.visualentity

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform
import com.benoitthore.visualentity.style.EStyle

internal class CircleVisualEntityImpl(
    private val circle: ECircle,
    override var style: EStyle
) :
    CircleVisualEntity, ECircle by circle {
    override fun copy(): CircleVisualEntity = circle.copy().toVisualEntity(style)
    override val transform: ETransform = E.TransformMutable()
}

interface CircleVisualEntity : ECircle, VisualEntity<ECircle> {
    override fun copy(): CircleVisualEntity
}

fun ECircle.toVisualEntity(style: EStyle = EStyle()): CircleVisualEntity =
    CircleVisualEntityImpl(this, style)
