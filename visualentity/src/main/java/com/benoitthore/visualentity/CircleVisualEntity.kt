package com.benoitthore.visualentity

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransformMutable
import com.benoitthore.visualentity.style.EStyle

internal class CircleVisualEntityMutableImpl(
    private val circle: ECircle,
    override var style: EStyle
) :
    CircleVisualEntityMutable, ECircle by circle {
    override fun toMutable(): CircleVisualEntityMutable = circle.toMutable().toVisualEntity(style)
    override fun toImmutable(): CircleVisualEntity = toMutable()
    override val transform: ETransformMutable = E.TransformMutable()
}

interface CircleVisualEntity : ECircle, VisualEntity<ECircle, ECircle> {
    override fun toMutable(): CircleVisualEntityMutable
    override fun toImmutable(): CircleVisualEntity
}

interface CircleVisualEntityMutable : CircleVisualEntity, ECircle,
    VisualEntityMutable<ECircle, ECircle>

fun ECircle.toVisualEntity(style: EStyle = EStyle()): CircleVisualEntity =
    toMutable().toVisualEntity(style)

fun ECircle.toVisualEntity(style: EStyle = EStyle()): CircleVisualEntityMutable =
    CircleVisualEntityMutableImpl(this, style)
