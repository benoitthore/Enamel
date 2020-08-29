package com.benoitthore.visualentity.generic

import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.figures.line.ELine
import com.benoitthore.enamel.geometry.figures.oval.EOval
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.functions.EShape
import com.benoitthore.visualentity.*
import com.benoitthore.visualentity.style.EStyle
import com.benoitthore.visualentity.style.StyleBuilder
import java.lang.Exception
import com.benoitthore.visualentity.toVisualEntity

fun EShape<*>._toVisualEntity(block: StyleBuilder.(EShape<*>) -> Unit): VisualEntity<*> =
    when (this) {
        is ECircle -> toVisualEntity { block(it) }
        is ERect -> toVisualEntity { block(it) }
        is ELine -> toVisualEntity { block(it) }
        is EOval -> toVisualEntity { block(it) }
        else -> throw Exception("Invalid $this")
    }

fun EShape<*>._toVisualEntity(style: EStyle): VisualEntity<*> =
    when (this) {
        is ECircle -> toVisualEntity(style)
        is ERect -> toVisualEntity(style)
        is ELine -> toVisualEntity(style)
        is EOval -> toVisualEntity(style)
        else -> throw Exception("Invalid $this")
    }