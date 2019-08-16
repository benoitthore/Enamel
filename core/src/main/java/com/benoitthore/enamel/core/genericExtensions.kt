package com.benoitthore.enamel.core

inline fun <T> Int.of(block: (Int) -> T) = List(this, block)


val <T> T.print get() = apply { println(this) }

fun <T> T.print(print: Boolean = true) = apply {
    if (print) {
        println(this)
    }
}
