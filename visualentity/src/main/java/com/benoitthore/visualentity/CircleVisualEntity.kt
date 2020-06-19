package com.benoitthore.visualentity

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.figures.circle.ECircleMutable
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransformMutable
import com.benoitthore.visualentity.style.EStyle

internal class CircleVisualEntityMutableImpl(
    private val circle: ECircleMutable,
    override var style: EStyle
) :
    CircleVisualEntityMutable, ECircleMutable by circle {
    override fun toMutable(): CircleVisualEntityMutable = circle.toMutable().toVisualEntity(style)
    override fun toImmutable(): CircleVisualEntity = toMutable()
    override val transform: ETransformMutable = E.TransformMutable()
}

interface CircleVisualEntity : ECircle, VisualEntity<ECircle, ECircleMutable> {
    override fun toMutable(): CircleVisualEntityMutable
    override fun toImmutable(): CircleVisualEntity
}

interface CircleVisualEntityMutable : CircleVisualEntity, ECircleMutable,
    VisualEntityMutable<ECircle, ECircleMutable>

fun ECircle.toVisualEntity(style: EStyle = EStyle()): CircleVisualEntity =
    toMutable().toVisualEntity(style)

fun ECircleMutable.toVisualEntity(style: EStyle = EStyle()): CircleVisualEntityMutable =
    CircleVisualEntityMutableImpl(this, style)
