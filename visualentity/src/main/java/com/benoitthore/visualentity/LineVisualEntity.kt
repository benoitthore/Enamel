package com.benoitthore.visualentity

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.line.ELine
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform
import com.benoitthore.visualentity.style.EStyle

internal class LineVisualEntityImpl(
    private val line: ELine,
    override var style: EStyle
) :
    LineVisualEntity, ELine by line {
    override fun copy(): LineVisualEntity = line.copy().toVisualEntity(style)
    override val transform: ETransform = E.Transform()
}

interface LineVisualEntity : ELine, VisualEntity<ELine> {
    override fun copy(): LineVisualEntity
}

