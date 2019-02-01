package com.thorebenoit.enamel.kotlin.core.backingfield


import java.lang.ref.ReferenceQueue
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


class ExtraValueHolder<K : Any, V>(onGcCallback: (Int, Int) -> Unit = { before, after -> }, val default: K.() -> V) :
    ReadWriteProperty<K, V> {
    private val referenceQueue = ReferenceQueue<K>()

    val referenceMap = referenceMap<K, V>(referenceQueue, onGcCallback)

    override fun getValue(thisRef: K, property: KProperty<*>): V =
        referenceMap[thisRef.toIdentity()]
            ?: thisRef.default().apply { setValue(thisRef, property, this) }

    override fun setValue(thisRef: K, property: KProperty<*>, value: V) {
        referenceMap[thisRef.toIdentity()] = value
    }

    fun K.toIdentity() = IdentityWeakReference(this, referenceQueue)

}

