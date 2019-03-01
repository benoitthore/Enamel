package com.thorebenoit.enamel.kotlin.ai.neurtalnetwork.ml4j

import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.dataset.DataSet
import org.nd4j.linalg.dataset.api.DataSetPreProcessor
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator

class EDataSetIterator(
    private val _labels: Set<String>,
    val datas: List<ELabeledData>,
    val batch: Int
) : DataSetIterator {


    private var cursor = 0

    override fun resetSupported(): Boolean = true

    override fun getLabels(): List<String> = _labels.toList()

    override fun cursor(): Int = cursor

    override fun remove() {
        TODO()
    }

    override fun inputColumns(): Int = datas.first().data.size

    override fun numExamples(): Int = labels.size

    override fun batch(): Int = batch

    // https://stackoverflow.com/questions/48845162/how-can-i-use-a-custom-data-model-with-deeplearning4j
    override fun next(num: Int): DataSet {
        TODO()
    }

    override fun next(): DataSet {
        val dataSet = datas[cursor++].toDataSet(_labels)
        preProcessor?.preProcess(dataSet)
        return dataSet
    }

    override fun totalOutcomes(): Int = labels.size

    private var preProcessor: DataSetPreProcessor? = null

    override fun setPreProcessor(preProcessor: DataSetPreProcessor?) {
        this.preProcessor = preProcessor
    }

    override fun totalExamples(): Int = numExamples()

    override fun reset() {
        cursor = 0
    }

    override fun hasNext(): Boolean = cursor < datas.size

    override fun asyncSupported(): Boolean = true

    override fun getPreProcessor(): DataSetPreProcessor? = preProcessor

}


