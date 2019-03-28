package com.thorebenoit.enamel.kotlin.geometry.layout.playground

import com.fasterxml.jackson.module.kotlin.readValue
import com.thorebenoit.enamel.kotlin.core.data.ignoreUnknownObjectMapper
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ELayoutDeserializer
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ELayoutSerializer
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.digital.ELayoutDeserializerDigital
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.digital.ELayoutSerializerDigital
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

class PlaygroundServer {
    companion object {
        val defaultPort = 9327
    }

    fun start(
        port: Int = defaultPort,
        newInstance: (Class<ELayout>) -> ELayout = { it.newInstance() },
        onNewLayout: (ELayout) -> Unit
    ) {
        val mapper = ignoreUnknownObjectMapper()
        embeddedServer(Netty, port = port) {
            routing {
                get("/test") {
                    call.respondText { "Working" }
                }
                post("/") {
                    try {
                        val json = call.receive<String>()
                        val data = mapper.readValue<List<Number>>(json)

                        println("SOCKET-IN: $data")
                        val layout = ELayoutDeserializerDigital.createIntIDDesrializer(data, newInstance)
                        onNewLayout(layout.readLayout())
                    } catch (e: Throwable) {
                        System.err.println(e)
                        throw e
                    }
                }
            }
        }.start(wait = false)
    }
}