package com.thorebenoit.enamel.processingtest.examples.genetics

import com.thorebenoit.enamel.kotlin.core.backingfield.ExtraValueHolder
import com.thorebenoit.enamel.kotlin.core.math.lerp
import com.thorebenoit.enamel.kotlin.core.math.random
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.core.time.ETimer
import com.thorebenoit.enamel.kotlin.genetics.Genome
import com.thorebenoit.enamel.kotlin.genetics.pollRandomWithWeigth
import com.thorebenoit.enamel.kotlin.genetics.randomFloatArray
import com.thorebenoit.enamel.kotlin.genetics.randomWithWeigth
import com.thorebenoit.enamel.kotlin.geometry.alignement.NamedPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.*
import com.thorebenoit.enamel.kotlin.threading.coroutine
import com.thorebenoit.enamel.processingtest.examples.steering.DotDrawer
import com.thorebenoit.enamel.processingtest.examples.steering.SteeringVehicle
import kotlin.math.pow

class GeneticPresenter(val view: DotDrawer) {

    init {

        val population = Population()


        view.onSized = {
            kotlin.run {

                /*
                // TODO Mark warning if not used on non-self functions in points/rect/circle/etc
               TODO The whole thing doesn't work

               Assume that pollRandomWithWeigth is working
               check https://www.youtube.com/watch?v=bGz7mv2vD6g&list=PLRqwX-V7Uu6bJM3VgzjNV5YxVxUwzALHV&index=11&t=1117

              If still not working, try the Shakespeare example and generalise
                 */

                //            val startPoint = view.constraintFrame.pointAtAnchor(NamedPoint.bottomCenter)
                val startPoint = view.constraintFrame.pointAtAnchor(NamedPoint.center)


                val timer = ETimer()
                while (true) {
                    timer.start()

                    // Evaluate
                    val individuals = mutableListOf<SteeringIndividual>()

                    population.createIndividuals().apply { "created".print }.forEach { individual ->

                        individual.vehicle.position.set(startPoint)

                        view.dotList = listOf(individual.vehicle)

                        individual.data.angles.forEach {

                            individual.vehicle.controller.steerAngle(it)
                            individual.vehicle.body.update()

                            // TODO View update
                            view.update()
                            Thread.sleep(16)
                        }

                        individuals += individual

                    }


                    // Calculate Fitness
                    val fitnessMap = population.calculateFitness(individuals)

                    with(fitnessMap.values) {
                        this.average().print
                    }

                    population.clear()

                    // Selection
                    val size = fitnessMap.size
                    (0 until size / 2).forEach {
                        val male = fitnessMap.randomWithWeigth()
                        val female = fitnessMap.randomWithWeigth()


                        val child1 = male.data.genome.reproduce(female.data.genome).mutate(0.05f)
                        val child2 = male.data.genome.reproduce(female.data.genome).mutate(0.05f)

                        population.add(child1)
                        population.add(child2)
                    }

                    println("generation $timer")
                    println("---------------")

                }
            }
        }

    }
}

class SteeringIndividual(val data: SteeringData, val vehicle: SteeringVehicle = SteeringVehicle(torque = 2f))

class Population(nbInviduals: Int = 200, lifeSpan: Int = 200) {
    // initialise with random values
    val dnaList = (0 until nbInviduals).map {
        Genome(randomFloatArray(lifeSpan))
    }.toMutableList()

    fun createIndividuals() = dnaList.map { SteeringIndividual(SteeringData(it)) }

    fun calculateFitness(individuals: List<SteeringIndividual>) =
        individuals.map { it to (1f / it.vehicle.position.y).pow(2) }.toMap().toMutableMap()

    fun clear() {
        dnaList.clear()
    }

    fun add(dna: Genome) {
        dnaList += dna
    }
}

class SteeringData(val genome: Genome) {
    val angles: List<EAngleType> = genome.getDnaCopy().map {
//        lerp(it,0,-0.5f).rotation()
//        (-0.25).rotation()
        it.rotation()
    }
}