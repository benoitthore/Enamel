package com.thorebenoit.enamel.kotlin.physics

import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.core.time.EDeltaTimer
import com.thorebenoit.enamel.kotlin.threading.coroutine

// Returns a stopper lambda -> Invoke the lambda to stop the loop
fun physicsLoop(targetFrameTime: Int = 16, block: (Float) -> Unit): () -> Unit {
    var loop = true
    coroutine {
        val timer = EDeltaTimer(targetFrameTime)
        while (loop) {
            timer.frame { deltaTime ->
                val deltaSleepRatio = (1f - (deltaTime ))

                if (deltaSleepRatio > 0) {
                    Thread.sleep((timer.targetFrameTime * deltaSleepRatio).toLong())
                }

                block(deltaTime)
            }
        }
    }

    return {
        loop = false
    }
}