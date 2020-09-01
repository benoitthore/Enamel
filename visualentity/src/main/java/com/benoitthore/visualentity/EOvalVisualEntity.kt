package com.benoitthore.visualentity

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.oval.EOval
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform
import com.benoitthore.visualentity.style.EStyle

internal class OvalVisualEntityImpl(
    private val Oval: EOval,
    override var style: EStyle
) :
    EOvalVisualEntity, EOval by Oval {
    override fun _copy(): EOvalVisualEntity = Oval._copy().toVisualEntity(style)
    override val transform: ETransform = E.Transform()
}

interface EOvalVisualEntity : EOval, VisualEntity {
    override fun _copy(): EOvalVisualEntity
}
