package com.benoitthore.enamel.geometry.figures

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.core.math.d
import com.benoitthore.enamel.geometry.Resetable
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.primitives.Tuple2
import kotlin.math.*

interface ESize : Tuple2 {
    val width: Float
    val height: Float

    override val v1: Number get() = width
    override val v2: Number get() = height

    fun toMutable() = E.SizeMutable(width, height)
    fun toImmutable() = E.Size(width, height)

    fun copy(
        width: Number = this.width,
        height: Number = this.height,
        target: ESizeMutable = E.SizeMutable()
    ) =
        target.set(width, height)

    val min get() = min(width, height)
    val max get() = max(width, height)
    val diagonal get() = hypot(width.d, height.d).f
    val area get() = width * height
    val hasArea get() = area > 0

    fun abs(target: ESizeMutable = E.SizeMutable()) = target.set(abs(width), abs(height))

    fun inset(x: Number, y: Number, target: ESizeMutable = E.SizeMutable()) =
        target.set(width - x.f, height - y.f)

    fun inset(other: Tuple2, target: ESizeMutable = E.SizeMutable()) =
        inset(other.v1, other.v2, target)

    fun inset(n: Number, target: ESizeMutable = E.SizeMutable()) = inset(n, n, target)

    fun expand(x: Number, y: Number, target: ESizeMutable = E.SizeMutable()) =
        inset(-x.f, -y.f, target)

    fun expand(other: Tuple2, target: ESizeMutable = E.SizeMutable()) =
        expand(other.v1, other.v2, target)

    fun expand(n: Number, target: ESizeMutable = E.SizeMutable()) = expand(n, n, target)

    fun scale(x: Number, y: Number, target: ESizeMutable = E.SizeMutable()) =
        target.set(width * x.f, height * y.f)

    fun scale(other: Tuple2, target: ESizeMutable = E.SizeMutable()) =
        scale(other.v1, other.v2, target)

    fun scale(n: Number, target: ESizeMutable = E.SizeMutable()) = scale(n, n, target)

    fun dividedBy(x: Number, y: Number, target: ESizeMutable = E.SizeMutable()) =
        target.set(width / x.f, height / y.f)

    fun dividedBy(other: Tuple2, target: ESizeMutable = E.SizeMutable()) =
        dividedBy(other.v1, other.v2, target)

    fun dividedBy(n: Number, target: ESizeMutable = E.SizeMutable()) = dividedBy(n, n, target)


}

interface ESizeMutable : ESize, Resetable {

    class Impl internal constructor(width: Number, height: Number) : ESizeMutable {
        override var width: Float = width.toFloat()
        override var height: Float = height.toFloat()
    }

    override var width: Float
    override var height: Float

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

infix fun Number.size(height: Number) = E.SizeMutable(this, height)

