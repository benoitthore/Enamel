package com.thorebenoit.enamel.kotlin.geometry.layout

import com.thorebenoit.enamel.geometry.alignement.ELayoutAxis
import com.thorebenoit.enamel.geometry.figures.ERect
import com.thorebenoit.enamel.geometry.figures.ERectType
import com.thorebenoit.enamel.geometry.figures.ESizeType
import com.thorebenoit.enamel.geometry.layout.ELayout
import com.thorebenoit.enamel.geometry.layout.ELayoutAlongAxis

class ELayoutLeaf(var color: Int = 0, var child: ELayout? = null) : ELayoutAlongAxis {

    override val layoutAxis: ELayoutAxis get() = (child as? ELayoutAlongAxis)?.layoutAxis ?: ELayoutAxis.horizontal

    companion object {
        val unit = ELayoutLeaf(0)
    }

    private val _frame: ERect = ERect()
    val frame: ERectType = _frame


    override val childLayouts: List<ELayout>
        get() = child?.let { listOf(it) } ?: emptyList() // TODO add setter on child to avoid allocation on get()

    override fun size(toFit: ESizeType): ESizeType = child?.size(toFit) ?: toFit


    override fun arrange(frame: ERectType) {
        _frame.set(frame)
        child?.arrange(frame)
    }

    override fun toString(): String {
        return "ELayoutLeaf(color=$color, child=$child, frame=$frame)"
    }

}
