package com.thorebenoit.enamel.kotlin.geometry.layout.playground

import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ELayoutSerializer
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
        embeddedServer(Netty, port = port) {
            routing {
                post("/") {
                    try {
                        val str = call.receive<String>()
                        val layout = ELayoutSerializer.deserialize(str)
                        println("SOCKET-IN: $str")
                        onNewLayout(layout)
                    } catch (e: Throwable) {
                        System.err.println(e)
                    }
                }
            }
        }.start(wait = false)
    }
}