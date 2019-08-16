package com.benoitthore.enamel.geometry.layout.playground

import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.geometry.layout.serializer.ELayoutSerializer
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody


private fun String.toRequestBody(mediaType: String = "text/json") = RequestBody.create(MediaType.parse(mediaType), this)
//adb forward tcp:9321 tcp:9321
class PlaygroundClient(
    private val address: String = "localhost",
    private val serializer: ELayoutSerializer = ELayoutSerializer(),
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