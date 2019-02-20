package com.thorebenoit.enamel.kotlin.ai.neurtalnetwork

import com.thorebenoit.enamel.kotlin.core.math.randomise
import com.thorebenoit.enamel.kotlin.core.math.toMatrixVertical
import koma.extensions.map
import koma.matrix.Matrix
import koma.zeros


inline fun sigmoid(x: Number) = 1 / (1 + Math.exp(-x.toDouble()))
inline fun sigmoidf(x: Number) = 1 / (1 + Math.exp(-x.toDouble())).toFloat()

class NeuralNetwork(
    input: Int,
    hidden: Int,
    output: Int
) {

    val weight_IH = zeros(hidden, input).randomise(-1, 1)
    val weight_HO = zeros(output, hidden).randomise(-1, 1)

    val bias_O = zeros(output, 1).randomise(-1, 1)
    val bias_H = zeros(hidden, 1).randomise(-1, 1)

    fun feedForward(input: List<Number>): List<Double> = feedForward(input.toMatrixVertical()).toList()

    fun feedForward(input: Matrix<Double>): Matrix<Double> {

        val hidden = weight_IH * input + bias_H
        val hiddenOutput = hidden.map { sigmoid(it) }

        val output = weight_HO * hiddenOutput + bias_O
        val outputOutput = output.map { sigmoid(it) }

        return outputOutput
    }


}


