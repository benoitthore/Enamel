package com.benoitthore.enamel.geometry.layout

import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.primitives.size.ESize

class ETrackingLayout(src: ELayout = ELayoutLeaf.unit, dst: ELayout = ELayoutLeaf.unit) : ELayout {

    private val _childLayouts: MutableList<ELayout> = mutableListOf(src, dst)
    var src = src
        internal set(value) {
            field = value
            _childLayouts.clear()
            _childLayouts.add(src)
            _childLayouts.add(dst)
        }

    var dst = dst
        internal set(value) {
            field = value
            _childLayouts.clear()
            _childLayouts.add(src)
            _childLayouts.add(dst)
        }

    override val children: List<ELayout> get() = _childLayouts

    override fun size(toFit: ESize): ESize = src.size(toFit)

    override fun arrange(frame: ERect) {
        src.arrange(frame)
        dst.arrange(frame)
    }

}
