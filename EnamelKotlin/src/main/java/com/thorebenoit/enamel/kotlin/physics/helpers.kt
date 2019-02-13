package com.thorebenoit.enamel.kotlin.physics

import com.thorebenoit.enamel.kotlin.core.time.EDeltaTimer
import com.thorebenoit.enamel.kotlin.threading.coroutine

// Returns a stopper lambda -> Invoke the lambda to stop the loop
inline fun physicsLoop(targetFrameTime: Int = 16, crossinline block: (Float) -> Unit): () -> Unit {
    var loop = true
    coroutine {
        while (loop) {
            val timer = EDeltaTimer(targetFrameTime)
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