package com.thorebenoit.enamel.kotlin.caching.store

import com.thorebenoit.enamel.kotlin.caching.Store

interface StoredData<T : Any, S : Any> {

    fun getStoredTime(): Long

    fun get(): Pair<Long, T?>

    fun store(value: T)
}

class StoredDataList<T : Any>(
    val createStore: (String) -> Store<T>
) {
    private val storeMap = mutableMapOf<String, Store<T>>()

    fun get(key: String) = key.store.get()

    private val String.store get() = storeMap.getOrPut(this) { createStore(this) }
}

