package com.thorebenoit.enamel.kotlin.core.math

import koma.extensions.*
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

fun <T : Number> arange(
    start: Number,
    stop: Number,
    step: Number,
    dtype: MatrixType<T>
): Matrix<Double> = dtype().arange(start.d, stop.d, step.d).toDouble()


fun <T : Number> Matrix<T>.toDouble(): Matrix<Double> {
    val out = zeros(this.numRows(), this.numCols())
    for (row in 0 until this.numRows())
        for (col in 0 until this.numCols())
            out[row, col] = this[row, col].toDouble()
    return out
}


fun Matrix<Double>.randomise(min: Number, max: Number) = map { random(-1, 1).d }

fun List<Number>.toMatrixHorizontal(): Matrix<Double> {
    var i = 0
    return zeros(1, size).map {
        this[i++].d
    }
}


fun List<Number>.toMatrixVertical(): Matrix<Double> {
    var i = 0
    return zeros(size, 1).map {
        this[i++].d
    }
}
