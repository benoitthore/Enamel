package com.thorebenoit.enamel.kotlin.core.time

import com.thorebenoit.enamel.kotlin.core.f


class ETimerProgress : ETimer() {

    var duration = 1_000L
        set(value) {
            field = value
            start()
        }

    var progress
        get() = elapsed.f / duration.f
        set(value) {
            val targetElapsed = (duration * value).toLong()
            startAt = System.currentTimeMillis() - targetElapsed
        }
}
