package com.benoitthore.visualentity

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.oval.EOval
import com.benoitthore.enamel.geometry.figures.oval.EOvalMutable
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransformMutable
import com.benoitthore.visualentity.style.EStyle

internal class OvalVisualEntityMutableImpl(
    private val oval: EOvalMutable,
    override var style: EStyle
) :
    OvalVisualEntityMutable, EOvalMutable by oval {
    override fun toMutable(): OvalVisualEntityMutable = oval.toMutable().toVisualEntity(style)
    override fun toImmutable(): OvalVisualEntity = toMutable()
    override val transform: ETransformMutable = E.TransformMutable()
}

interface OvalVisualEntity : EOval, VisualEntity<EOval, EOvalMutable> {
    override fun toMutable(): OvalVisualEntityMutable
    override fun toImmutable(): OvalVisualEntity
}

interface OvalVisualEntityMutable : OvalVisualEntity, EOvalMutable,
    VisualEntityMutable<EOval, EOvalMutable>

fun EOval.toVisualEntity(style: EStyle = EStyle()): OvalVisualEntity =
    toMutable().toVisualEntity(style)

fun EOvalMutable.toVisualEntity(style: EStyle = EStyle()): OvalVisualEntityMutable =
    OvalVisualEntityMutableImpl(this, style)
