package com.benoitthore.enamel.geometry.layout.refs

import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.geometry.figures.ESize
import com.benoitthore.enamel.geometry.layout.ELayout

class ELayoutTag(var tag: String) : ELayout {
    override val children: List<ELayout> = emptyList()

    val frame: ERect get() = _frame
    private val _frame = ERectMutable()

    override fun size(toFit: ESize): ESize = toFit

    override fun arrange(frame: ERect) {
        this._frame.set(frame)
    }

}