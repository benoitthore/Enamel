package com.benoitthore.visualentity

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.oval.EOval
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform
import com.benoitthore.visualentity.style.EStyle
import com.benoitthore.visualentity.style.StyleBuilder
import com.benoitthore.visualentity.style.style


internal class OvalVisualEntityImpl(
    private val oval: EOval,
    override var style: EStyle
) :
    OvalVisualEntity, EOval by oval {
    override fun copy(): OvalVisualEntity = oval.copy().toVisualEntity(style)
    override val transform: ETransform = E.Transform()
}

interface OvalVisualEntity : EOval, VisualEntity<EOval> {
    override fun copy(): OvalVisualEntity
}

fun EOval.toVisualEntity(style: EStyle = EStyle()): OvalVisualEntity =
    OvalVisualEntityImpl(this, style)

fun EOval.toVisualEntity(styleDef: () -> EStyle = { EStyle() }): OvalVisualEntity =
    OvalVisualEntityImpl(this, styleDef())

fun EOval.toVisualEntity(block: StyleBuilder.() -> Unit): OvalVisualEntity =
    OvalVisualEntityImpl(this, E.style(block))
