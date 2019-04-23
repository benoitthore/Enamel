package com.thorebenoit.enamel.core

import java.lang.StringBuilder
import java.text.DecimalFormat

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

private val _bigNumberFormat = DecimalFormat("#,###.##")
val Number.bigNumberFormat: String get() = _bigNumberFormat.format(this)

fun Number.humanReadableByteCount(si: Boolean = true): String {
    val bytes = toLong()

    val unit = if (si) 1000 else 1024
    if (bytes < unit) return "$bytes B"
    val exp = (Math.log(bytes.toDouble()) / Math.log(unit.toDouble())).toInt()
    val pre = (if (si) "kMGTPE" else "KMGTPE")[exp - 1] + if (si) "" else "i"
    return String.format("%.1f %sB", bytes / Math.pow(unit.toDouble(), exp.toDouble()), pre)
}

