package com.thorebenoit.enamel.kotlin.caching.store

interface StoredData<T : Any, S : Any> {

    fun getStoredTime(): Long

    fun get(): Pair<Long, T?>

    fun store(value: T)
}

