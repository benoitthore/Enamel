package com.thorebenoit.enamel.kotlin.caching.serializer

import java.nio.charset.Charset

class StringSerializer : Serializer<String, String> {
    override fun serialize(value: String): String = value.toString()

    override fun deserialize(serializedData: String): String = serializedData

    override fun toBytes(serializedData: String): ByteArray = serializedData.toByteArray(Charset.defaultCharset())

    override fun fromBytes(bytes: ByteArray): String = bytes.toString(Charset.defaultCharset())

}