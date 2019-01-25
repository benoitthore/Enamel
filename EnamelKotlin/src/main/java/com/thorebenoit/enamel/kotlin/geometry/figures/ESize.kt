package com.thorebenoit.enamel.kotlin.geometry.figures

import com.thorebenoit.enamel.kotlin.*

open class ESizeImmutable(open val width: Float = 0f, open val height: Float = 0f)
class ESize(override var width: Float = 0f, override var height: Float = 0f) : ESizeImmutable(width, height){
    constructor(width: Number,height: Number) : this(width.f,height.f)
}

infix fun Number.size(height: Number) = ESize(this,height)

val ESizeImmutable.min get() = Math.min(width, height)
val ESizeImmutable.max get() = Math.min(width, height)
val ESizeImmutable.area get() = width * height