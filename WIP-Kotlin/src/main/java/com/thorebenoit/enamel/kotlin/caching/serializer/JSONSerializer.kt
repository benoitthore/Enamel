package com.thorebenoit.enamel.kotlin.caching.serializer

import com.thorebenoit.enamel.core.fromJson
import com.thorebenoit.enamel.core.toJson
import java.nio.charset.Charset

class JSONSerializer<T : Any> constructor(private val clazz: Class<T>) : Serializer<T, String> {
    override fun fromBytes(bytes: ByteArray): T = bytes.toString(Charset.defaultCharset()).fromJson(clazz)

    companion object {
        inline fun <reified T : Any> create() = JSONSerializer(T::class.java)
    }

    override fun toBytes(serializedData: String): ByteArray = serializedData.toByteArray(Charset.defaultCharset())


    override fun serialize(value: T): String = value.toJson()

    override fun deserialize(serializedData: String): T = serializedData.fromJson(clazz)

}
