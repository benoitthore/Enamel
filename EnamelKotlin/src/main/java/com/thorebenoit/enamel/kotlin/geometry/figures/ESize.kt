package com.thorebenoit.enamel.kotlin.geometry.figures

import com.thorebenoit.enamel.kotlin.core.Resetable
import com.thorebenoit.enamel.kotlin.core.math.d
import com.thorebenoit.enamel.kotlin.core.math.f
import com.thorebenoit.enamel.kotlin.geometry.allocateDebugMessage
import com.thorebenoit.enamel.kotlin.geometry.allocate
import com.thorebenoit.enamel.kotlin.geometry.primitives.Tuple2

open class ESizeType(open val width: Float = 0f, open val height: Float = 0f) : Tuple2 {
    override val v1: Number get() = width
    override val v2: Number get() = height

    companion object {
        val zero: ESizeType = allocate { ESizeType() }

    }

    init {
        allocateDebugMessage()
    }

    fun toMutable() = ESize(width, height)
    fun toImmutable() = ESizeType(width, height)

    fun copy(buffer: ESize = ESize()) = buffer.set(this)

    val min get() = Math.min(width, height)
    val max get() = Math.max(width, height)
    val diagonal get() = Math.hypot(width.d, height.d).f
    val area get() = width * height
    val hasArea get() = area > 0


    override fun equals(other: Any?): Boolean =
        (other as? ESizeType)?.let { it.width == width && it.height == height } ?: false

    override fun toString(): String {
        return "ESize(width=$width, height=$height)"
    }

    override fun hashCode(): Int {
        var result = width.hashCode()
        result = 31 * result + height.hashCode()
        return result
    }

}

class ESize(override var width: Float = 0f, override var height: Float = 0f) : ESizeType(width, height),
    Resetable {
    constructor(width: Number, height: Number) : this(width.f, height.f)
    constructor(other: ESizeType) : this(other.width, other.height)

    fun set(width: Number, height: Number, buffer: ESize = this): ESize {
        this.width = width.f
        this.height = height.f
        return buffer
    }

    fun set(size: ESizeType, buffer: ESize = this) = set(size.width, size.height, buffer)

    override fun reset() {
        set(0, 0)
    }

    fun inset(x: Number, y: Number, buffer: ESize = this) = set(width - x.f, height - y.f, buffer)
    fun inset(n: Number, buffer: ESize = this) = inset(n, n, buffer)

    fun expand(x: Number, y: Number, buffer: ESize = this) = inset(-x.f, -y.f, buffer)
    fun expand(n: Number, buffer: ESize = this) = expand(n, n, buffer)

    fun scale(x: Number, y: Number, buffer: ESize = this) = set(width * x.f, height * y.f, buffer)
    fun scale(n: Number, buffer: ESize = this) = scale(n, n, buffer)


}

infix fun Number.size(height: Number) = ESize(this, height)

inline operator fun ESizeType.times(n: Number) = ESize(this).apply { width *= n.f; height *= n.f }