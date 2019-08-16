package com.benoitthore.enamel.processingtest.kotlinapplet

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.core.math.i
import com.benoitthore.enamel.geometry.allocate
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.geometry.figures.ESizeMutable
import com.benoitthore.enamel.geometry.figures.size
import processing.core.PGraphics


val PGraphics.eframe get() = allocate { ERectMutable(size = esize) }
var PGraphics.esize: ESizeMutable
    get() = allocate { width size height }
    set(value) {
        setSize(value.width.i, value.height.i)
    }

fun PGraphics.stroke(r: Number, g: Number, b: Number, a: Number = 255f) = stroke(r.f, g.f, b.f, a.f)
fun PGraphics.fill(r: Number, g: Number, b: Number, a: Number = 255f) = fill(r.f, g.f, b.f, a.f)

