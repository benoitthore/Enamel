package com.thorebenoit.enamel.kotlin.ai.genetics

import com.thorebenoit.enamel.core.math.f
import com.thorebenoit.enamel.core.math.random
import com.thorebenoit.enamel.core.print
import java.lang.Exception


private fun main() {
    val builder = DnaBuilder { dna ->
        dna.joinToString { (it * 100).toInt().toString() }
    }
    val genome = Genome((1..9).map { it / 10f }, builder)

    val other = Genome((1..9).reversed().map { it / 10f }, builder)

    print("Mommy:        ")
    genome.individual.print
    print("Daddy:        ")
    other.individual.print

    print("Child:        ")
    genome.reproduce(other).individual.print
    print("Mutate child: ")
    genome.mutate(0.25f).individual.print

}

class DnaBuilder<T>(val builder: (dna: List<Float>) -> T) {
    fun create(dna: List<Float>): T = builder(dna)
}

class Genome<T>(
    val dna: List<Float>,
    private val builder: DnaBuilder<T>
) {
    constructor(
        dnaSize: Int,
        builder: DnaBuilder<T>,
        randomGene: (Int, Float) -> Float
    ) : this(
        dna = List(dnaSize) { randomGene(it, -1f) },
        builder = builder
    )


    val individual: T by lazy { builder.create(dna) }

//    constructor(dna: List<Number>, builder: DnaBuilder<T>) : this(dna.map { it.toFloat() }.toList<Float>(), builder)

    val genomeSize = dna.size


    // TODO Remove splitHalf
    fun reproduce(other: Genome<T>, splitHalf: Boolean = false): Genome<T> {
        if (genomeSize != other.genomeSize) {
            throw Exception("Genomes must have the same DNA size")
        }
        val newDna = dna.toMutableList()
        val otherDna = other.dna

        val midPoint = newDna.size * random()
        for (i in 0 until newDna.size) {
            if (splitHalf) {
                if (i > midPoint) {
                    newDna[i] = otherDna[i]
                }
            } else {
                if (random() < 0.5f) {
                    newDna[i] = otherDna[i]
                }
            }
        }

        return Genome(newDna, builder)
    }

    fun mutate(mutationRate: Number,newGene: (Int, Float) -> Float = { geneIndex, geneValue -> random() }): Genome<T> {
        val mutationRate = mutationRate.f
        if (mutationRate < 0 || mutationRate > 1) {
            throw Exception("mutationRate must be between 0 and 1")
        }

        val newDna = dna.toMutableList()

        for (i in 0 until newDna.size) {
            if (random() < mutationRate) {
                newDna[i] = newGene(i, newDna[i])
            }
        }

        return Genome(newDna, builder)
    }

    override fun toString(): String {
        return "Genome(individual=$individual)"
    }


}
