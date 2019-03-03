package com.thorebenoit.enamel.kotlin.caching.store

import com.thorebenoit.enamel.kotlin.caching.serializer.Serializer
import com.thorebenoit.enamel.kotlin.caching.serializer.toBytes
import com.thorebenoit.enamel.kotlin.core.tryCatch
import java.io.InputStream
import java.io.OutputStream

interface StoredDataStream<T : Any, S : Any> : StoredData<T, S> {

    val reader: InputStream
    val writer: OutputStream
    val serializer: Serializer<T, S>


    override fun get(): Pair<Long, T?> =
        tryCatch {
            reader.use {
                getStoredTime() to serializer.fromBytes(reader.readBytes())
            }
        }
            ?: 0L to null

    override fun store(value: T) {
        writer.use {
            writer.write(serializer.toBytes(value))
        }
    }
}
