package com.thorebenoit.enamel.kotlin.core.data

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue


fun ignoreUnknownObjectMapper(): ObjectMapper =
    jacksonObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

fun Any.toJson(): String = ignoreUnknownObjectMapper().writeValueAsString(this)

fun <T : Any> String.fromJson(clazz: Class<T>): T = ignoreUnknownObjectMapper().readValue(this, clazz)

inline fun <reified T : Any> String.fromJson(): T = ignoreUnknownObjectMapper().readValue(this)
inline fun <reified T : Any> String.fromJsonSafe(): T? = try {
    ignoreUnknownObjectMapper().readValue(this)
} catch (e: Exception) {
    null
}
