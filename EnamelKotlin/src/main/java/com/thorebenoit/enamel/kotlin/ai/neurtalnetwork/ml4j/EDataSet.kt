package com.thorebenoit.enamel.kotlin.ai.neurtalnetwork.ml4j

import org.nd4j.linalg.dataset.api.iterator.DataSetIterator

data class ELabeledData(val label: String, val data: List<Float>)

class EDataSet(val dataSize: Int, val labels: Set<String>) {

    private val datas: MutableList<ELabeledData> = mutableListOf()

    fun getDataSet() = datas.toList()

    fun addEntry(labeledData: ELabeledData) {
        addEntry(labeledData.label, labeledData.data)
    }

    fun addEntries(labeledData: List<ELabeledData>) {
        labeledData.forEach { addEntry(it) }
    }

    fun addEntry(label: String, data: List<Float>) {
        if (data.size != dataSize) {
            throw Exception("data size is ${data.size}, expected $dataSize")
        }

        if (!labels.contains(label)) {
            throw Exception("Unkown label $label: only allowed $labels")
        }

        datas.add(ELabeledData(label, data))
    }

    fun addEntries(label: String, datas: List<List<Float>>) {
        datas.forEach { addEntry(label, it) }
    }


    fun getDataSetIterator(shuffled: Boolean = true, batch: Int = 10): DataSetIterator {
        val _data = if (shuffled) datas.shuffled() else datas
        return EDataSetIterator(
            labels.toList(),
            _data.toList(),
            batch
        )
    }


}

