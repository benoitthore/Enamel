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

    fun copy(buffer: ESize = ESize()) = buffer.set(this)

    fun set(width: Number, height: Number, buffer: ESize = this): ESize {
        this.width = width.f
        this.height = height.f
        return buffer
    }

    fun set(size: ESizeImmutable, buffer: ESize = this) = set(size.width, size.height, buffer)

    fun inset(x: Number, y: Number, buffer: ESize = this) = set(width - x.f, height - y.f, buffer)
    fun inset(n: Number, buffer: ESize = this) = inset(n, n, buffer)

    fun expand(x: Number, y: Number, buffer: ESize = this) = inset(-x.f, -y.f, buffer)
    fun expand(n: Number, buffer: ESize = this) = expand(n, n, buffer)

    fun scale(x: Number, y: Number, buffer: ESize = this) = set(width * x.f, height * y.f, buffer)
    fun scale(n: Number, buffer: ESize = this) = scale(n, n, buffer)


}

infix fun Number.size(height: Number) = ESize(this, height)

