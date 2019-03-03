package com.thorebenoit.enamel.kotlin.time

val Number.seconds: Long get() = 1000L * toLong()
val Number.minutes: Long get() = 60 * this.seconds
val Number.hours: Long get() = 60 * this.minutes
val Number.days: Long get() = 24 * this.hours