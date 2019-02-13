package com.thorebenoit.enamel.kotlin.genetics

import com.thorebenoit.enamel.kotlin.core._2dec
import com.thorebenoit.enamel.kotlin.core.math.random
import com.thorebenoit.enamel.kotlin.core.print
import java.lang.Exception


private fun main() {
    val builder = { dna: FloatArray ->
        dna.joinToString { it._2dec }
    }

    val genome = Genome((0..10).map { 1 }, builder)

    val other = Genome((0..10).map { 0 }, builder)

    genome.reproduce(other).create().print
    genome.mutate(0.1f, 1f).create().print
}

class Genome<T>(private val dna: FloatArray, private val create: (FloatArray) -> T) {

    constructor(dna: List<Number>, create: (FloatArray) -> T) : this(dna.map { it.toFloat() }.toFloatArray(), create)

    val genomeSize = dna.size

    fun getDnaCopy() = dna.copyOf()

    fun create() = create(dna.copyOf())


    fun reproduce(other: Genome<T>): Genome<T> {
        if (genomeSize != other.genomeSize) {
            throw Exception("Genomes must have the same DNA size")
        }
        val copy = getDnaCopy()
        val otherDna = other.getDnaCopy()

        for (i in 0 until copy.size) {
            if (random() < 0.5f) {
                copy[i] = otherDna[i]
            }
        }

        return Genome(copy, create)
    }

    fun mutate(mutationChance: Float, mutationRate: Float): Genome<T> {
        if (mutationChance < 0 || mutationChance > 1) {
            throw Exception("mutationChance must be between 0 and 1")
        }

        val copy = getDnaCopy()

        for (i in 0 until copy.size) {
            if (random() < mutationChance) {
                val mutateBy = copy[i] * random(-mutationRate, mutationRate)
                copy[i] += mutateBy
            }
        }

        return Genome(copy, create)
    }
}
