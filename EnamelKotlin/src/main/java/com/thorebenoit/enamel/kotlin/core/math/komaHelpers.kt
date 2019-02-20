package com.thorebenoit.enamel.kotlin.core.math

import koma.extensions.get
import koma.extensions.map
import koma.extensions.set
import koma.internal.KomaJsName
import koma.matrix.Matrix
import koma.matrix.MatrixType
import koma.matrix.MatrixTypes
import koma.zeros

// Koma helper so floats can be used
infix fun <T : Number> T.end(other: T) = Pair(this.toDouble(), other.toDouble())


fun arange(start: Number, stop: Number, step: Number): Matrix<Double> = arange(
    start,
    stop,
    step,
    dtype = MatrixTypes.DoubleType
)

fun <T> arange(
    start: Number,
    stop: Number,
    step: Number,
    dtype: MatrixType<T>
): Matrix<T> = dtype().arange(start.d, stop.d, step.d)


fun Matrix<Number>.toDouble(): Matrix<Double> {
    val out = zeros(this.numRows(), this.numCols())
    for (row in 0 until this.numRows())
        for (col in 0 until this.numCols())
            out[row, col] = this[row, col].toDouble()
    return out
}