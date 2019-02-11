package com.thorebenoit.enamel.kotlin.core.time

import com.thorebenoit.enamel.kotlin.core.f
import com.thorebenoit.enamel.kotlin.core.i

class EDeltaTimer(val targetFrameTime: Int = 16) {
    private val timer : ETimer = ETimer()
    var lastFrameTime = -1

    var deltaTime: Float = 1f
        private set

    fun start(){
        timer.start()
    }

    inline fun frame(block : (Float)->Unit){
        block(deltaTime)
        onFrameFinishes()
    }




    fun onFrameFinishes() {
        val elapsed = timer.elapsed.f
        deltaTime = elapsed / targetFrameTime
        timer.start()
    }
}