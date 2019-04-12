package com.thorebenoit.enamel.geometry.layout

import com.thorebenoit.enamel.geometry.figures.ERectType
import com.thorebenoit.enamel.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayoutLeaf

class ETrackingLayout(src: ELayout = ELayoutLeaf.unit, dst: ELayout = ELayoutLeaf.unit) : ELayout {

    private val _childLayouts: MutableList<ELayout> = mutableListOf(src, dst)
    var src = src
        set(value) {
            field = value
            _childLayouts.clear()
            _childLayouts.add(src)
            _childLayouts.add(dst)
        }

    var dst = src
        set(value) {
            field = value
            _childLayouts.clear()
            _childLayouts.add(src)
            _childLayouts.add(dst)
        }

    override val childLayouts: List<ELayout> get() = _childLayouts

    override fun size(toFit: ESizeType): ESizeType = src.size(toFit)

    override fun arrange(frame: ERectType) {
        src.arrange(frame)
        dst.arrange(frame)
    }

}