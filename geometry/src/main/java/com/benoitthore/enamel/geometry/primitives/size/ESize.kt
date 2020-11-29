package com.benoitthore.enamel.geometry.primitives.size

import com.benoitthore.enamel.core.math.d
import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.builders.*
import com.benoitthore.enamel.geometry.primitives.Tuple2
import kotlin.math.abs
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min

interface ESize : Tuple2 {
    
    val width: Float
    val height: Float
    
    override val v1: Number get() = width
    override val v2: Number get() = height

    fun copy(
        width: Number = this.width,
        height: Number = this.height,
        target: ESizeMutable = MutableSize()
    ) = target.set(width, height)

    val min get() = min(width, height)
    val max get() = max(width, height)
    val diagonal get() = hypot(width.d, height.d).f
    val area get() = width * height
    val hasArea get() = area > 0

    fun abs(target: ESizeMutable = MutableSize()) = target.set(abs(width), abs(height))

    fun inset(x: Number, y: Number, target: ESizeMutable = MutableSize()) =
        target.set(width - x.f, height - y.f)

    fun inset(other: Tuple2, target: ESizeMutable = MutableSize()) =
        inset(other.v1, other.v2, target)

    fun inset(n: Number, target: ESizeMutable = MutableSize()) = inset(n, n, target)

    fun expand(x: Number, y: Number, target: ESizeMutable = MutableSize()) =
        inset(-x.f, -y.f, target)

    fun expand(other: Tuple2, target: ESizeMutable = MutableSize()) =
        expand(other.v1, other.v2, target)

    fun expand(n: Number, target: ESizeMutable = MutableSize()) = expand(n, n, target)

    fun scale(x: Number, y: Number, target: ESizeMutable = MutableSize()) =
        target.set(width * x.f, height * y.f)

    fun scale(other: Tuple2, target: ESizeMutable = MutableSize()) =
        scale(other.v1, other.v2, target)

    fun scale(n: Number, target: ESizeMutable = MutableSize()) = scale(n, n, target)

    fun dividedBy(x: Number, y: Number, target: ESizeMutable = MutableSize()) =
        target.set(width / x.f, height / y.f)

    fun dividedBy(other: Tuple2, target: ESizeMutable = MutableSize()) =
        dividedBy(other.v1, other.v2, target)

    fun dividedBy(n: Number, target: ESizeMutable = MutableSize()) = dividedBy(n, n, target)


}

interface ESizeMutable : ESize{
    override var width: Float
    override var height: Float

    fun set(width: Number, height: Number): ESizeMutable {
        this.width = width.f
        this.height = height.f
        return this
    }

    fun set(size: ESize) = set(size.width, size.height)


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
