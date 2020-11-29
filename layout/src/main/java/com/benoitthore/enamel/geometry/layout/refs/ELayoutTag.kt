package com.benoitthore.enamel.geometry.layout.refs

import com.benoitthore.enamel.geometry.builders.*
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.functions.setBounds
import com.benoitthore.enamel.geometry.layout.ELayout

class ELayoutTag(var tag: String) : ELayout {
    override val children: List<ELayout> get() = emptyList()

    val frame: ERect get() = _frame
    private val _frame = Rect()

    override fun size(toFit: ESize): ESize = toFit

    override fun arrange(frame: ERect) {
        this._frame.setBounds(frame)
    }

}