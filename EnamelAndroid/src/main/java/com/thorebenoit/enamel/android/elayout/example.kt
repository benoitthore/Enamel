package com.thorebenoit.enamel.android.elayout

import com.thorebenoit.enamel.kotlin.core.math.i
import com.thorebenoit.enamel.kotlin.core.math.random
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment.*
import com.thorebenoit.enamel.kotlin.geometry.alignement.ERectEdge
import com.thorebenoit.enamel.kotlin.geometry.alignement.ERectEdge.*
import com.thorebenoit.enamel.kotlin.geometry.layout.EDivideLayout
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.*
import com.thorebenoit.enamel.kotlin.geometry.layout.playground.PlaygroundClient
import com.thorebenoit.enamel.kotlin.geometry.layout.playground.sendToPlayground
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

private fun ELayout.sendToAndroid() = this.
    // Apply DP
    scaled(3, 3)
    // Send to Android
    .sendToPlayground(pgAndroid)


//val pgAndroid = PlaygroundClient("192.168.2.149")
private val pgAndroid = PlaygroundClient("localhost")
//val pgProcessing = PlaygroundClient.defaultClient

private val randomAlign get() = EAlignment.all.toMutableList().random()
//private val randomAlign get() = EAlignment.all.toMutableList().apply { remove(middle) }.random()


private val A = "A".layoutTag
private val B = "B".layoutTag
private val C = "C".layoutTag
private val D = "D".layoutTag
private val E = "E".layoutTag
private val F = "F".layoutTag

val list = listOf(A, B, C, D, E, F)


fun main() {
    PlaygroundClient.defaultClient = pgAndroid

    while (true) {
        list
//        .shuffled()
            .map {
                it.sized(random(50, 150), random(50, 150))
//            it.sizedSquare(100)
            }
            .stacked(randomAlign, 32)
//            .snugged()
            .padded(32)
            .arranged(topLeft)
            .sendToAndroid()
        Thread.sleep(1500)
    }

//    listOf(A, B, C)
//        .stackedBottomRight()
//
//    return
//    workingLayout()
//    return

///////
///////
///////
///////

}

private fun workingLayout() {
    list
//        .shuffled()
//        .map {
//            it.sized(random(50, 150), random(50, 150))
//            it.sizedSquare(100)
//        }
        .stacked(bottomLeft, 32)
        .snugged()
        .padded(32)
        .arranged(topLeft)
        .sendToAndroid()


}

