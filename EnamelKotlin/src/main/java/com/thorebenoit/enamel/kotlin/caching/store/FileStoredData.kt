package com.thorebenoit.enamel.kotlin.caching.store

import com.thorebenoit.enamel.kotlin.caching.serializer.JSONSerializer
import com.thorebenoit.enamel.kotlin.caching.serializer.Serializer
import com.thorebenoit.enamel.kotlin.caching.serializer.StringSerializer
import java.io.*

class FileStoredData<T : Any>(
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
        inline fun <reified T : Any> create(file: File): FileStoredData<T> {
            if (T::class.java == String::class.java) {
                return FileStoredData(file, StringSerializer() as Serializer<T, String>)
            }
            return FileStoredData(file, JSONSerializer.create())
        }
    }


    override val reader: InputStream
        get() = FileInputStream(file)
    override val writer: OutputStream
        get() = FileOutputStream(file)


}
