package com.benoitthore.enamel.geometry.figures.oval

import com.benoitthore.enamel.geometry.Resetable
import com.benoitthore.enamel.geometry.interfaces.bounds.CanSetBounds
import com.benoitthore.enamel.geometry.interfaces.bounds.EShapeMutable

interface EOvalMutable : EOval, Resetable,
    CanSetBounds<EOval, EOvalMutable>, EShapeMutable<EOval,EOvalMutable> {

    override var ry: Float
        get() = TODO()
        set(value) {
            TODO()
        }
    override var rx: Float
        get() = TODO()
        set(value) {
            TODO()
        }

    override fun reset() {
        rx = 0f
        ry = 0f
    }
}