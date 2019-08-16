package com.benoitthore.enamel.core.time

import com.benoitthore.enamel.core.math.f


class EDeltaTimer(val targetFrameTime: Int = 16) {
    private val timer: ETimer = ETimer()

    var deltaTime: Float = 1f
        private set

    fun start() {
        timer.start()
    }

    inline fun frame(block: (Float) -> Unit) {
        block(deltaTime)
        onFrameFinishes()
    }


    fun onFrameFinishes() {
        val elapsed = timer.elapsed.f
        deltaTime = elapsed / targetFrameTime
        timer.start()
    }
}