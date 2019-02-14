package com.thorebenoit.enamel.kotlin.genetics

import com.thorebenoit.enamel.kotlin.core.math.f
import com.thorebenoit.enamel.kotlin.core.math.random
import kotlin.math.roundToInt

fun randomFloatArray(n: Int) = (0 until n).map { random() }.toFloatArray()


// randomWithWeight
private fun main(){
    val map = mapOf(
        "50%" to .50f,
        "25%" to .25f,
        "10%" to .10f,
        "5%" to .05f
    )



    val testList = mutableListOf<String>()
    (0 .. 100_000).map { map.randomWithWeight() }.forEach {
        testList += it
    }
    map.keys.forEach { k->
        val ratio = (100 * testList.filter { it == k }.size / testList.size.f).roundToInt()
        println("$k=$ratio%")

    }
}

fun <T : Any, N : Number> Map<T, N>.randomWithWeight(): T {
    val totalWeight = values.asSequence().map { it.f }.sum()
    var value = random(0, totalWeight)
    forEach { (o, w) ->
        value -= w.f
        if (value < 0) {
            return o
        }
    }

    return toList().random().first
}

fun <T : Any, N : Number> MutableMap<T, N>.pollRandomWithWeight(): T {
    val totalWeight = values.asSequence().map { it.f }.sum()
    var value = random(0, totalWeight)
    forEach { (o, w) ->
        value -= w.f
        if (value < 0) {
            remove(o)
            return o
        }
    }

    return toList().random().first.apply { remove(this) }
}