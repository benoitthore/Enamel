package com.thorebenoit.enamel.processingtest.kotlinapplet

import com.thorebenoit.enamel.core.math.f
import com.thorebenoit.enamel.core.math.i
import com.thorebenoit.enamel.geometry.allocate
import com.thorebenoit.enamel.geometry.figures.ERectMutable
import com.thorebenoit.enamel.geometry.figures.ESizeMutable
import com.thorebenoit.enamel.geometry.figures.size
import processing.core.PGraphics


val PGraphics.eframe get() = allocate { ERectMutable(size = esize) }
var PGraphics.esize: ESizeMutable
    get() = allocate { width size height }
    set(value) {
        setSize(value.width.i, value.height.i)
    }

fun PGraphics.stroke(r: Number, g: Number, b: Number, a: Number = 255f) = stroke(r.f, g.f, b.f, a.f)
fun PGraphics.fill(r: Number, g: Number, b: Number, a: Number = 255f) = fill(r.f, g.f, b.f, a.f)

