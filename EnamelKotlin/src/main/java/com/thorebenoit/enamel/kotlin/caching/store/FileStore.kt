package com.thorebenoit.enamel.kotlin.caching.store

import com.thorebenoit.enamel.kotlin.caching.serializer.JSONSerializer
import com.thorebenoit.enamel.kotlin.caching.serializer.Serializer
import com.thorebenoit.enamel.kotlin.caching.serializer.StringSerializer
import java.io.*

class FileStore<T : Any>(
    private val file: File,
    override val serializer: Serializer<T, String>
) : StoredDataStream<T, String> {

    override fun getStoredTime(): Long = file.lastModified()

    override fun store(value: T) {
        if (!file.exists()) {
            file.createNewFile()
        }
        super.store(value)
    }

    companion object {
        inline fun <reified T : Any> create(file: File): FileStore<T> {
            if (T::class.java == String::class.java) {
                return FileStore(file, StringSerializer() as Serializer<T, String>)
            }
            return FileStore(file, JSONSerializer.create())
        }
    }


    override val reader: InputStream
        get() = FileInputStream(file)
    override val writer: OutputStream
        get() = FileOutputStream(file)


}
