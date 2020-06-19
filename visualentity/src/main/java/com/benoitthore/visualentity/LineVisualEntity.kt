package com.benoitthore.visualentity

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.line.ELine
import com.benoitthore.enamel.geometry.figures.line.ELineMutable
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransformMutable
import com.benoitthore.visualentity.style.EStyle

internal class LineVisualEntityMutableImpl(
    private val line: ELineMutable,
    override var style: EStyle
) :
    LineVisualEntityMutable, ELineMutable by line {
    override fun toMutable(): LineVisualEntityMutable = line.toMutable().toVisualEntity(style)
    override fun toImmutable(): LineVisualEntity = toMutable()
    override val transform: ETransformMutable = E.TransformMutable()
}

interface LineVisualEntity : ELine, VisualEntity<ELine, ELineMutable> {
    override fun toMutable(): LineVisualEntityMutable
    override fun toImmutable(): LineVisualEntity
}

interface LineVisualEntityMutable : LineVisualEntity, ELineMutable,
    VisualEntityMutable<ELine, ELineMutable>

fun ELine.toVisualEntity(style: EStyle = EStyle()): LineVisualEntity =
    toMutable().toVisualEntity(style)

fun ELineMutable.toVisualEntity(style: EStyle = EStyle()): LineVisualEntityMutable =
    LineVisualEntityMutableImpl(this, style)
