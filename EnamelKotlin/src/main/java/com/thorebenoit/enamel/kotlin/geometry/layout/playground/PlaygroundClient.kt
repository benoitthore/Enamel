package com.thorebenoit.enamel.kotlin.geometry.layout.playground

import com.thorebenoit.enamel.kotlin.core.math.random
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.*
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ELayoutSerializer
import com.thorebenoit.enamel.kotlin.network.toRequestBody
import okhttp3.OkHttpClient
import okhttp3.Request


//adb forward tcp:9327 tcp:9327
class PlaygroundClient(
    private val serializer: ELayoutSerializer = ELayoutSerializer(),
    private val address: String = "localhost",
    private val defaultPort: Int = PlaygroundServer.defaultPort
) {

    companion object {
        var defaultClient = PlaygroundClient()
    }

    private val client = OkHttpClient.Builder()
//    .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        .build()

    fun sendToPlayground(layout: ELayout) {

        val url = "http://$address:$defaultPort/"

        val json = serializer.serialize(layout).toString()
        client.newCall(Request.Builder().url(url).post(json.toRequestBody()).build()).execute()
    }

}


fun <T : ELayout> T.sendToPlayground(playground: PlaygroundClient = PlaygroundClient.defaultClient): T {
    playground.sendToPlayground(this)
    return this
}

//////////////////
//////////////////
//////////////////
//////////////////

private val kotlin.Number.dp get() = toFloat() * 3
fun main() {
    val pgAndroid = PlaygroundClient(address = "192.168.2.149")
    val pgProcessing = PlaygroundClient.defaultClient

    PlaygroundClient.defaultClient = pgAndroid

    val list = listOf("A", "B", "C")
//val list = listOf<String>()

    list
//    .shuffled()
//    .subList(0, random(list.size).i + 1)
        .layoutTag
        .map {
            it.sized(random(50, 150).dp, random(50, 150).dp)
        }
        .stackedRightCenter()
        .snugged()
        .arranged(EAlignment.topLeft)
//    .padded(left = 20.dp)
        .sendToPlayground(pgProcessing)
        .sendToPlayground(pgAndroid)

}
