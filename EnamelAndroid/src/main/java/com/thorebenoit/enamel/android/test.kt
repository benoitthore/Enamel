package com.thorebenoit.enamel.android

import com.thorebenoit.enamel.kotlin.core.print
import java.io.*
import java.util.*


fun main() {

    val lambda = { println("123") }
    val s_lambda = lambda.serialize()

    s_lambda.map { it.toChar() }.joinToString(separator = "").print
//    val ds_lambda = s_lambda.deserialize<() -> Unit>()
//    ds_lambda()

}

fun Any.serialize(): ByteArray {
    val baos = ByteArrayOutputStream()
    baos.use {

        val objOs = ObjectOutputStream(BufferedOutputStream(baos))
        objOs.use {

            objOs.writeObject(this)
        }
    }
    return baos.toByteArray()
}

fun <T> ByteArray.deserialize(): T {
    val objIs = ObjectInputStream(
        BufferedInputStream(
            ByteArrayInputStream(this)
        )
    )
    objIs.use {
        return objIs.readObject() as T
    }
}