package com.thorebenoit.enamel.core

import java.lang.StringBuilder

operator fun StringBuilder.plusAssign(str: String) {
    append(str)
}

operator fun StringBuilder.plusAssign(char: Char) {
    append(char)
}


// <Random Sting generation>
fun CharRange.randomString(size: Int): String = listOf(this).randomString(size)

fun randomString(size: Int, vararg range: Pair<Char, Char>) =
    range.map { (from, to) -> from..to }.randomString(size)

fun randomString(size: Int, vararg range: CharRange) = range.toList().randomString(size)

fun List<CharRange>.randomString(size: Int): String =
    (0 until size).map { random().random() }.joinToString(separator = "")
// </Random Sting generation>

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
val Double._2dec get() = _2decFormat.format(this)

