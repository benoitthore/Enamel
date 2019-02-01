package com.thorebenoit.enamel.kotlin.core.map

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
