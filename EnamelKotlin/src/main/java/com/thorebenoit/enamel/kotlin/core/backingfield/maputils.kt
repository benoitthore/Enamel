package com.thorebenoit.enamel.kotlin.core.backingfield

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.ref.ReferenceQueue


fun <K, V> MutableMap<K, V>.removeWhen(block: (K, V) -> Boolean) {
    val remove = mutableListOf<K>()
    _forEach { k, v ->
        if (block(k, v)) {
            remove += k
        }
    }
    remove.forEach { this.remove(it) }
}

inline fun <K, V> Map<out K, V>._forEach(action: (K, V) -> Unit) {
    for (element in this) action(element.key, element.value)
}


fun <K, V> ConcurrentWeakIdentityHashMap<K, V>.clearOnGC(referenceQueue: ReferenceQueue<K>): ConcurrentWeakIdentityHashMap<K, V> {

    GlobalScope.launch {
        while (true) {

            // This line blocks until one of the keys from the map gets garbage collected
            referenceQueue.remove()

            // Empty the reference queue so the next bit of code runs only once per GC
            while (referenceQueue.poll() != null);

            // Remove all the references that are null
            removeWhen { k, v ->
                v == null
            }
        }
    }

    return this
}

fun <K, V> referenceMap(referenceQueue: ReferenceQueue<K>) =
    ConcurrentWeakIdentityHashMap<K, V>(referenceQueue).clearOnGC(referenceQueue)
