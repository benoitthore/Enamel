package com.benoitthore.visualentity

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.line.ELine
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform
import com.benoitthore.visualentity.style.EStyle

internal class ELineVisualEntityImpl(
    private val line: ELine,
    override var style: EStyle
) :
    ELineVisualEntity, ELine by line {
    override fun _copy(): ELineVisualEntity = line._copy().toVisualEntity(style)
    override val transform: ETransform = E.Transform()
}

interface ELineVisualEntity : ELine, VisualEntity {
    override fun _copy(): ELineVisualEntity
}

