package com.benoitthore.enamel.android.bubble

import com.benoitthore.enamel.geometry.figures.ECircleMutable
import com.benoitthore.enamel.geometry.lerp
import com.benoitthore.enamel.geometry.primitives.EPoint


private typealias touchListener = (EPoint) -> Unit

class BubbleController(val view: BubbleController.View) {
    interface View {
        var bubble: ECircleMutable
        fun update()
        var handleTouch: (EPoint) -> Boolean
        var touchListener: (isDown: Boolean, current: EPoint?, previous: EPoint?) -> Unit
    }

    fun start() {
        view.touchListener = { isDown: Boolean, current: EPoint?, previous: EPoint? ->
            current?.let { current ->
                // TODO Add physics instead of interpolating
                view.bubble.center.lerp(0.125, view.bubble.center, current)
                view.update()
            }
        }

        view.handleTouch = { view.bubble.contains(it) }
    }

}


