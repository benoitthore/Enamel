package com.thorebenoit.enamel.kotlin

val Number.i get() = toInt()
val Number.f get() = toFloat()
val Number.d get() = toDouble()
val Number.L get() = toLong()
val Number.b get() = toByte()


val Any?.print get() = println(this)