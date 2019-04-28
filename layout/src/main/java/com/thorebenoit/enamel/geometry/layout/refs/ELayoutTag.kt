package com.thorebenoit.enamel.geometry.layout.refs

import com.thorebenoit.enamel.geometry.figures.ERect
import com.thorebenoit.enamel.geometry.figures.ERectMutable
import com.thorebenoit.enamel.geometry.figures.ESize
import com.thorebenoit.enamel.geometry.layout.ELayout

class ELayoutTag(var tag: String) : ELayout {
    override val childLayouts: List<ELayout> = emptyList()

    val frame: ERect get() = _frame
    private val _frame = ERectMutable()

    override fun size(toFit: ESize): ESize = toFit

    override fun arrange(frame: ERect) {
        this._frame.set(frame)
    }

}