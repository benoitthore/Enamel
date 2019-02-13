package com.thorebenoit.enamel.kotlin.core

fun Regex.capture(target: String): List<String> {
    val list = mutableListOf<String>()
    with(toPattern().matcher(target)) {
        while (find()) {
            for (i in 0 until groupCount()) {
                list += group(i)
            }
        }
    }
    return list
}


private val _2decFormat = "%.2f"
val Float._2dec get() = _2decFormat.format(this)

