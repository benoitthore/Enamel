package com.benoitthore.enamel.geometry.layout

import com.benoitthore.enamel.geometry.alignement.ELayoutAxis
import com.benoitthore.enamel.geometry.e.E
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.figures.ESize

class ELayoutLeaf(var color: Int = 0, var child: ELayout? = null) : ELayoutAlongAxis {

    override val layoutAxis: ELayoutAxis get() = (child as? ELayoutAlongAxis)?.layoutAxis ?: ELayoutAxis.horizontal

    companion object {
        val unit = ELayoutLeaf(0)
    }

    private val _frame: ERectMutable = E.mrect()
    val frame: ERect = _frame


    override val children: List<ELayout>
        get() = child?.let { listOf(it) } ?: emptyList() // TODO add setter on child to avoid allocation on get()

    override fun size(toFit: ESize): ESize = child?.size(toFit) ?: toFit


    override fun arrange(frame: ERect) {
        _frame.set(frame)
        child?.arrange(frame)
    }

    override fun toString(): String {
        return "ELayoutLeaf(color=$color, child=$child, frame=$frame)"
    }

}
