package com.thorebenoit.enamel.kotlin.core.backingfield


import java.lang.ref.ReferenceQueue
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


class ExtraValueHolder<K, V>(val default: K.() -> V) : ReadWriteProperty<K, V> {
    private val referenceQueue = ReferenceQueue<K>()

    val referenceMap = referenceMap<K, V>(referenceQueue)

    override fun getValue(thisRef: K, property: KProperty<*>): V =
        referenceMap[thisRef]
            ?: thisRef.default().apply { setValue(thisRef, property, this) }

    override fun setValue(thisRef: K, property: KProperty<*>, value: V) {
        referenceMap[thisRef] = value
    }

}

