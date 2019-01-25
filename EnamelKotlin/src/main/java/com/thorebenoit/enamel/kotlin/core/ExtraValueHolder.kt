package com.thorebenoit.enamel.kotlin.core


import com.thorebenoit.enamel.kotlin.threading.coroutine
import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference
import java.util.concurrent.ConcurrentHashMap
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <K, V> Map<WeakReference<K>, V>.getRef(key: K): Pair<K, V>? {
    this._forEach { ref, value ->
        ref.get()?.let {
            if (key == it) {
                return it to value
            }
        }
    }

    return null
}

fun <K, V, M : MutableMap<WeakReference<K>, V>> M.clearOnGC(referenceQueue: ReferenceQueue<K>): M {

    coroutine {
        while (true) {

            // This line blocks until one of the keys from the map gets garbage collected
            referenceQueue.remove()

            // Empty the reference queue so the next bit of code runs only once per GC
            while (referenceQueue.poll() != null);

            // Remove all the references that are null
            removeWhen { weakReference, _ ->
                weakReference.get() == null
            }
        }
    }

    return this
}

fun <K, V> referenceMap(referenceQueue: ReferenceQueue<K>): MutableMap<WeakReference<K>, V> = ConcurrentHashMap<WeakReference<K>, V>(mutableMapOf()).clearOnGC(referenceQueue)

class ExtraValueHolder<K, V>(val default: K.() -> V) : ReadWriteProperty<K, V> {
    private val referenceQueue = ReferenceQueue<K>()
    private val referenceMap = referenceMap<K, V>(referenceQueue)

    override fun getValue(thisRef: K, property: KProperty<*>): V =
        referenceMap.getRef(thisRef)?.second
            ?: thisRef.default().apply { setValue(thisRef, property, this) }

    override fun setValue(thisRef: K, property: KProperty<*>, value: V) {
        referenceMap.removeWhen { reference, _ ->
            reference.get() == null || reference.get() == thisRef
        }
        val ref = WeakReference(thisRef, referenceQueue)
        referenceMap[ref] = value
    }

}
