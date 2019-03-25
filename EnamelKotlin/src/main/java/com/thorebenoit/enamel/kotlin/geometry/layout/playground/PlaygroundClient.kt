package com.thorebenoit.enamel.kotlin.geometry.layout.playground

import com.thorebenoit.enamel.kotlin.core.color.red
import com.thorebenoit.enamel.kotlin.core.math.random
import com.thorebenoit.enamel.kotlin.core.of
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.alignement.ERectEdge
import com.thorebenoit.enamel.kotlin.geometry.layout.EDivideLayout
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayoutLeaf
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.*
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ELayoutSerializer
import com.thorebenoit.enamel.kotlin.network.toRequestBody
import okhttp3.OkHttpClient
import okhttp3.Request

class PlaygroundClient(private val address: String = "localhost", val defaultPort: Int = PlaygroundServer.defaultPort) {

    companion object {
        var defaultClient = PlaygroundClient()
    }

    private val client = OkHttpClient.Builder()
//    .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        .build()

    fun sendToPlayground(layout: ELayout) {
        TODO("This code is now invalid")
//        val url = "http://$address:$defaultPort/"
//
//        val json = ELayoutSerializer.serialize(layout)
//        ELayoutSerializer.deserialize(json)
//        client.newCall(Request.Builder().url(url).post(json.toRequestBody()).build()).execute()
    }

}


fun <T : ELayout> T.sendToPlayground(): T {
    PlaygroundClient.defaultClient.sendToPlayground(this)
    return this
}

fun main() {
    val layout =
        5.of {
            ELayoutLeaf().sized(random(10,50),random(10,100))
        }
            .justified(EAlignment.leftCenter)
            .snugged()
            .arranged(EAlignment.topLeft)
            .padded(5)

//    val origin = ELayoutLeaf(red).sized(200, 200).arranged(EAlignment.topLeft, snugged = true)
//    val layout = ELayoutLeaf().sized(100,100).aligned(ERectEdge.bottom, of = origin,sizedBy = EDivideLayout.Division.Fraction(0.5f))

    layout.sendToPlayground()
}
//KTS Example
/*
import com.thorebenoit.enamel.kotlin.core.color.*
import com.thorebenoit.enamel.kotlin.core.of
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.alignement.ERectEdge
import com.thorebenoit.enamel.kotlin.geometry.figures.ESize
import com.thorebenoit.enamel.kotlin.geometry.layout.EDivideLayout
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayoutLeaf
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.*
import com.thorebenoit.enamel.kotlin.geometry.toRect
import com.thorebenoit.enamel.kotlin.geometry.layout.playground.sendToPlayground
import koma.pow

val layout = 10.of { ELayoutLeaf() }
    .mapIndexed { i, layout ->
//        layout.sizedSquare((i + 1).pow(2) * 5)
//        layout.sizedSquare((i + 1) * 15)
        layout.sizedSquare(25)
    }
    .justified(EAlignment.leftCenter)
//    .stacked(EAlignment.leftBottom, spacing = 5)
    .snugged()
    .arranged(EAlignment.topLeft)
    .padded(20)


//val layout1 = ELayoutLeaf(red).sized(200, 200)
//val layout2 = ELayoutLeaf(blue).sized(200, 200)
//
//val layout = EDivideLayout(layout1, layout2, EDivideLayout.Division.Fraction(0.9f), ERectEdge.right)
//    .arranged(EAlignment.topLeft)


layout.sendToPlayground()
 */