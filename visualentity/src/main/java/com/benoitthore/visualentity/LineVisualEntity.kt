package com.benoitthore.visualentity

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.line.ELine
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransformMutable
import com.benoitthore.visualentity.style.EStyle

internal class LineVisualEntityMutableImpl(
    private val line: ELine,
    override var style: EStyle
) :
    LineVisualEntityMutable, ELine by line {
    override fun toMutable(): LineVisualEntityMutable = line.toMutable().toVisualEntity(style)
    override fun toImmutable(): LineVisualEntity = toMutable()
    override val transform: ETransformMutable = E.TransformMutable()
}

interface LineVisualEntity : ELine, VisualEntity<ELine, ELine> {
    override fun toMutable(): LineVisualEntityMutable
    override fun toImmutable(): LineVisualEntity
}

interface LineVisualEntityMutable : LineVisualEntity,
    ELine,
    VisualEntityMutable<ELine, ELine>

fun ELine.toVisualEntity(style: EStyle = EStyle()): LineVisualEntity =
    toMutable().toVisualEntity(style)

fun ELine.toVisualEntity(style: EStyle = EStyle()): LineVisualEntityMutable =
    LineVisualEntityMutableImpl(this, style)
