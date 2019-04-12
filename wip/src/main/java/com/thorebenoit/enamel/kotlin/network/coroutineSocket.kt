package com.thorebenoit.enamel.kotlin.network

import com.thorebenoit.enamel.core.print
import com.thorebenoit.enamel.core.threading.singleThreadCoroutine
import com.thorebenoit.enamel.core.toStringFromBytes
import com.thorebenoit.enamel.kotlin.time.seconds
import kotlinx.coroutines.io.jvm.javaio.toByteReadChannel
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.net.ServerSocket
import java.net.Socket


fun main() {
    coroutineServer {
        it.print
        if (it == 10.toByte()) {
            return@coroutineServer "ABC".toByteArray()
        }
        null
    }

    val byteList = mutableListOf<Byte>()
    coroutineClient(onConnect = { (0 until 100).map { it.toByte() }.toByteArray() }) {

        byteList += it
        if (byteList.size == 3) {
            byteList.toStringFromBytes().print
        }

        null
    }

    Thread.sleep(10.seconds)
}

fun coroutineClient(
    host: String = "localhost",
    port: Int = 8080,
    onConnect: (() -> ByteArray)? = null,
    onNext: (Byte) -> ByteArray?
) {
    singleThreadCoroutine {
        Socket(host, port).handle(onConnect, onNext)
    }
}

fun coroutineServer(
    port: Int = 8080,
    shouldRun: () -> Boolean = { true },
    onConnect: (() -> ByteArray)? = null,
    onNext: (Byte) -> ByteArray?
) {
    singleThreadCoroutine {
        val server = ServerSocket(port)

        while (shouldRun()) {
            val socket = server.accept()
            socket.handle(onConnect, onNext)
        }


    }
}

private suspend fun Socket.handle(onStart: (() -> ByteArray)? = null, onNext: (Byte) -> ByteArray?) {


    onStart?.invoke()?.let { getOutputStream().write(it); getOutputStream().flush() }

    BufferedInputStream(getInputStream()).use { isStream ->

        val byteChannel = isStream.toByteReadChannel()
        BufferedOutputStream(getOutputStream()).use { osStream ->
            while (true) {
                val response = onNext(byteChannel.readByte())
                if (response != null) {
                    osStream.write(response)
                    osStream.flush()
                }
            }
        }

    }
}
