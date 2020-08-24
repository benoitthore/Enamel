package com.benoitthore.visualentity

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.figures.line.ELine
import com.benoitthore.enamel.geometry.figures.oval.EOval
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.functions.EShape
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransformable
import com.benoitthore.visualentity.style.EStyle
import com.benoitthore.visualentity.style.EStyleable
import com.benoitthore.visualentity.style.StyleBuilder
import com.benoitthore.visualentity.style.style

interface VisualEntity<T : EShape<T>> : ETransformable,
    EShape<T>, EStyleable

fun ERect.toVisualEntity(style: EStyle): ERectVisualEntity =
    RectVisualEntityImpl(this, style)

fun ERect.toVisualEntity(block: StyleBuilder.(ERect) -> Unit): ERectVisualEntity =
    RectVisualEntityImpl(this, E.style(this, block))

fun EOval.toVisualEntity(style: EStyle): EOvalVisualEntity =
    OvalVisualEntityImpl(this, style)

fun EOval.toVisualEntity(block: StyleBuilder.(EOval) -> Unit): EOvalVisualEntity =
    OvalVisualEntityImpl(this, E.style(this, block))

fun ELine.toVisualEntity(style: EStyle): ELineVisualEntity =
    ELineVisualEntityImpl(this, style)

fun ELine.toVisualEntity(block: StyleBuilder.(ELine) -> Unit): ELineVisualEntity =
    ELineVisualEntityImpl(this, E.style(this, block))

fun ECircle.toVisualEntity(style: EStyle): ECircleVisualEntity =
    CircleVisualEntityImpl(this, style)

fun ECircle.toVisualEntity(block: StyleBuilder.(ECircle) -> Unit): ECircleVisualEntity =
    CircleVisualEntityImpl(this, E.style(this, block))
