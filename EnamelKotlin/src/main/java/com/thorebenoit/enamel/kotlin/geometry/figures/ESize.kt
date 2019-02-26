package com.thorebenoit.enamel.kotlin.geometry.figures

import com.thorebenoit.enamel.kotlin.core.Resetable
import com.thorebenoit.enamel.kotlin.core.math.d
import com.thorebenoit.enamel.kotlin.core.math.f
import com.thorebenoit.enamel.kotlin.geometry.allocateDebugMessage
import com.thorebenoit.enamel.kotlin.geometry.allocate
import com.thorebenoit.enamel.kotlin.geometry.primitives.Tuple2

open class ESizeType(open val width: Float = 0f, open val height: Float = 0f) : Tuple2 {

    constructor(width: Number, height: Number) : this(width.f, height.f)

    override val v1: Number get() = width
    override val v2: Number get() = height

    companion object {
        val zero: ESizeType = allocate { ESizeType() }
        val greatestSize: ESizeType = allocate { ESizeType(Float.MAX_VALUE, Float.MAX_VALUE) }

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

    fun inset(x: Number, y: Number, buffer: ESize = ESize()) = buffer.set(width - x.f, height - y.f)
    fun inset(other: Tuple2, buffer: ESize = ESize()) = inset(other.v1, other.v2, buffer)
    fun inset(n: Number, buffer: ESize = ESize()) = inset(n, n, buffer)

    fun expand(x: Number, y: Number, buffer: ESize = ESize()) = inset(-x.f, -y.f, buffer)
    fun expand(other: Tuple2, buffer: ESize = ESize()) = expand(other.v1, other.v2, buffer)
    fun expand(n: Number, buffer: ESize = ESize()) = expand(n, n, buffer)

    fun scale(x: Number, y: Number, buffer: ESize = ESize()) = buffer.set(width * x.f, height * y.f)
    fun scale(other: Tuple2, buffer: ESize = ESize()) = scale(other.v1, other.v2, buffer)
    fun scale(n: Number, buffer: ESize = ESize()) = scale(n, n, buffer)

    fun div(x: Number, y: Number, buffer: ESize = ESize()) = buffer.set(width / x.f, height / y.f)
    fun div(other: Tuple2, buffer: ESize = ESize()) = div(other.v1, other.v2, buffer)
    fun div(n: Number, buffer: ESize = ESize()) = scale(n, n, buffer)


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
    constructor(other: Tuple2) : this(other.v1, other.v2)

    fun set(width: Number, height: Number): ESize {
        this.width = width.f
        this.height = height.f
        return this
    }

    fun set(size: ESizeType) = set(size.width, size.height)

    override fun reset() {
        set(0, 0)
    }

    fun selfInset(x: Number, y: Number) = inset(x, y, this)
    fun selfInset(other: Tuple2) = inset(other, this)
    fun selfInset(n: Number) = inset(n, this)

    fun selfExpand(x: Number, y: Number) = inset(x, y, this)
    fun selfExpand(other: Tuple2) = expand(other, this)
    fun selfExpand(n: Number) = expand(n, this)

    fun selfScale(x: Number, y: Number) = scale(x, y)
    fun selfScale(other: Tuple2) = scale(other, this)
    fun selfScale(n: Number) = scale(n, this)

    fun selfDiv(x: Number, y: Number) = scale(x, y, this)
    fun selfDiv(other: Tuple2) = scale(other, this)
    fun selfDiv(n: Number) = scale(n, this)


}

infix fun Number.size(height: Number) = ESize(this, height)