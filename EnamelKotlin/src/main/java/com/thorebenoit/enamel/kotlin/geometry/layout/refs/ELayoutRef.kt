package com.thorebenoit.enamel.kotlin.geometry.layout.refs

import com.thorebenoit.enamel.kotlin.geometry.figures.ERect
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ELayoutDataStore
import java.util.*

open class ELayoutRef<V : Any>(
    var ref: ELayoutRefObject<V>,
    private val sizeToFIt: (ESizeType) -> ESizeType,
    private val arrangeIn: (ERectType) -> Unit,
    private val serialize: ELayoutRef<V>.(ELayoutDataStore) -> Unit = { TODO() },
    private val deserialize: ELayoutRef<V>.(ELayoutDataStore) -> Unit = { TODO() }
) : ELayout {
    override fun serialize(dataStore: ELayoutDataStore) {
        serialize(dataStore)
    }

    override fun deserialize(dataStore: ELayoutDataStore) {
        deserialize(dataStore)
    }

    override val childLayouts: List<ELayout> = listOf()

    var isInMeasureMode = false

    private var _frame: ERect = ERect()
    val frame: ERectType get() = _frame

    override fun size(toFit: ESizeType): ESizeType {
        return sizeToFIt(toFit)
    }

    override fun arrange(frame: ERectType) {
        _frame.set(frame)
        if (!isInMeasureMode) {
            arrangeIn(frame)
            ref.addToParent()
        }
    }
}


//
// TODO Test
fun ELayout.getAllChildren(list: LinkedList<ELayout> = LinkedList()): List<ELayout> {
    list += this
    childLayouts.flatMap { it.getAllChildren(list) }
    return list
}

fun <T : Any> ELayout.getObjects(): List<T> = getRefs<T>().map { it.ref.viewRef }

fun <T : Any> ELayout.getRefs(): List<ELayoutRef<T>> {
    val list = mutableListOf<ELayoutRef<T>>()

    (this as? ELayoutRef<T>)?.let {
        list.add(it)
    }
    list.addAll(childLayouts.flatMap { it.getRefs<T>() })

    return list
}
