package com.thorebenoit.enamel.kotlin.ai.neurtalnetwork

import com.thorebenoit.enamel.kotlin.core.print
import org.deeplearning4j.datasets.fetchers.BaseDataFetcher
import org.deeplearning4j.datasets.iterator.BaseDatasetIterator
import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator
import org.deeplearning4j.eval.Evaluation
import org.deeplearning4j.nn.api.OptimizationAlgorithm
import org.deeplearning4j.nn.conf.MultiLayerConfiguration
import org.deeplearning4j.nn.conf.NeuralNetConfiguration
import org.deeplearning4j.nn.conf.layers.DenseLayer
import org.deeplearning4j.nn.conf.layers.OutputLayer
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.deeplearning4j.nn.weights.WeightInit
import org.deeplearning4j.optimize.listeners.ScoreIterationListener
import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.api.ndarray.BaseNDArray
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.dataset.DataSet
import org.nd4j.linalg.dataset.api.DataSetPreProcessor
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator
import org.nd4j.linalg.dataset.api.iterator.DataSetIteratorFactory
import org.nd4j.linalg.factory.Nd4j
import org.nd4j.linalg.learning.config.Nesterovs
import org.nd4j.linalg.learning.config.Sgd
import org.nd4j.linalg.lossfunctions.LossFunctions
import org.slf4j.LoggerFactory

data class LabeledData(val data: List<Float>, val label: Int, val labelList: List<String>) {
    fun createVector() = (0 until labelList.size).mapIndexed { index, i -> if (label == index) 1 else 0 }
}


object ML4J_test{
    fun List<Number>.toINDArray(vertical: Boolean = false): INDArray = Nd4j.create(map { it.toFloat() }.toFloatArray())

    fun LabeledData.toDataSet(): DataSet =
        DataSet(this.data.toINDArray(), this.createVector().toINDArray())

    fun List<LabeledData>.toDataSetIterator(batch: Int = 10): DataSetIterator {

        val labeledDataList = this

        return object : DataSetIterator {
            private var cursor = 0
            override fun resetSupported(): Boolean = true

            override fun getLabels(): List<String> = labeledDataList.first().labelList

            override fun cursor(): Int = cursor

            override fun remove() {

            }

            override fun inputColumns(): Int = 1  // TODO

            override fun numExamples(): Int = labeledDataList.size

            override fun batch(): Int = batch

            // https://stackoverflow.com/questions/48845162/how-can-i-use-a-custom-data-model-with-deeplearning4j
            override fun next(num: Int): DataSet {
                println(num)
                return labeledDataList[num].toDataSet()
            }

            override fun next(): DataSet {
                return labeledDataList[cursor++].toDataSet()
            }

            override fun totalOutcomes(): Int = 2 // TODO

            private var preProcessor: DataSetPreProcessor? = null

            override fun setPreProcessor(preProcessor: DataSetPreProcessor?) {
                preProcessor.print
            }

            override fun totalExamples(): Int = numExamples()

            override fun reset() {
                cursor = 0
            }

            override fun hasNext(): Boolean = cursor < labeledDataList.size

            override fun asyncSupported(): Boolean = false

            override fun getPreProcessor(): DataSetPreProcessor? = preProcessor

        }

    }

    @JvmStatic
    fun main(args: Array<String>) {

        //number of rows and columns in the input pictures
//        val numRows = 28
//        val numColumns = 28
//        val outputNum = 10 // number of output classes
        val inputLayer = 2
        val outputLayer = 2

        val rngSeed = 123 // random number seed for reproducibility

        //Get the DataSetIterators:
//        val mnistTrain = MnistDataSetIterator(batchSize, true, rngSeed)
//        val mnistTest = MnistDataSetIterator(batchSize, false, rngSeed)

        println("Build model....")
        val conf = NeuralNetConfiguration.Builder()
            .seed(rngSeed) //include a random seed for reproducibility
            // use stochastic gradient descent as an optimization algorithm
            .updater(Nesterovs(0.006, 0.9)) //specify the rate of change of the learning rate.
            .l2(1e-4)
            .list()
            .layer(
                0, DenseLayer.Builder() //create the first, input layer with xavier initialization
                    .nIn(inputLayer)
                    .nOut(1000)
                    .activation(Activation.RELU)
                    .weightInit(WeightInit.XAVIER)
                    .build()
            )
            .layer(
                1, OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD) //create hidden layer
                    .nIn(1000)
                    .nOut(outputLayer)
                    .activation(Activation.SOFTMAX)
                    .weightInit(WeightInit.XAVIER)
                    .build()
            )
            .pretrain(false).backprop(true) //use backpropagation to adjust weights
            .build()

        val model = MultiLayerNetwork(conf)
        model.init()
        //print the score with every 1 iteration
        model.setListeners(ScoreIterationListener(1))


        val labels = listOf("false", "true")

        val truthTable = listOf(
            LabeledData(listOf(1f, 0f), 1, labels),
            LabeledData(listOf(0f, 1f), 1, labels),
            LabeledData(listOf(1f, 1f), 0, labels),
            LabeledData(listOf(0f, 0f), 0, labels)
        )

        val trainingData = (0..1000).flatMap { truthTable }.shuffled().toDataSetIterator()
        val testData = (0..200).flatMap { truthTable }.shuffled().toDataSetIterator()


        println("Train model....")
//        for (i in 0 until numEpochs) {
        model.fit(trainingData)
//        }

        println("Evaluate model....")
        val eval = Evaluation(labels) //create an evaluation object with 10 possible classes
        while (testData.hasNext()) {
            val next = testData.next()
            val output = model.output(next.features) //get the networks prediction
            eval.eval(next.getLabels(), output) //check the prediction against the true class
        }

        println(eval.stats())
        println("****************Example finished********************")

    }
}