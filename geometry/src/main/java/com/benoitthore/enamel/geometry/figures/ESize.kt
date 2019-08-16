package com.benoitthore.enamel.geometry.figures

import com.benoitthore.enamel.core.math.d
import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.Resetable
import com.benoitthore.enamel.geometry.allocateDebugMessage
import com.benoitthore.enamel.geometry.allocate
import com.benoitthore.enamel.geometry.primitives.Tuple2

open class ESize(open val width: Float = 0f, open val height: Float = 0f) : Tuple2 {

    constructor(width: Number, height: Number) : this(width.f, height.f)

    override val v1: Number get() = width
    override val v2: Number get() = height

    companion object {
        val zero: ESize = allocate { ESize() }
        val greatestSize: ESize = allocate { ESize(Float.MAX_VALUE, Float.MAX_VALUE) }
        fun square(size: Number) = ESizeMutable(size, size)
    }

    init {
        allocateDebugMessage()
    }

    fun toMutable() = ESizeMutable(width, height)
    fun toImmutable() = ESize(width, height)

    fun copy(width: Number = this.width, height: Number = this.height, buffer: ESizeMutable = ESizeMutable()) =
        buffer.set(width, height)

    val min get() = Math.min(width, height)
    val max get() = Math.max(width, height)
    val diagonal get() = Math.hypot(width.d, height.d).f
    val area get() = width * height
    val hasArea get() = area > 0

    fun abs(buffer: ESizeMutable = ESizeMutable()) = buffer.set(Math.abs(width), Math.abs(height))

    fun inset(x: Number, y: Number, buffer: ESizeMutable = ESizeMutable()) = buffer.set(width - x.f, height - y.f)
    fun inset(other: Tuple2, buffer: ESizeMutable = ESizeMutable()) = inset(other.v1, other.v2, buffer)
    fun inset(n: Number, buffer: ESizeMutable = ESizeMutable()) = inset(n, n, buffer)

    fun expand(x: Number, y: Number, buffer: ESizeMutable = ESizeMutable()) = inset(-x.f, -y.f, buffer)
    fun expand(other: Tuple2, buffer: ESizeMutable = ESizeMutable()) = expand(other.v1, other.v2, buffer)
    fun expand(n: Number, buffer: ESizeMutable = ESizeMutable()) = expand(n, n, buffer)

    fun scale(x: Number, y: Number, buffer: ESizeMutable = ESizeMutable()) = buffer.set(width * x.f, height * y.f)
    fun scale(other: Tuple2, buffer: ESizeMutable = ESizeMutable()) = scale(other.v1, other.v2, buffer)
    fun scale(n: Number, buffer: ESizeMutable = ESizeMutable()) = scale(n, n, buffer)

    fun dividedBy(x: Number, y: Number, buffer: ESizeMutable = ESizeMutable()) = buffer.set(width / x.f, height / y.f)
    fun dividedBy(other: Tuple2, buffer: ESizeMutable = ESizeMutable()) = dividedBy(other.v1, other.v2, buffer)
    fun dividedBy(n: Number, buffer: ESizeMutable = ESizeMutable()) = dividedBy(n, n, buffer)


    override fun equals(other: Any?): Boolean =
        (other as? ESize)?.let { it.width == width && it.height == height } ?: false

    override fun toString(): String {
        return "ESizeMutable(width=$width, height=$height)"
    }

    override fun hashCode(): Int {
        var result = width.hashCode()
        result = 31 * result + height.hashCode()
        return result
    }

}

class ESizeMutable(override var width: Float = 0f, override var height: Float = 0f) : ESize(width, height),
    Resetable {
    constructor(width: Number, height: Number) : this(width.f, height.f)
    constructor(other: Tuple2) : this(other.v1, other.v2)

    fun set(width: Number, height: Number): ESizeMutable {
        this.width = width.f
        this.height = height.f
        return this
    }

    fun set(size: ESize) = set(size.width, size.height)

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

infix fun Number.size(height: Number) = ESizeMutable(this, height)