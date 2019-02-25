package com.thorebenoit.enamel.kotlin.ai.neurtalnetwork

import com.thorebenoit.enamel.kotlin.ai.genetics.DnaBuilder
import com.thorebenoit.enamel.kotlin.ai.genetics.Genome
import com.thorebenoit.enamel.kotlin.ai.genetics.Population
import com.thorebenoit.enamel.kotlin.core._2dec
import com.thorebenoit.enamel.kotlin.core.math.d
import com.thorebenoit.enamel.kotlin.core.math.random
import com.thorebenoit.enamel.kotlin.core.math.randomise
import com.thorebenoit.enamel.kotlin.core.math.toMatrixVertical
import com.thorebenoit.enamel.kotlin.core.print
import koma.extensions.emul
import koma.extensions.forEach
import koma.extensions.map
import koma.extensions.set
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

    constructor(
        nbInputNodes: Int,
        nbHiddenNodes: Int,
        nbOutputNodes: Int,
        data: List<Number>,
        learningRate: Double = 0.1
    ) : this(nbInputNodes, nbHiddenNodes, nbOutputNodes, learningRate = learningRate) {
        setWeightsAndBiases(data)
    }

    fun serialise(): List<Number> {
        val data = mutableListOf<Number>()

        weight_IH.forEach { data += it }
        weight_HO.forEach { data += it }
        bias_H.forEach { data += it }
        bias_O.forEach { data += it }

        return data
    }

    fun setWeightsAndBiases(data: List<Number>) {
        var cursor = 0
        (0 until weight_IH.size).forEachIndexed { index, _ -> weight_IH.set(index, data[cursor++].toDouble()) }
        (0 until weight_HO.size).forEachIndexed { index, _ -> weight_HO.set(index, data[cursor++].toDouble()) }
        (0 until bias_H.size).forEachIndexed { index, _ -> bias_H.set(index, data[cursor++].toDouble()) }
        (0 until bias_O.size).forEachIndexed { index, _ -> bias_O.set(index, data[cursor++].toDouble()) }
    }

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


private fun main2() {
    var nn = ToyNeuralNetwork(2, 16, 1)

    val training = listOf(
        listOf(1, 0) to listOf(1),
        listOf(0, 1) to listOf(1),
        listOf(1, 1) to listOf(0),
        listOf(0, 0) to listOf(0)
    )


    (0..50_000).forEach {
        val (input, target) = training.random()
        nn.train(input, target)
        "Error: ${(nn.evaluate(training) * 100)._2dec}%".print

        Thread.sleep(1)
    }

    nn = ToyNeuralNetwork(nn.nbInputNodes, nn.nbHiddenNodes, nn.nbOutputNodes, nn.serialise())

    val avgError = nn.evaluate(training)

//    val avgError = training.map { (input, target) ->
//        nn.error(input, listOf(target))
//    }.sum() / 4

    println()
    if (avgError > 0.05) {
        throw Exception("Not working")
    }
    "Error: ${(avgError * 100)._2dec}%".print

}


private fun main() {
    for (____a in 0 until 5) {
        val baseNN = ToyNeuralNetwork(2, 16, 1)

        val training = listOf(
            listOf(1, 0) to listOf(1),
            listOf(0, 1) to listOf(1),
            listOf(1, 1) to listOf(0),
            listOf(0, 0) to listOf(0)
        )

//        val population = Population<ToyNeuralNetwork>(
//            baseNN.serialise().size,
//            100,
//            mutationRate = 0.01f,
//            evaluateFitness = {
//                val avgError = it.individual.evaluate(training).toFloat()
//                return@Population 1f / (avgError + 1)
//            },
//            builder = DnaBuilder {
//                ToyNeuralNetwork(baseNN.nbInputNodes, baseNN.nbHiddenNodes, baseNN.nbOutputNodes, it.map { it * 10 })
//            },
//            randomGene = { _, _ -> random(-1, 1) }
//        )

        val population = baseNN.getGeneticsBasedNeuralNetwork(100, 10) {
            val avgError = it.individual.evaluate(training).toFloat()
            1f / (avgError + 1)
        }

        (0 until 1000).forEach {
            population.evolve()
//        population.best.first.individual.evaluate(training).print
        }


        population.best.first.individual.evaluate(training).print
//       val errorRate = population.best.first.individual.evaluate(training) * 100
//       println("${errorRate._2dec} %")


    }
    System.exit(0)

}

fun ToyNeuralNetwork.getGeneticsBasedNeuralNetwork(
    populationSize: Int,
    scale: Int = 1,
    mutationRate: Float = 0.01f,
    evaluateFitness: (Genome<ToyNeuralNetwork>) -> Float
): Population<ToyNeuralNetwork> {
    val baseNeuralNetwork = this
    return Population(
        baseNeuralNetwork.serialise().size,
        populationSize = populationSize,
        mutationRate = mutationRate,
        evaluateFitness = evaluateFitness,
        builder = DnaBuilder {
            ToyNeuralNetwork(baseNeuralNetwork.nbInputNodes,
                baseNeuralNetwork.nbHiddenNodes,
                baseNeuralNetwork.nbOutputNodes,
                it.map { it * scale })
        },
        randomGene = { _, _ -> random(-1, 1) }
    )

}

fun ToyNeuralNetwork.evaluate(data: List<Pair<List<Number>, List<Number>>>): Double {
    val totalError = data.sumByDouble { (input, target) -> error(input, target) }
    val avgError = totalError / data.size
    return avgError
}