package com.thorebenoit.enamel.kotlin.ai.neurtalnetwork.ml4j

import com.thorebenoit.enamel.core.print
import org.deeplearning4j.eval.Evaluation
import org.deeplearning4j.nn.conf.NeuralNetConfiguration
import org.deeplearning4j.nn.conf.layers.DenseLayer
import org.deeplearning4j.nn.conf.layers.OutputLayer
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.deeplearning4j.nn.weights.WeightInit
import org.deeplearning4j.optimize.listeners.ScoreIterationListener
import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.dataset.DataSet
import org.nd4j.linalg.factory.Nd4j
import org.nd4j.linalg.learning.config.Nesterovs
import org.nd4j.linalg.lossfunctions.LossFunctions


/*
Genetic neuro evolution:
network.layers.first().setInput(
            network.layers.first().input()
        )
 */


fun List<Number>.toINDArray(): INDArray = Nd4j.create(map { it.toFloat() }.toFloatArray())

fun ELabeledData.toDataSet(labels: Set<String>): DataSet =
    DataSet(this.data.toINDArray(), labels.indexOfFirst { it == label }.asVector(labels.size))

fun Int.asVector(ofSize: Int): INDArray {
    val indexAsOne = this
    return (0 until ofSize).mapIndexed { index, i -> if (indexAsOne == index) 1 else 0 }.toINDArray()
}

fun INDArray.toFloatList(): List<Float> = toFloatArray().toList()
fun INDArray.toFloatArray(): FloatArray = data().asFloat()

class ENeuralNetwork(val inputNodes: Int, val hiddenLayers: List<Int>, val outputNodes: Int) {

    lateinit var network: MultiLayerNetwork

    val log = false

    init {
        if (hiddenLayers.isEmpty()) {
            throw Throwable("Needs at least 1 hidden layer")
        }
        resetNetwork()
    }

    fun resetNetwork() {
        val rngSeed = 123 // random number seed for reproducibility

        var layerdId = 0

        val configuration = NeuralNetConfiguration.Builder()
            .seed(rngSeed) //include a random seed for reproducibility
            // use stochastic gradient descent as an optimization algorithm
            .updater(Nesterovs(0.006, 0.9)) //specify the rate of change of the learning rate.
            .l2(1e-4)
            .learningRate(0.1)
            .list()

            // <LAYERS>
            .layer(
                layerdId++, DenseLayer.Builder() //create the first, input layer with xavier initialization
                    .nIn(inputNodes.print(log))
                    .also { "<hidden>".print(log) }
                    .nOut(hiddenLayers.first().print(log))
                    .activation(Activation.SIGMOID)
                    .weightInit(WeightInit.XAVIER)
                    .build()
            )
            .apply {


                var lastNbNodes = hiddenLayers.first()
                hiddenLayers.forEachIndexed { index, nbNodes ->

                    if (index < hiddenLayers.size - 1) {
                        layer(
                            layerdId++,
                            DenseLayer.Builder()
                                .nIn(lastNbNodes.print(log))
                                .nOut(nbNodes.print(log))
                                .activation(Activation.RELU)
                                .weightInit(WeightInit.XAVIER)
                                .build()
                        )

                    } else {


                        layer(
                            layerdId++,
                            OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD) //create hidden layer
                                .nIn(lastNbNodes.print(log))
                                .also { "</hidden>".print(log) }
                                .nOut(outputNodes.print(log))
                                .activation(Activation.SOFTMAX)
                                .weightInit(WeightInit.XAVIER)
                                .build()
                        )
                    }

                    lastNbNodes = nbNodes
                }


            }

            // </LAYERS>
            .pretrain(false).backprop(true) //use backpropagation to adjust weights
            .build()


        ///
        network = MultiLayerNetwork(configuration)
        network.init()
        //print the score with every 1 iteration
        network.setListeners(ScoreIterationListener(1))
    }

    private fun List<ELabeledData>.toDataSetIterator(labels: Set<String>) = with(EDataSet(inputNodes, labels)) {
        addEntries(this@toDataSetIterator)
        getDataSetIterator()
    }

    fun train(labels: Set<String>, trainingData: List<ELabeledData>, epoch: Int = 1) {
        (0 until epoch).forEach {
            network.fit(trainingData.toDataSetIterator(labels))
        }
    }

    fun feedForward(data: ELabeledData): List<Float> {
        return network.feedForward(data.data.toINDArray()).map { it.toFloatList() }.last()
    }

    fun test(labels: Set<String>, trainingData: List<ELabeledData>) {

        val testData = trainingData.toDataSetIterator(labels) // when getting this, is label order kept ?

        val eval = Evaluation(testData.labels)
        while (testData.hasNext()) {
            val next = testData.next()
            val output = network.output(next.features) //get the networks prediction
            eval.eval(next.labels, output) //check the prediction against the true class
        }

        println(eval.stats())
    }

}

fun main() {
    val nn = ENeuralNetwork(2, listOf(8), 2)

    val labels = setOf("true", "false")
    val truthTable = listOf(
        ELabeledData("true", listOf(0f, 1f)),
        ELabeledData("true", listOf(1f, 0f)),
        ELabeledData("false", listOf(1f, 1f)),
        ELabeledData("false", listOf(0f, 0f))
    )

    nn.resetNetwork()
    nn.train(labels, (0..800).flatMap { truthTable })

    nn.test(labels, truthTable)

    truthTable.forEach {
        it.print
        nn.feedForward(it).print
        "-----".print
    }

}




