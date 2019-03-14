package com.thorebenoit.enamel.kotlin.geometry.layout.refs

import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout

open class ELayoutRef<V : Any>(
    val ref: ELayoutRefObject<V>,
    private val sizeToFIt: (ESizeType) -> ESizeType,
    private val arrangeIn: (ERectType) -> Unit,
    override val childLayouts: List<ELayout>
) : ELayout {
    override fun size(toFit: ESizeType): ESizeType {
        return sizeToFIt(toFit)
    }

    override fun arrange(frame: ERectType) {
        arrangeIn(frame)
        ref.addToParent()
    }
}


//
//// TODO Use Extra value holder and make lazy to speed up perfs (possible only if layout is fully immutable)
//fun <T : Any> ELayout.getObjects(): List<T> = getRefs<T>().map { it.ref.viewRef }
//
//fun <T : Any> ELayout.getRefs(): List<ELayoutRef<T>> {
//    val list = mutableListOf<ELayoutRef<T>>()
//
//    (this as? ELayoutRef<T>)?.let {
//        list.add(it)
//    }
//    list.addAll(childLayouts.flatMap { getRefs<T>() })
//
//    return list
//}
