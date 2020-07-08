package com.benoitthore.visualentity

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.oval.EOval
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform
import com.benoitthore.visualentity.style.EStyle

internal class OvalVisualEntityImpl(
    private val Oval: EOval,
    override var style: EStyle
) :
    OvalVisualEntity, EOval by Oval {
    override fun copy(): OvalVisualEntity = Oval.copy().toVisualEntity(style)
    override val transform: ETransform = E.Transform()
}

interface OvalVisualEntity : EOval, VisualEntity<EOval> {
    override fun copy(): OvalVisualEntity
}
