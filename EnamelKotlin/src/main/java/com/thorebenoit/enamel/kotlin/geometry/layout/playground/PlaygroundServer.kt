package com.thorebenoit.enamel.kotlin.geometry.layout.playground

import com.fasterxml.jackson.module.kotlin.readValue
import com.thorebenoit.enamel.kotlin.core.data.ignoreUnknownObjectMapper
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ELayoutDeserializer
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ELayoutSerializer
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object PlaygroundServer {
    val defaultPort = 9321

    private var _server: NettyApplicationEngine? = null

    fun start(
        deserializer: ELayoutDeserializer,
        port: Int = defaultPort,
        onError: (Throwable) -> Unit = { System.err.println(it) },
        onNewLayout: (ELayout) -> Unit
    ) {
        _server?.stop(0, 0, TimeUnit.MILLISECONDS)
        _server = embeddedServer(Netty, port = port) {
            routing {
                get("/test") {
                    call.respondText { "Working" }
                }
                post("/") {
                    try {
                        val data = call.receive<String>()

                        println("SOCKET-IN: $data")
                        val layout = deserializer.readLayout(JSONObject(data))
                        onNewLayout(layout)
                    } catch (e: Throwable) {
                        onError(e)
                    }
                }
            }
        }.start(wait = false)
    }
}