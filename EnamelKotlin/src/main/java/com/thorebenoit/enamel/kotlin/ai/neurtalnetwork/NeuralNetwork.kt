package com.thorebenoit.enamel.kotlin.ai.neurtalnetwork

import com.thorebenoit.enamel.kotlin.core.math.randomise
import com.thorebenoit.enamel.kotlin.core.math.toMatrixVertical
import com.thorebenoit.enamel.kotlin.core.print
import koma.extensions.map
import koma.matrix.Matrix
import koma.zeros
import java.lang.Exception


inline fun sigmoid(x: Number) = 1 / (1 + Math.exp(-x.toDouble()))
inline fun sigmoidf(x: Number) = 1 / (1 + Math.exp(-x.toDouble())).toFloat()

class NeuralNetwork(
    val inputNodes: Int,
    hidden: Int,
    val outputNodes: Int
) {

    private val weight_IH = zeros(hidden, inputNodes).randomise(-1, 1)
    private val weight_HO = zeros(outputNodes, hidden).randomise(-1, 1)

    private val bias_O = zeros(outputNodes, 1).randomise(-1, 1)
    private val bias_H = zeros(hidden, 1).randomise(-1, 1)

    fun feedForward(input: List<Number>): List<Double> = feedForward(input.toMatrixVertical()).toList()

    fun feedForward(input: Matrix<Double>): Matrix<Double> {
        if (input.size != this.inputNodes) {
            throw Exception("expected ${this.inputNodes} element as an inputNodes")
        }

        val hidden = weight_IH * input + bias_H
        val hiddenOutput = hidden.map { sigmoid(it) }

        val output = weight_HO * hiddenOutput + bias_O
        val outputOutput = output.map { sigmoid(it) }

        return outputOutput
    }

    fun train(input: List<Number>, target: List<Number>) = train(input.toMatrixVertical(), target.toMatrixVertical())

    fun train(input: Matrix<Double>, target: Matrix<Double>) {
        val output = feedForward(input)
        if (target.size != this.outputNodes) {
            throw Exception("expected ${this.outputNodes} element as an outputNodes")
        }

        val outputError = target - output


        val hiddenError = weight_HO.T() * outputError
    }

}


