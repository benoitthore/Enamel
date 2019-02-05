package com.thorebenoit.enamel.kotlin.core

import com.thorebenoit.enamel.kotlin.animations.Interpolator
import com.thorebenoit.enamel.kotlin.animations.linearInterpolator
import com.thorebenoit.enamel.kotlin.animations.sinInterpolator

private typealias DomainRange = Pair<Number, Number>

private val DomainRange.domain get() = first.f
private val DomainRange.range get() = second.f

private typealias DomainRangeFunction = Triple<Number, Number, (Float) -> Float>

private val DomainRangeFunction.domain get() = first.f
private val DomainRangeFunction.range get() = second
private val DomainRangeFunction.function get() = third

class Scale(domainRange: Iterable<Pair<Number, Number>>) {

    companion object {
        val defaultInterpolator = linearInterpolator
        fun map(value: Number, start1: Number, stop1: Number, start2: Number, stop2: Number): Float {
            return start2.f +
                    (stop2.f - start2.f) *
                    (
                            (value.f - start1.f)
                                    / (stop1.f - start1.f)
                            )
        }
    }

    val domain by lazy { this.map.map { it.domain } }
    val range by lazy { this.map.map { it.range } }
    // convert Iterable<Pair<Number, Number>> to List<Pair<Number, Float>>
    val map: List<Pair<Float, Float>> = domainRange.map { (a, b) -> a.f to b.f }

    constructor(domain: List<Number>, range: List<Number>) : this(domain.zip(range))
    constructor(map: Map<Number, Number>) : this(map.toList())
    constructor(domainFrom: Number, domainTo: Number, rangeFrom: Number, rangeTo: Number) :
            this(
                    listOf(domainFrom.f, domainTo.f),
                    listOf(rangeFrom.f, rangeTo.f))

    fun linear(x: Number) = scale(x.f, linearInterpolator)

    fun scale(x: Number, f: Interpolator): Float {
        val x = x.f
        when (map.size) {
            0 -> return x
            1 -> return map[0].domain - x + map[0].range
            else -> {
                if (x <= map[0].domain) {
                    return map[0].range
                }
                var from = map[0]

                (1 until map.size)
                        .map {
                            map[it]
                        }
                        .forEach { to ->
                            if (x < to.domain) {
                                val y = f((x - from.domain) / (to.domain - from.domain))
                                return (1 - y) * from.range + y * to.range
                            }
                            from = to
                        }
                return map.last().range

            }
        }
    }

    operator fun get(x: Number) = linear(x)
}

fun Scale.sin(it: Number) = this.scale(it.f, sinInterpolator)

operator fun Scale.times(n: Number) = Scale(domain, range.map { it * n.f })