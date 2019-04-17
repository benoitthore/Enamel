package com.thorebenoit.enamel.kotlin.caching

import com.thorebenoit.enamel.kotlin.caching.store.FileStoredData
import com.thorebenoit.enamel.kotlin.caching.store.StoredData
import com.thorebenoit.enamel.core.print
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.io.File

typealias GeySystemTime = () -> Long


object FileStore {
    inline fun <reified T : Any> create(
        file: File, cachingTime: Long,
        useInFlight: Boolean = true,
        noinline getSystemTime: GeySystemTime = { System.currentTimeMillis() },
        noinline refresh: suspend () -> T?
    ): Store<T> = Store(
        cachingTime = cachingTime,
        store = FileStoredData.create<T>(file) as StoredData<T, Any>,
        useInFlight = useInFlight,
        getSystemTime = getSystemTime,
        refresh = refresh
    )
}

open class Store<T : Any>(
    val cachingTime: Long,
    val store: StoredData<T, Any>,
    val useInFlight: Boolean = true,
    val getSystemTime: GeySystemTime = { System.currentTimeMillis() },
    val refresh: suspend () -> T?
) {

    val lastCached get() = store.get().first

    private fun store(value: T) {
        store.store(value)
    }

    private var inFlight: Deferred<T?>? = null

    private fun createDeferred() {
        if (useInFlight && inFlight != null) {
            return
        }

        inFlight = GlobalScope.async {

            val result = let {
                val localData = store.get().second
                if (localData != null && isCacheValid()) {
                    return@let localData
                }


                val remoteData = refresh()

                if (remoteData != null) {
                    store(remoteData)
                }

                return@let remoteData
            }

            inFlight = null

            result
        }


    }

    fun get(): Deferred<T?> {
        createDeferred()
        return inFlight!!
    }

    private fun isCacheValid(): Boolean = getSystemTime() < lastCached + cachingTime
}


////////////////
////////////////
////////////////




