package com.thorebenoit.enamel.kotlin.ai.neurtalnetwork

import com.thorebenoit.enamel.kotlin.core._2dec
import com.thorebenoit.enamel.kotlin.core.math.d
import com.thorebenoit.enamel.kotlin.core.math.randomise
import com.thorebenoit.enamel.kotlin.core.math.toMatrixVertical
import com.thorebenoit.enamel.kotlin.core.print
import koma.extensions.emul
import koma.extensions.map
import koma.matrix.Matrix
import koma.zeros
import java.lang.Exception


inline fun sigmoid(x: Number) = 1 / (1 + Math.exp(-x.toDouble()))
inline fun sigmoidf(x: Number) = 1 / (1 + Math.exp(-x.toDouble())).toFloat()

//inline fun dsigmoid(x: Number) = sigmoid(x) * (1 - sigmoid(x))
inline fun dsigmoid(x: Number) = x.d * (1 - x.d)

class ToyNeuralNetwork(
    val nbInputNodes: Int,
    val nbHiddenNodes: Int,
    val nbOutputNodes: Int,
    private var weight_IH: Matrix<Double> = zeros(nbHiddenNodes, nbInputNodes).randomise(-1, 1),
    private var weight_HO: Matrix<Double> = zeros(nbOutputNodes, nbHiddenNodes).randomise(-1, 1),
    private var bias_H: Matrix<Double> = zeros(nbHiddenNodes, 1).randomise(-1, 1),
    private var bias_O: Matrix<Double> = zeros(nbOutputNodes, 1).randomise(-1, 1),
    var learningRate: Double = 0.1
) {

    constructor(other: ToyNeuralNetwork) : this(
        nbInputNodes = other.nbInputNodes,
        nbHiddenNodes = other.nbHiddenNodes,
        nbOutputNodes = other.nbOutputNodes,
        weight_IH = other.weight_IH.copy(),
        weight_HO = other.weight_HO.copy(),
        bias_H = other.bias_H.copy(),
        bias_O = other.bias_O.copy(),
        learningRate = other.learningRate
    )

    fun feedForward(input: List<Number>): List<Double> = feedForward(input.toMatrixVertical()).toList()

    fun feedForward(input: Matrix<Double>): Matrix<Double> {
        if (input.size != this.nbInputNodes) {
            throw Exception("expected ${this.nbInputNodes} element as an inputNodes")
        }

        val hidden = ((weight_IH * input) + bias_H).map { sigmoid(it) }

        val output = ((weight_HO * hidden) + bias_O).map { sigmoid(it) }

        return output
    }

    fun error(input: List<Number>, target: List<Number>): Double {
        if (nbOutputNodes != target.size) {
            throw Exception("expected ${this.nbOutputNodes} element as target")
        }
        val output = feedForward(input)

        var error = 0.0
        output.forEachIndexed { index, value ->
            error += Math.abs(value - target[index].toDouble())
        }
        return error / output.size
    }

    fun train(input: List<Number>, target: List<Number>) = train(input.toMatrixVertical(), target.toMatrixVertical())

    fun train(input: Matrix<Double>, target: Matrix<Double>) {
        if (input.size != this.nbInputNodes) {
            throw Exception("expected ${this.nbInputNodes} element as an input")
        }
        if (target.size != this.nbOutputNodes) {
            throw Exception("expected ${this.nbOutputNodes} element as an target")
        }


        val hidden = ((weight_IH * input) + bias_H).map { sigmoid(it) }

        val output = ((weight_HO * hidden) + bias_O).map { sigmoid(it) }

        val outputError = target - output

        // Calulate gradient
        val gradient = (output.map(::dsigmoid) emul outputError) * learningRate

        // Calculate  deltas
        val weight_HO_delta = gradient * hidden.T

        // Adjust weight and bias
        this.weight_HO = weight_HO + weight_HO_delta
        this.bias_O = bias_O + gradient


        val hiddenError = weight_HO.T() * outputError

        val hiddenGradient = (hidden.map(::dsigmoid) emul hiddenError) * learningRate

        // Calculate input->hidden deltas
        val weigth_IH_delta = (hiddenGradient * input.T())
        this.weight_IH = weight_IH + weigth_IH_delta
        this.bias_H = bias_H + hiddenGradient
    }

}


private fun main() {
    val nn = ToyNeuralNetwork(2, 4, 1)

    val training = listOf(
        listOf(1, 0) to 1,
        listOf(0, 1) to 1,
        listOf(1, 1) to 0,
        listOf(0, 0) to 0
    )


    (0..50_000).forEach {
        val (input, target) = training.random()
        nn.train(input, listOf(target))
    }

    nn.feedForward(listOf(1, 0)).print
    nn.feedForward(listOf(0, 1)).print
    nn.feedForward(listOf(1, 1)).print
    nn.feedForward(listOf(0, 0)).print

    val avgError = training.map { (input, target) ->
        nn.error(input, listOf(target))
    }.sum() / 4

    println()
    if (avgError > 0.05) {
        throw Exception("Not working")
    }
    "Error: ${(avgError * 100)._2dec}%".print

}