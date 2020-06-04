package com.benoitthore.enamel.geometry.primitives.size

import com.benoitthore.enamel.core.math.d
import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.interfaces.bounds.EShape
import com.benoitthore.enamel.geometry.primitives.Tuple2
import kotlin.math.abs
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min

interface ESize : Tuple2, EShape<ESize,ESizeMutable> {
    val width: Float
    val height: Float

    override val v1: Number get() = width
    override val v2: Number get() = height

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

