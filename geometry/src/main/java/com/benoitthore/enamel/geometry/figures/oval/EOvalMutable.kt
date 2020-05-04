package com.benoitthore.enamel.geometry.figures.oval

import com.benoitthore.enamel.geometry.figures.rect.ERectMutable

interface EOvalMutable : EOval, ERectMutable {

    override var ry: Float
        get() = size.height / 2f
        set(value) {
            size.height = value / 2f
        }
    override var rx: Float
        get() = size.width / 2f
        set(value) {
            size.width = value / 2f
        }

    override fun reset() {
        rx = 0f
        ry = 0f
    }
}