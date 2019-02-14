package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.core.get
import com.thorebenoit.enamel.kotlin.core.math.random
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.genetics.DnaBuilder
import com.thorebenoit.enamel.kotlin.genetics.Genome
import com.thorebenoit.enamel.kotlin.genetics.randomWithWeight
import com.thorebenoit.enamel.kotlin.geometry.AllocationTracker
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPApplet


object ProcessingTestMain {
    init {
        // TODO Remove and check if allocating debug
        AllocationTracker.debugAllocations = false

    }

    @JvmStatic
    fun main(args: Array<String>) {

        val possibleChar = ('a'..'z').toList() // + ('A'..'Z').toList() + '('
        val builder = DnaBuilder { dna ->
            dna.map {
                possibleChar[it]
            }.joinToString(separator = "")
        }


        val population = Population(_target.length, 10, builder = builder, randomGene = { _, _ -> random() })


        // <//>
        val targetDna = _target.map { it.toInt() - 'a'.toInt() }.map { it / 26f }
        val bestPossible = Genome(targetDna, builder).mutate(0.25)
//        bestPossible.individual.print
//        population.evaluateFitness(bestPossible).print
//        return
        // </ //>

        population.individuals.add(bestPossible)


        fun Population.print() {
            val best = population.createFitnessIndividualMap().maxBy { (g, f) -> f }!!

            best.let { (genome, fitness) ->

                "$fitness -> ${genome.individual}".print
            }
        }

        var i = 0
        while (i < 10_000) {
            population.evolve()
            i++
//            print(i)
            population.print()
        }
//        population.print()


    }

}

private val _target = "benoit"

class Population(
    dnaSize: Int,
    val populationSize: Int,
    builder: DnaBuilder<String>,
    randomGene: (Int, Float) -> Float
) {

    val individuals: MutableList<Genome<String>> =
        MutableList(populationSize) { Genome(dnaSize, builder, randomGene) }

    /**
     * @return the fitness, higher is better
     */
    fun evaluateFitness(genome: Genome<String>): Float {

        var fitness = 0f
        genome.individual.forEachIndexed { i, c ->
            if (c == _target[i]) {
                fitness++
            }
        }
        return fitness // / _target.length
    }

    fun evolve() {

        val map = createFitnessIndividualMap()

        individuals.clear()
        (0 until populationSize).forEach {
            val mommy = map.randomWithWeight()
            val daddy = map.randomWithWeight()
            val child = mommy.reproduce(daddy).mutate(0.01)
            individuals.add(child)
        }
    }


    fun createFitnessIndividualMap(): MutableMap<Genome<String>, Float> =
        individuals.map { it to evaluateFitness(it) }.toMap().toMutableMap()

}

class MainApplet : KotlinPApplet() {


}


