package com.thorebenoit.enamel.kotlin.geometry.primitives

import com.thorebenoit.enamel.kotlin.geometry.figures.ESize
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType

/**
 * This file is used to facilitate development of HIGHER LEVEL APIs, it doesn't care about allocations
 */

interface Tuple2 {
    val v1: Number
    val v2: Number
}


inline operator fun EPointType.unaryMinus() = inverse()

inline operator fun EPointType.div(other: Tuple2) = div(other)
inline operator fun EPointType.div(n: Number) = div(n)

inline operator fun EPointType.times(other: Tuple2) = times(other)
inline operator fun EPointType.times(n: Number) = times(n)

inline operator fun EPointType.plus(other: Tuple2) = offset(other)
inline operator fun EPointType.plus(n: Number) = offset(n)

inline operator fun EPointType.minus(other: Tuple2) = minus(other)
inline operator fun EPointType.minus(n: Number) = minus(n)


inline operator fun Number.times(p: EPointType) = p.times(this)
inline operator fun Number.plus(p: EPointType) = p.offset(this)


inline operator fun ESizeType.unaryMinus() = ESize(-width, -height)

inline operator fun ESizeType.div(other: Tuple2) = div(other)
inline operator fun ESizeType.div(n: Number) = div(n)

inline operator fun ESizeType.times(other: Tuple2) = scale(other)
inline operator fun ESizeType.times(n: Number) = scale(n)

inline operator fun ESizeType.plus(other: Tuple2) = expand(other)
inline operator fun ESizeType.plus(n: Number) = expand(n)

inline operator fun ESizeType.minus(other: Tuple2) = inset(other)
inline operator fun ESizeType.minus(n: Number) = inset(n)


inline operator fun Number.times(p: ESizeType) = p.times(this)
inline operator fun Number.plus(p: ESizeType) = p.expand(this)