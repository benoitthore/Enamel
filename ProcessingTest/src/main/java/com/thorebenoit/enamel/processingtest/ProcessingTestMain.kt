package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.core._2dec
import com.thorebenoit.enamel.kotlin.core.get
import com.thorebenoit.enamel.kotlin.core.math.f
import com.thorebenoit.enamel.kotlin.core.math.random
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.core.time.ETimer
import com.thorebenoit.enamel.kotlin.genetics.DnaBuilder
import com.thorebenoit.enamel.kotlin.genetics.Genome
import com.thorebenoit.enamel.kotlin.genetics.randomWithWeight
import com.thorebenoit.enamel.kotlin.geometry.AllocationTracker
import com.thorebenoit.enamel.kotlin.geometry.alignement.NamedPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EAngleType
import com.thorebenoit.enamel.kotlin.geometry.primitives.rotation
import com.thorebenoit.enamel.kotlin.geometry.toRect
import com.thorebenoit.enamel.processingtest.examples.steering.DotDrawer
import com.thorebenoit.enamel.processingtest.examples.steering.DotDrawingApplet
import com.thorebenoit.enamel.processingtest.examples.steering.SteeringVehicle
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPApplet
import jdk.nashorn.internal.objects.Global.Infinity
import java.util.*
import kotlin.math.pow


object ProcessingTestMain {
    init {
        // TODO Remove and check if allocating debug
        AllocationTracker.debugAllocations = false
    }

    data class DnaV(val steeringVehicle: SteeringVehicle, val steeringData: List<EAngleType>)

    @JvmStatic
    fun main(args: Array<String>) {

        val applet: DotDrawer = KotlinPApplet.createApplet<DotDrawingApplet>()

        val evaluateFitness: (Genome<DnaV>) -> Float = { genome ->
            val vehicle = genome.individual.steeringVehicle

            vehicle.position.set(applet.size.toRect().pointAtAnchor(NamedPoint.center))

            genome.individual.steeringData.forEach {

                vehicle.controller.steerAngle(it)
                vehicle.body.update()
                applet.dotList = listOf(vehicle)
//                Thread.sleep(1)
            }

            (1f / genome.individual.steeringVehicle.position.y).pow(2)
        }
        val steerPop = Population<DnaV>(
            dnaSize = 200,
            populationSize = 20,
            builder = DnaBuilder { floats -> DnaV(SteeringVehicle(), floats.map { it.rotation() }) },
            evaluateFitness = evaluateFitness
        )


        steerPop.doEvolution()


//        val possibleChar = ('a'..'z').toList() + ' ' + ('A'..'Z').toList()
//        val builder = DnaBuilder { dna ->
//            dna.map {
//                possibleChar[it]
//            }.joinToString(separator = "")
//        }
//
//
//
//
//        (0..10).forEach {
//
//            val t = ETimer()
//            val population = Population(_target.length, 1000, builder = builder, randomGene = { _, _ -> random() })
//
//            var i = 0
//            var found = false
//            while (!found) {
//                val map = population.evolve()
//
////                println()
////                print("$i ")
////                population.best?.let { (genome, fitness) ->
////                    "${(fitness / population.maxFitness)._2dec}-> ${genome.individual}".print
////                }
//
//
//                if (_target == population.best?.first?.individual) {
//                    "Found after $i tries           ${population.best}".print
//                    found = true
//                }
//
//                i++
//            }
//
//            t.print
//        }


    }

}

private fun <T> Population<T>.doEvolution() {
    while (true) {
        evolve()
        evaluateFitness(best!!.first)
        "generation".print
    }
}

//private val _target = "Darwin is winning"

class Population<T>(
    dnaSize: Int,
    populationSize: Int,
    val evaluateFitness: (Genome<T>) -> Float,
    builder: DnaBuilder<T>,
    randomGene: (Int, Float) -> Float = { _, _ -> random() }
) {

    val individuals: MutableList<Genome<T>> =
        MutableList(populationSize) { Genome(dnaSize, builder, randomGene) }

    /**
     * @return the fitness, higher is better
     */
//    fun evaluateFitness(genome: Genome<String>): Float {
//
//        var fitness = 0f
//        genome.individual.forEachIndexed { i, c ->
//            if (c == _target[i]) {
//                fitness++
//            }
//        }
//        return fitness.pow(2) // / _target.length
//    }
//
//    val maxFitness = _target.length.f.pow(4)

    var best: Pair<Genome<T>, Float>? = null

    fun evolve(): MutableMap<Genome<T>, Float> {

        var i = 0

        val map = createFitnessIndividualMap()

        val avgFitness = map.map { it.value }.average().toFloat()

        val size = individuals.size
        individuals.clear()
        (0 until size).forEach {
            var m = map.randomWithWeight()
            var d = map.randomWithWeight()
            // TODO Fix this
            while (m.value < avgFitness || d.value < avgFitness) {
                m = map.randomWithWeight()
                d = map.randomWithWeight()
                i++
            }

            val child = m.key.reproduce(d.key).mutate(0.01)
            individuals.add(child)


            // DEBUG
            val (mommy, mommyFitness) = m
            val (daddy, daddyFitness) = d

            val childFitness = evaluateFitness(child)

            if (mommyFitness < avgFitness && daddyFitness < avgFitness) {
                println("regression")
            }


        }

//        println("$i extra steps")
        return map
    }


    fun createFitnessIndividualMap(): MutableMap<Genome<T>, Float> =
        individuals.map {

            val score = evaluateFitness(it)

            (it to score).also {
                if (score > best?.second ?: -Infinity.f) {
                    best = it
                }
            }

        }.toMap().toMutableMap()

}

class MainApplet : KotlinPApplet() {


}


