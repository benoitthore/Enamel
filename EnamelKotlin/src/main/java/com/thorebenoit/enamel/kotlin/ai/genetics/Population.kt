package com.thorebenoit.enamel.kotlin.ai.genetics

import com.thorebenoit.enamel.kotlin.core.math.random
import com.thorebenoit.enamel.kotlin.threading.forEachParallel

class Population<T>(
    val dnaSize: Int,
    val populationSize: Int,
    val evaluateFitness: (Genome<T>) -> Float, // do the required test and return the fitness, higher is better
    val mutationRate: Float = 0.01f,
    builder: DnaBuilder<T>,
    val initGene: (Int, Float) -> Float = { _, _ -> random() },
    val updateGene: (Int, Float) -> Float = { _, _ -> random() }
) {

    val individuals: MutableList<Genome<T>> =
        MutableList(populationSize) { Genome(dnaSize, builder, initGene) }

    var best: Pair<Genome<T>, Float> = individuals.random() to -Float.MAX_VALUE
        private set
    var generationCount = 0
        private set


    fun evolve(): MutableMap<Genome<T>, Float> {

        var i = 0

        val map = createFitnessIndividualMap()

        val avgFitness = map.map { it.value }.average().toFloat()

        val size = individuals.size

        individuals.clear()


        (0 until size).toList().forEach {

            var m = map.randomWithWeight()
            var d = map.randomWithWeight()
            // TODO Fix this
            while (m.value < avgFitness || d.value < avgFitness) {
                m = map.randomWithWeight()
                d = map.randomWithWeight()
                i++
            }

            val (mommy, mommyFitness) = m
            val (daddy, daddyFitness) = d
            val child = mommy.reproduce(daddy).mutate(mutationRate, updateGene)
            individuals.add(child)


            // DEBUG

//            val childFitness = evaluateFitness(child)

            if (mommyFitness < avgFitness && daddyFitness < avgFitness) {
                println("regression")
            }


        }

        generationCount++

        return map
    }

    fun createFitnessIndividualMap(): MutableMap<Genome<T>, Float> {

        val map: MutableMap<Genome<T>, Float> = mutableMapOf()
        individuals.forEachParallel {

            val score = evaluateFitness(it)

            val e = (it to score).also {
                if (score > best.second) {
                    best = it
                }
            }
            map += e
        }

        return map
    }

}