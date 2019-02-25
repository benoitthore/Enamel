package com.thorebenoit.enamel.processingtest.doodleclassifier

import com.thorebenoit.enamel.kotlin.ai.genetics.DnaBuilder
import com.thorebenoit.enamel.kotlin.ai.genetics.Genome
import com.thorebenoit.enamel.kotlin.ai.genetics.Population
import com.thorebenoit.enamel.kotlin.ai.neurtalnetwork.LabeledDataOLD
import com.thorebenoit.enamel.kotlin.ai.neurtalnetwork.ToyNeuralNetwork
import com.thorebenoit.enamel.kotlin.core._2dec
import com.thorebenoit.enamel.kotlin.core.math.lerp
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.core.time.ETimer

fun DoodleTrainerGeneticsConfiguration.test(
    map: List<LabeledDataOLD>,
    objects: List<Any>,
    print: Boolean = false
): Float {

    val map = map.shuffled()

    val trainingRatio = 0.9f

    val training = map.subList(0, (map.size * trainingRatio).toInt())
    val testing = map.subList((map.size * trainingRatio).toInt(), map.size)


    val nn = ToyNeuralNetwork(map.first().data.size, hiddenNodes, objects.size)

    if (print)
        println("training")
    training.forEach {
        nn.train(it.data, it.createVector())
    }

    if (print)
        println("training done")

    var totalError = 0.0
    testing.forEach {
        val result = nn.feedForward(it.data)
        val error = nn.error(it.data, it.createVector())

        totalError += error
        if (print)
            println("Error ${error._2dec} with guess $result, expecting ${it.label}")
    }

    val avgError = (totalError / testing.size)
    if (print)
        println("Average error : $avgError")

    return avgError.toFloat()
}

data class DoodleTrainerGeneticsConfiguration(val hiddenNodes: Int) {

}

fun main() {
    var pop: Population<DoodleTrainerGeneticsConfiguration>? = null
    val timeToSetup = ETimer.time {
        val objects = listOf("dog", "spoon")

        objects.forEach {
            ETimer.time {
                it.doodleList
            }.print
            println()
        }

        val dataMap = objects
            .mapIndexed { index, s -> s.doodleList to index }
            .flatMap { (dataList, label) ->
                dataList.map {
                    LabeledDataOLD(it, label, objects)
                }
            }.shuffled()


        val fitness: (Genome<DoodleTrainerGeneticsConfiguration>) -> Float = {

            1f / it.individual.test(dataMap, objects)
        }
        pop = Population(
            1,
            Runtime.getRuntime().availableProcessors() * 2,
            fitness,
            mutationRate = 0.1f,
            builder = DnaBuilder { dna ->
                DoodleTrainerGeneticsConfiguration(
                    hiddenNodes = lerp(dna[0], 50, 500).toInt()
                )
            }
        )
    }

    println("$timeToSetup ms to setup")

    while (true) {
        ETimer.time {
            val pop = pop!!
            pop.evolve()
            val best = pop.best
            println(
                "Best: ${best.first.individual.hiddenNodes} nodes with error ${(1f / best.second)._2dec}"
            )
        }.print

        println()
    }
}