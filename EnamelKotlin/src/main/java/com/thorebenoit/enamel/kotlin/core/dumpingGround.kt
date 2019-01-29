package com.thorebenoit.enamel.kotlin.core

import java.lang.Exception

val Any?.print get() = println(this)

fun tryCatch(tryBlock: () -> Unit, catchBlock: () -> Unit = {}) {
    try {
        tryBlock()
    } catch (e: Exception) {
        catchBlock()
    }
}