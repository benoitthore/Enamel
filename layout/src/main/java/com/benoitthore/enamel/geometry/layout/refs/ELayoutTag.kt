package com.benoitthore.enamel.geometry.layout.refs

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.figures.ESize
import com.benoitthore.enamel.geometry.layout.ELayout

class ELayoutTag(var tag: String) : ELayout {
    override val children: List<ELayout> get() = emptyList()

    val frame: ERect get() = _frame
    private val _frame = E.mrect()

    override fun size(toFit: ESize): ESize = toFit

    override fun arrange(frame: ERect) {
        this._frame.set(frame)
    }

}