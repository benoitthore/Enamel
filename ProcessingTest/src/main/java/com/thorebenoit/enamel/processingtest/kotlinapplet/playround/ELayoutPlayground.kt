package com.thorebenoit.enamel.processingtest.kotlinapplet.playround

import com.thorebenoit.enamel.kotlin.core.data.toJson
import com.thorebenoit.enamel.kotlin.core.of
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.figures.size
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayoutLeaf
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.*
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ELayoutSerializer
import com.thorebenoit.enamel.kotlin.network.createService
import com.thorebenoit.enamel.kotlin.network.toRequestBody
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPApplet
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPAppletLambda
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

// KTS
/*
import com.thorebenoit.enamel.kotlin.core.of
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.figures.ESize
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayoutLeaf
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.*
import com.thorebenoit.enamel.kotlin.geometry.toRect
import com.thorebenoit.enamel.processingtest.kotlinapplet.playround.sendToPlayground

val layout = 3.of { ELayoutLeaf() }
    .mapIndexed { i, layout ->
        layout.sizedSquare((i + 1) * 100)
    }
    .stacked(EAlignment.rightTop, spacing = 10)
    .snugged()
    .arranged(EAlignment.topLeft)
    .padded(20)

// TODO This doesn't work in KTS file
layout.sendToPlayground()
 */

    val _layout = 3.of { ELayoutLeaf() }
        .mapIndexed { i, layout ->
            layout.sizedSquare((i + 1) * 100)
        }
        .stacked(EAlignment.rightTop, spacing = 10)
        .snugged()
        .arranged(EAlignment.topLeft)
        .padded(20)


//val _layout = ELayoutLeaf().arranged(EAlignment.middle)

class ELayoutPlayground : KotlinPAppletLambda() {

    private var layout: ELayout = _layout

//    private var layout: ELayout = 3.of { ELayoutLeaf() }
//        .mapIndexed { i, layout ->
//            layout.sizedSquare((i + 1) * 100)
//        }
//        .stacked(EAlignment.rightTop, spacing = 10)
//        .snugged()
//        .arranged(EAlignment.topLeft)
//        .padded(20)

    init {

        // TODO Layout doesn't work once passed through the network, check serialization
        createPlaygroundServer {
            this.layout.print
            it.print
            this.layout = it
        }

        onDraw {
            background(255)
            layout.arrange(eframe)
            layout.draw()
        }
    }


}

fun main() {

    KotlinPApplet.createApplet<ELayoutPlayground>(800 size 800)



    _layout.sendToPlayground()

}

private val defaultPort = 9824
fun createPlaygroundServer(port: Int = defaultPort, onNewLayout: (ELayout) -> Unit) {
    embeddedServer(Netty, port = port) {
        routing {
            post("/") {
                val str = call.receive<String>()
                val layout = ELayoutSerializer.deserialize(str)
                println("SOCKET-IN: $str")
                onNewLayout(layout)
            }
        }
    }.start(wait = false)
}

val playgroundService = createService<PlaygroundService>("http://localhost:$defaultPort", converterFactory = null)

fun ELayout.sendToPlayground() {
    val json = ELayoutSerializer.serialize(this)
    runBlocking {
        playgroundService.sendData(json.toRequestBody()).await()
    }
}

interface PlaygroundService {
    @POST("/")
    fun sendData(@Body json: RequestBody): Deferred<Response<ResponseBody>>
}