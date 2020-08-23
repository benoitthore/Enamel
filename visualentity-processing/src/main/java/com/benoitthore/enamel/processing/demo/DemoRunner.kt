package com.benoitthore.enamel.processing.demo

import com.benoitthore.enamel.core.animations.Interpolator
import com.benoitthore.enamel.core.animations.linearInterpolator
import com.benoitthore.enamel.core.animations.sinInterpolator
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.processing.visualentity.ProcessingVisualEntity
import com.benoitthore.enamel.processing.visualentity.toProcessingGeneric

class DemoRunner(val demo: EDemo,val interpolator: Interpolator = sinInterpolator) {


    var progress = 0f
        private set

    fun step(frame: ERect): List<ProcessingVisualEntity<*>> {
        if (progress > 1f) {
            progress = 0f
        }

        progress += 0.01f
        return demo.get(frame, interpolator(progress)).map { it.toProcessingGeneric() }

    }
}