package com.thorebenoit.enamel.kotlin.ai.neurtalnetwork

import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.core.time.ETimer
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
import org.nd4j.linalg.dataset.api.DataSetPreProcessor
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator
import org.nd4j.linalg.factory.Nd4j
import org.nd4j.linalg.lossfunctions.LossFunctions


data class ELabeledData(val label: String, val data: List<Float>)

/*
Genetic neuro evolution:
network.layers.first().setInput(
            network.layers.first().input()
        )
 */
class EDataSet(val dataSize: Int, val labels: Set<String>) {
    //TODO Check if the order is guaranteed to be maintained in a Set, if yes, use one
    //  /!\ The order matters /!\
    private val datas: MutableList<ELabeledData> = mutableListOf()

    fun getDataSet() = datas.toList()


    fun addEntry(labeledData: ELabeledData) {
        labeledData.apply {
            if (data.size != dataSize) {
                throw Exception("data size is ${data.size}, expected $dataSize")
            }

            if (!labels.contains(label)) {
                throw Exception("Unkown label $label: only allowed $labels")
            }

            datas.add(ELabeledData(label, data))
        }
    }

    fun addEntries(labeledData: List<ELabeledData>) {
        labeledData.forEach { addEntry(it) }
    }

    fun addEntry(label: String, data: List<Float>) {
        addEntry(ELabeledData(label, data))
    }

    fun addEntries(label: String, datas: List<List<Float>>) {
        datas.forEach { addEntry(label, it) }
    }


    fun getDataSetIterator(shuffled: Boolean = true, batch: Int = 10): DataSetIterator {
        val _data = if (shuffled) datas.shuffled() else datas
        return EDataSetIterator(labels.toList(), _data.toList(), batch)
    }


}

class ENeuralNetwork(val inputNodes: Int, val hiddenLayers: List<Int>, val outputNodes: Int) {


    private lateinit var network: MultiLayerNetwork

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
//            .updater(Nesterovs(0.006, 0.9)) //specify the rate of change of the learning rate.
//            .l2(1e-4)
//            .learningRate(0.1)
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

    // TODO
    fun List<ELabeledData>.toDataSetIterator(labels: Set<String>) = with(EDataSet(inputNodes, labels)) {
        addEntries(this@toDataSetIterator)
        getDataSetIterator()
    }

    fun train(labels: Set<String>, trainingData: List<ELabeledData>, epoch: Int = 1) {
        (0 until epoch).forEach {
            // _TODO
            network.fit(trainingData.toDataSetIterator(labels))             // TODO
            // _TODO
        }

    }

    fun test(labels: Set<String>, trainingData: List<ELabeledData>) {

        // TODO Fix ~50% accuracy when running train ten test
        // TODO Fix Warning: 1 class was never predicted by the model and was excluded from average precision
        val testData = trainingData.toDataSetIterator(labels) // when getting this, is label order kept ?


        val eval = Evaluation(testData.labels)
        while (testData.hasNext()) {
            val next = testData.next()
            val output = network.output(next.features) //get the networks prediction
            eval.eval(next.labels, output) //check the prediction against the true class
        }

        println(eval.stats())
    }

    // TODO  </TODO>

    fun fit() {
        val labels = setOf("true", "false")
        val truthTable = listOf(
            ELabeledData("true", listOf(0f, 1f)),
            ELabeledData("true", listOf(1f, 0f)),
            ELabeledData("false", listOf(1f, 1f)),
            ELabeledData("false", listOf(0f, 0f))
        )

        println("Train model....")
        train(labels, (0 until 800).map { truthTable.random() })
        test(labels, listOf(truthTable[2]))
//        test(labels, (0 until 200).map { truthTable.random() })


        return
        "".print
        "".print
        "".print
        "".print
        "".print
        "".print
        resetNetwork()


        val trainingData = EDataSet(2, labels).apply {

            (0..800).forEach {
                addEntry("true", listOf(0f, 1f))
                addEntry("true", listOf(1f, 0f))
                addEntry("false", listOf(0f, 0f))
                addEntry("false", listOf(1f, 1f))
            }

        }.getDataSetIterator()

        val testData = EDataSet(2, labels).apply {

            (0..200).forEach {
                addEntry("true", listOf(0f, 1f))
                addEntry("true", listOf(1f, 0f))
                addEntry("false", listOf(0f, 0f))
                addEntry("false", listOf(1f, 1f))
            }
            // _TODO
        }.getDataSetIterator() // TODO
        // _TODO


        println("Train model....")
        network.fit(trainingData)

        println("Evaluate model....")
        val eval = Evaluation(labels.toList()) //create an evaluation object with 10 possible classes
        while (testData.hasNext()) {
            val next = testData.next()
            val output = network.output(next.features) //get the networks prediction
            eval.eval(next.labels, output) //check the prediction against the true class
        }

        println(eval.stats())
        println("****************Example finished********************")
    }
}

fun main() {
    val nn = ENeuralNetwork(2, listOf(8), 2)
    nn.fit()
}

data class LabeledDataOLD(val data: List<Float>, val label: Int, val labelList: List<String>) {
    fun createVector() = (0 until labelList.size).mapIndexed { index, i -> if (label == index) 1 else 0 }
}


    fun List<Number>.toINDArray(): INDArray = Nd4j.create(map { it.toFloat() }.toFloatArray())

    fun LabeledDataOLD.toDataSet(): DataSet =
        DataSet(this.data.toINDArray(), this.createVector().toINDArray())

