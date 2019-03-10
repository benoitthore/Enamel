package com.thorebenoit.enamel.kotlin.geometry.layout.playground

import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
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
        val url = "http://$address:$defaultPort/"

        val json = ELayoutSerializer.serialize(layout)
        ELayoutSerializer.deserialize(json)
        client.newCall(Request.Builder().url(url).post(json.toRequestBody()).build()).execute()
    }

}


fun ELayout.sendToPlayground() {
    PlaygroundClient.defaultClient.sendToPlayground(this)
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


// TODO This doesn't work in KTS file
layout.sendToPlayground()
 */