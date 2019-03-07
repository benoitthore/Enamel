package com.thorebenoit.enamel.kotlin.network

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.features.origin
import io.ktor.html.respondHtml
import io.ktor.http.ContentType
import io.ktor.http.content.files
import io.ktor.http.content.staticRootFolder
import io.ktor.http.formUrlEncode
import io.ktor.http.fromFilePath
import io.ktor.request.*
import io.ktor.response.respondRedirect
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.util.combineSafe
import io.ktor.util.flattenEntries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.html.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

fun File.startFileServer(port: Int = 8080) {
    val root = this.takeIf { it.exists() }
        ?: File("files").takeIf { it.exists() }
        ?: error("Can't locate files folder")

    embeddedServer(Netty, port = port) {
        install(DefaultHeaders)
        install(CallLogging)
        routing {
            get("/") {
                call.respondRedirect("/files")
            }
            get("/info") {
                call.respondInfo()
            }
            route("/files") {
                files(root)
                listing(root)
            }
        }
    }.start(wait = true)
}

private suspend fun ApplicationCall.respondInfo() {
    fun TABLE.row(key: String, value: Any?) {
        tr {
            th { +key }
            td { +value.toString() }
        }
    }

    respondHtml {
        body {
            style {
                +"""
                    table {
                        font: 1em Arial;
                        border: 1px solid black;
                        width: 100%;
                    }
                    th {
                        background-color: #ccc;
                        width: 200px;
                    }
                    td {
                        background-color: #eee;
                    }
                    th, td {
                        text-align: left;
                        padding: 0.5em 1em;
                    }
                """.trimIndent()
            }
            h1 {
                +"Ktor info"
            }
            h2 {
                +"Info"
            }
            table {
                row("request.httpVersion", request.httpVersion)
                row("request.httpMethod", request.httpMethod)
                row("request.uri", request.uri)
                row("request.path()", request.path())
                row("request.host()", request.host())
                row("request.document()", request.document())
                row("request.location()", request.location())
                row("request.queryParameters", request.queryParameters.formUrlEncode())

                row("request.userAgent()", request.userAgent())

                row("request.accept()", request.accept())
                row("request.acceptCharset()", request.acceptCharset())
                row("request.acceptCharsetItems()", request.acceptCharsetItems())
                row("request.acceptEncoding()", request.acceptEncoding())
                row("request.acceptEncodingItems()", request.acceptEncodingItems())
                row("request.acceptLanguage()", request.acceptLanguage())
                row("request.acceptLanguageItems()", request.acceptLanguageItems())

                row("request.authorization()", request.authorization())
                row("request.cacheControl()", request.cacheControl())

                row("request.contentType()", request.contentType())
                row("request.contentCharset()", request.contentCharset())
                row("request.isChunked()", request.isChunked())
                row("request.isMultipart()", request.isMultipart())

                row("request.ranges()", request.ranges())
            }

            for ((name, value) in listOf(
                "request.local" to request.local,
                "request.origin" to request.origin
            )) {
                h2 {
                    +name
                }
                table {
                    row("$name.version", value.version)
                    row("$name.method", value.method)
                    row("$name.scheme", value.scheme)
                    row("$name.host", value.host)
                    row("$name.port", value.port)
                    row("$name.remoteHost", value.remoteHost)
                    row("$name.uri", value.uri)
                }
            }

            for ((name, parameters) in listOf(
                "Query parameters" to request.queryParameters,
                "Headers" to request.headers
            )) {
                h2 {
                    +name
                }
                if (parameters.isEmpty()) {
                    +"empty"
                } else {
                    table {
                        for ((key, value) in parameters.flattenEntries()) {
                            row(key, value)
                        }
                    }
                }
            }

            h2 {
                +"Cookies"
            }
            table {
                for ((key, value) in request.cookies.rawCookies) {
                    row(key, value)
                }
            }
        }
    }
}

private fun Route.listing(folder: File) {
    val dir = staticRootFolder.combine(folder)
    val pathParameterName = "static-content-path-parameter"
    val dateFormat = SimpleDateFormat("dd-MMM-YYYY HH:mm")
    get("{$pathParameterName...}") {
        val relativePath = call.parameters.getAll(pathParameterName)?.joinToString(File.separator) ?: return@get
        val file = dir.combineSafe(relativePath)
        if (file.isDirectory) {
            val isRoot = relativePath.trim('/').isEmpty()
            val files = file.listSuspend(includeParent = !isRoot)
            val base = call.request.path().trimEnd('/')
            call.respondHtml {
                body {
                    h1 {
                        +"Index of $base/"
                    }
                    hr {}
                    table {
                        style = "width: 100%;"
                        thead {
                            tr {
                                for (column in listOf("Name", "Last Modified", "Size", "MimeType")) {
                                    th {
                                        style = "width: 25%; text-align: left;"
                                        +column
                                    }
                                }
                            }
                        }
                        tbody {
                            for (finfo in files) {
                                val rname = if (finfo.directory) "${finfo.name}/" else finfo.name
                                tr {
                                    td {
                                        if (finfo.name == "..") {
                                            a(File(base).parent) { +rname }
                                        } else {
                                            a("$base/$rname") { +rname }
                                        }
                                    }
                                    td {
                                        +dateFormat.format(finfo.date)
                                    }
                                    td {
                                        +(if (finfo.directory) "-" else "${finfo.size}")
                                    }
                                    td {
                                        +(ContentType.fromFilePath(finfo.name).firstOrNull()?.toString() ?: "-")
                                    }
                                }
                            }
                        }
                    }
                    hr {}
                }
            }
        }
    }
}

private fun File?.combine(file: File) = when {
    this == null -> file
    else -> resolve(file)
}

private data class FileInfo(val name: String, val date: Date, val directory: Boolean, val size: Long)

private suspend fun File.listSuspend(includeParent: Boolean = false): List<FileInfo> {
    val file = this
    return withContext(Dispatchers.IO) {
        listOfNotNull(if (includeParent) FileInfo("..", Date(), true, 0L) else null) + file.listFiles().toList().map {
            FileInfo(it.name, Date(it.lastModified()), it.isDirectory, it.length())
        }.sortedWith(comparators(
            Comparator { a, b -> -a.directory.compareTo(b.directory) },
            Comparator { a, b -> a.name.compareTo(b.name, ignoreCase = true) }
        ))
    }
}

private fun <T> comparators(vararg comparators: Comparator<T>): Comparator<T> {
    return Comparator { l, r ->
        for (comparator in comparators) {
            val result = comparator.compare(l, r)
            if (result != 0) return@Comparator result
        }
        return@Comparator 0
    }
}

private operator fun <T> Comparator<T>.plus(other: Comparator<T>): Comparator<T> = comparators(this, other)
