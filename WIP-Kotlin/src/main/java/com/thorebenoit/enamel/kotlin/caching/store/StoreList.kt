package com.thorebenoit.enamel.kotlin.caching.store

import com.thorebenoit.enamel.kotlin.caching.Store

class StoreList<T : Any>(
    val createStore: (String) -> Store<T>
) {
    private val storeMap = mutableMapOf<String, Store<T>>()

    fun get(key: String) = key.store.get()

    private val String.store get() = storeMap.getOrPut(this) { createStore(this) }
}

