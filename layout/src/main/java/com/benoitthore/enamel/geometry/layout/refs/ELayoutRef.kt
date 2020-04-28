package com.benoitthore.enamel.geometry.layout.refs

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.figures.ESize
import com.benoitthore.enamel.geometry.layout.ELayout

import java.util.*


class ELayoutRef<V : Any>(
    var ref: ELayoutRefObject<V>,
    private val sizeToFit: ELayoutRef<V>.(ESize) -> ESize,
    private val arrangeIn: ELayoutRef<V>.(ERect) -> Unit
) : ELayout {

    override val children: List<ELayout> = listOf()

    var isInMeasureMode = false

    private var _frame: ERectMutable = E.mRect()
    val frame: ERect get() = _frame

    override fun size(toFit: ESize): ESize {
        return sizeToFit(toFit)
    }

    override fun arrange(frame: ERect) {
        _frame.set(frame)
        if (!isInMeasureMode) {
            ref.addToParent()
            arrangeIn(frame)
        }
    }
}


inline fun <reified T : ELayout> ELayout.getAllChildrenWithType(list: MutableList<ELayout> = LinkedList()) =
    getAllChildren(list).asSequence().filterIsInstance<T>()

fun ELayout.getAllChildren(list: MutableList<ELayout> = LinkedList()): Iterable<ELayout> {
    list += this
    children.flatMap { it.getAllChildren(list) }
    return list
}

fun <T : Any> ELayout.getRefs(): List<ELayoutRef<T>> {
    val list = mutableListOf<ELayoutRef<T>>()

    (this as? ELayoutRef<T>)?.let {
        list.add(it)
    }
    list.addAll(children.flatMap { it.getRefs<T>() })

    return list
}
