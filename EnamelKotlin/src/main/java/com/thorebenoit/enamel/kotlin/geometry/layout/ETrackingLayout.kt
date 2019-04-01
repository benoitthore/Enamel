package com.thorebenoit.enamel.kotlin.geometry.layout

import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ELayoutDeserializer
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ELayoutSerializer

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

    override fun serialize(dataStore: ELayoutSerializer) {
        dataStore.add(src)
        dataStore.add(dst)
    }

    override fun deserialize(dataStore: ELayoutDeserializer) {
        src = dataStore.readLayout()
        dst = dataStore.readLayout()
    }
}
