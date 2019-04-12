package com.thorebenoit.enamel.kotlin.core.backingfield

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.ref.ReferenceQueue
import java.util.concurrent.ConcurrentHashMap


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


fun <K : Any, V> ConcurrentHashMap<IdentityWeakReference<K>, V>.clearOnGC(
    referenceQueue: ReferenceQueue<K>,
    onGcCallback: (Int, Int) -> Unit = { before, after -> }
): ConcurrentHashMap<IdentityWeakReference<K>, V> {

    GlobalScope.launch {
        while (true) {

            // This line blocks until one of the keys from the map gets garbage collected
            referenceQueue.remove()

            // Empty the reference queue so the next bit of code runs only once per GC
            while (referenceQueue.poll() != null);

            val before = size
            // Remove all the references that are null
            removeWhen { k, v ->
                k.get() == null
            }
            val after = size

            onGcCallback(before, after)
        }
    }

    return this
}

fun <K : Any, V> referenceMap(
    referenceQueue: ReferenceQueue<K>,
    onGcCallback: (Int, Int) -> Unit = { before, after -> }
) =
    ConcurrentHashMap<IdentityWeakReference<K>, V>(mutableMapOf()).clearOnGC(referenceQueue, onGcCallback)
