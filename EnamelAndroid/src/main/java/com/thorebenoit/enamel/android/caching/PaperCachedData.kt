package com.thorebenoit.enamel.android.caching

import android.os.SystemClock
import com.thorebenoit.enamel.kotlin.caching.CachedData
import com.thorebenoit.enamel.kotlin.caching.GeySystemTime
import com.thorebenoit.enamel.kotlin.caching.StoredData
import io.paperdb.Book
import io.paperdb.Paper

object AndroidGeySystemTime : GeySystemTime {
    override fun invoke(): Long = SystemClock.currentThreadTimeMillis()
}

object PaperCachedData {
    fun <T : Any> create(key: String, cachingTime: Long, refresh: () -> T?) =
        CachedData<T>(cachingTime, PaperCache(key, AndroidGeySystemTime), AndroidGeySystemTime, refresh)
}

class PaperCache<T : Any>(
    val key: String,
    val androidGeySystemTime: AndroidGeySystemTime,
    val book: Book = Paper.book()
) : StoredData<T, Any> {
    private fun _get(): Pair<Long, T?>? = book.read<Pair<Long, T?>>(key)

    override fun getStoredTime(): Long = _get()?.first ?: 0

    override fun get(): Pair<Long, T?> {
        return _get()?.let { (time, value) ->
            time to value
        } ?: 0L to null
    }

    override fun store(value: T) {
        book.write(key, androidGeySystemTime() to value)
    }

}
