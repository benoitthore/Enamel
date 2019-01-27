package com.thorebenoit.enamel.kotlin.geometry.figures

import com.thorebenoit.enamel.kotlin.*
import com.thorebenoit.enamel.kotlin.core.d
import com.thorebenoit.enamel.kotlin.core.f

open class ESizeImmutable(open val width: Float = 0f, open val height: Float = 0f) {
    fun toMutable() = ESize(width, height)
    fun toImmutable() = ESizeImmutable(width, height)

    val min get() = Math.min(width, height)
    val max get() = Math.max(width, height)
    val diagonal get() = Math.hypot(width.d,height.d).f
    val area get() = width * height

}

class ESize(override var width: Float = 0f, override var height: Float = 0f) : ESizeImmutable(width, height) {
    constructor(width: Number, height: Number) : this(width.f, height.f)

    fun copy() = ESize(width, height)

}

infix fun Number.size(height: Number) = ESize(this, height)

