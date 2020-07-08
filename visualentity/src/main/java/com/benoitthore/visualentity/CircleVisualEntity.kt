package com.benoitthore.visualentity

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform
import com.benoitthore.visualentity.style.EStyle
import com.benoitthore.visualentity.style.StyleBuilder
import com.benoitthore.visualentity.style.style

internal class CircleVisualEntityImpl(
    private val circle: ECircle,
    override var style: EStyle
) :
    CircleVisualEntity, ECircle by circle {
    override fun copy(): CircleVisualEntity = circle.copy().toVisualEntity(style)
    override val transform: ETransform = E.Transform()
}

interface CircleVisualEntity : ECircle, VisualEntity<ECircle> {
    override fun copy(): CircleVisualEntity
}

fun ECircle.toVisualEntity(style: EStyle = EStyle()): CircleVisualEntity =
    CircleVisualEntityImpl(this, style)

fun ECircle.toVisualEntity(block: StyleBuilder.() -> Unit): CircleVisualEntity =
    CircleVisualEntityImpl(this, E.style(block))
