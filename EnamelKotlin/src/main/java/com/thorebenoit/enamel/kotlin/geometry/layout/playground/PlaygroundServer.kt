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
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

class PlaygroundServer {
    companion object {
        val defaultPort = 9824
    }

    fun start(port: Int = defaultPort, onNewLayout: (ELayout) -> Unit) {
        val mapper = ignoreUnknownObjectMapper()
        embeddedServer(Netty, port = port) {
            routing {
                post("/") {
                    try {
                        val json = call.receive<String>()
                        val data = mapper.readValue<List<Number>>(json)

                        println("SOCKET-IN: $data")
                        val layout = ELayoutDeserializerDigital.createIntIDDesrializer(data)
                        onNewLayout(layout.readLayout())
                    } catch (e: Throwable) {
                        System.err.println(e)
                    }
                }
            }
        }.start(wait = false)
    }
}