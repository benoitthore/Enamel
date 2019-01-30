package com.thorebenoit.enamel.kotlin.geometry.figures

import com.thorebenoit.enamel.kotlin.core.d
import com.thorebenoit.enamel.kotlin.core.f
import com.thorebenoit.enamel.kotlin.geometry.allocateDebugMessage
import com.thorebenoit.enamel.kotlin.geometry.allocate

open class ESizeImmutable(open val width: Float = 0f, open val height: Float = 0f) {
    companion object {
        val zero: ESizeImmutable = allocate { ESizeImmutable() }

    }

    init {
        allocateDebugMessage()
    }

    fun toMutable() = ESize(width, height)
    fun toImmutable() = ESizeImmutable(width, height)

    val min get() = Math.min(width, height)
    val max get() = Math.max(width, height)
    val diagonal get() = Math.hypot(width.d, height.d).f
    val area get() = width * height
    val hasArea get() = area > 0


    override fun equals(other: Any?): Boolean =
        (other as? ESize)?.let { it.width == width && it.height == height } ?: false

    override fun toString(): String {
        return "ESize(width=$width, height=$height)"
    }

}

class ESize(override var width: Float = 0f, override var height: Float = 0f) : ESizeImmutable(width, height) {
    constructor(width: Number, height: Number) : this(width.f, height.f)

    fun copy() = ESize(width, height)
    fun set(width: Number, height: Number): ESize {
        this.width = width.f
        this.height = height.f
        return this
    }

    fun set(size: ESizeImmutable) = set(size.width, size.height)

    fun inset(x: Number, y: Number) = set(width - x.f, height - y.f)
    fun inset(n: Number) = inset(n, n)

    fun expand(x: Number, y: Number) = inset(-x.f, -y.f)
    fun expand(n: Number) = expand(n, n)

    fun scale(x: Number, y: Number) = set(width * x.f, height * y.f)
    fun scale(n: Number) = scale(n, n)


}

infix fun Number.size(height: Number) = ESize(this, height)

