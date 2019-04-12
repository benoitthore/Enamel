package com.thorebenoit.enamel.kotlin.caching.serializer

interface Serializer<T : Any, S : Any> {
    fun serialize(value: T): S
    fun deserialize(serializedData: S): T
    fun toBytes(serializedData: S): ByteArray
    fun fromBytes(bytes: ByteArray): T
}

fun <T : Any, S : Any> Serializer<T, S>.toBytes(value: T) = toBytes(serialize(value))
