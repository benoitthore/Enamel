package com.thorebenoit.enamel.kotlin.caching.store

import com.thorebenoit.enamel.kotlin.caching.Store

interface StoredData<T : Any, S : Any> {

    fun getStoredTime(): Long

    fun get(): Pair<Long, T?>

    fun store(value: T)
}

