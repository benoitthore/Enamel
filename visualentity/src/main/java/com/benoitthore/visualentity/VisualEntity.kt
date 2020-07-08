package com.benoitthore.visualentity

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.figures.line.ELine
import com.benoitthore.enamel.geometry.figures.oval.EOval
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.interfaces.bounds.EShape
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransformable
import com.benoitthore.visualentity.style.EStyle
import com.benoitthore.visualentity.style.EStyleable
import com.benoitthore.visualentity.style.StyleBuilder
import com.benoitthore.visualentity.style.style

interface VisualEntity<T : EShape<T>> : ETransformable, EShape<T>, EStyleable

fun ERect.toVisualEntity(style: EStyle = EStyle()): RectVisualEntity =
    RectVisualEntityImpl(this, style)

fun ERect.toVisualEntity(block: StyleBuilder.(ERect) -> Unit): RectVisualEntity =
    RectVisualEntityImpl(this, E.style(this, block))

fun EOval.toVisualEntity(style: EStyle = EStyle()): OvalVisualEntity =
    OvalVisualEntityImpl(this, style)

fun EOval.toVisualEntity(block: StyleBuilder.(EOval) -> Unit): OvalVisualEntity =
    OvalVisualEntityImpl(this, E.style(this, block))

fun ELine.toVisualEntity(style: EStyle = EStyle()): LineVisualEntity =
    LineVisualEntityImpl(this, style)

fun ELine.toVisualEntity(block: StyleBuilder.(ELine) -> Unit): LineVisualEntity =
    LineVisualEntityImpl(this, E.style(this, block))

fun ECircle.toVisualEntity(style: EStyle = EStyle()): CircleVisualEntity =
    CircleVisualEntityImpl(this, style)

fun ECircle.toVisualEntity(block: StyleBuilder.(ECircle) -> Unit): CircleVisualEntity =
    CircleVisualEntityImpl(this, E.style(this, block))
