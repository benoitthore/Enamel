package com.thorebenoit.enamel.geometry.layout.refs

import com.thorebenoit.enamel.geometry.figures.ERectType
import com.thorebenoit.enamel.geometry.figures.ESizeType
import com.thorebenoit.enamel.geometry.layout.ELayout

class ELayoutTag(var tag: String) : ELayout {
    override val childLayouts: List<ELayout> = emptyList()

    override fun size(toFit: ESizeType): ESizeType = toFit

    override fun arrange(frame: ERectType) {

    }

}