package com.thorebenoit.enamel.kotlin.core.backingfield

import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference
import java.util.concurrent.ConcurrentHashMap


class ConcurrentWeakIdentityHashMap<K, V>(
    val referenceQueue: ReferenceQueue<in K>
) : MutableMap<K, V?> {

    val map: ConcurrentHashMap<IdentityWeakReference<K>, V?> = ConcurrentHashMap(mutableMapOf())

    override fun containsValue(value: V?): Boolean = map.containsValue(value)

    override val entries: MutableSet<MutableMap.MutableEntry<K, V?>>
        get() = TODO("not implemented")
    override val keys: MutableSet<K>
        get() = TODO("not implemented")
    override val values: MutableCollection<V?>
        get() = map.values

    override fun put(key: K, value: V?): V? = map.put(key.toIdentity(), value)

    override fun putAll(from: Map<out K, V?>) {
        map.putAll(from.mapKeys { (k, v) -> k.toIdentity() })
    }

    override val size: Int
        get() = map.size

    override fun containsKey(key: K): Boolean = map.contains(key.toIdentity())


    override fun get(key: K): V? = map[key.toIdentity()]

    override fun isEmpty(): Boolean = map.isEmpty()

    override fun clear() = map.clear()


    override fun remove(key: K): V? = map.remove(key.toIdentity())

    private fun K.toIdentity() = IdentityWeakReference(this, referenceQueue)
}


class IdentityWeakReference<T> : WeakReference<T> {

    private val hash: Int


    constructor(p0: T) : super(p0) {
        hash = System.identityHashCode(p0)
    }

    constructor(p0: T, p1: ReferenceQueue<in T>?) : super(p0, p1) {
        hash = System.identityHashCode(p0)
    }


    override fun equals(other: Any?): Boolean {
        return (other as? WeakReference<T>)?.let { it.get() == get() } ?: false
    }

    override fun hashCode(): Int {
        return hash
    }


}