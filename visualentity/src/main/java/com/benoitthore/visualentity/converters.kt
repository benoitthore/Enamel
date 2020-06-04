package com.benoitthore.visualentity

import com.benoitthore.enamel.geometry.figures.rect.*
import com.benoitthore.enamel.geometry.figures.circle.*
import com.benoitthore.enamel.geometry.interfaces.bounds.HasBounds
import com.benoitthore.visualentity.style.EStyle

// Circle
class CircleVisualEntity(circle: ECircle, style: EStyle) :
    ECircle by circle,
    VisualEntityImpl<ECircle, ECircleMutable>(circle, style) {
    override fun toMutable(): CircleVisualEntityMutable = super.toMutable().toVisualEntity(style)

    override fun toImmutable(): CircleVisualEntity = super.toImmutable().toVisualEntity(style)
}

class CircleVisualEntityMutable(circle: ECircleMutable, style: EStyle) :
    ECircleMutable by circle,
    VisualEntityMutableImpl<ECircle, ECircleMutable>(circle, style)

fun ECircle.toVisualEntity(style: EStyle = EStyle()) = CircleVisualEntity(this, style)
fun ECircleMutable.toVisualEntity(style: EStyle = EStyle()) = CircleVisualEntityMutable(this, style)