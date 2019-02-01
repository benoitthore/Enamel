package com.thorebenoit.enamel.kotlin.core.backingfield

import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference
import java.util.concurrent.ConcurrentHashMap

// TODO Refactor to use a copy of https://github.com/openjdk-mirror/jdk7u-jdk/blob/master/src/share/classes/java/util/concurrent/ConcurrentHashMap.java
class ConcurrentWeakIdentityHashMap<K : Any, V>(
    val referenceQueue: ReferenceQueue<in K>
) : MutableMap<K, V?> {

    val map: ConcurrentHashMap<IdentityWeakReference<K>, V?> = ConcurrentHashMap(mutableMapOf())

    override fun containsValue(value: V?): Boolean = map.containsValue(value)

    // TODO This is terrible, only use for testing!
    override val entries: MutableSet<MutableMap.MutableEntry<K, V?>>
        get() = keys
            .map { it to map[it.toIdentity()] }
            .map<Pair<K,V?>,MutableMap.MutableEntry<K, V?>> { (k, v) ->
                object : MutableMap.MutableEntry<K, V?> {
                    override val key: K
                        get() = k
                    override val value: V?
                        get() = v

                    override fun setValue(newValue: V?): V? {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                }
            }.toMutableSet()

    override val keys: MutableSet<K>
        get() = map.keys().asSequence().map { it.get() }.filterNotNull().toMutableSet()
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