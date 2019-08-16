package com.benoitthore.enamel.core.threading

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


fun getExecutor(threads: Int) = Executors.newFixedThreadPool(threads)
fun ExecutorService.run(runnables: List<() -> Unit>) {
    val jobs = runnables.map {
        submit(it)
    }
    jobs.forEach {
        it.get()
    }
}

val _executorMap = mutableMapOf<Int, ExecutorService>()

fun initParallelExecutors() {
    val threads = Runtime.getRuntime().availableProcessors()
    _executorMap.getOrPut(threads) { getExecutor(threads) }
}

fun <T> List<T>.forEachParallel(threads: Int = Runtime.getRuntime().availableProcessors(), block: (T) -> Unit) {
    val executor = _executorMap.getOrPut(threads) { getExecutor(threads) }
    if (this is LinkedList) {
        System.err.println("Inefficient on LinkedList")
    }
    val list: List<() -> Unit> = (0 until size).map { i ->
        { block(get(i)) }
    }

    executor.run(list)
}


inline fun <T, R> List<T>.mapIndexedParallel(
    chunkSize: Int = 100,
    crossinline block: suspend (Int, T) -> R
): List<R> =
    runBlocking(Dispatchers.Default) {
        chunked(chunkSize).mapIndexed { chunkIndex, chunk ->
            async {
                chunk.mapIndexed { index, obj ->
                    val actualIndex = (chunkIndex * chunkSize + index)
                    block(actualIndex, obj)
                }
            }
        }.awaitAll().flatten()
    }


inline fun <T> List<T>.mapParallel(
    chunkSize: Int = size / Runtime.getRuntime().availableProcessors(),
    crossinline block: suspend (T) -> Unit
) =
    mapIndexedParallel(chunkSize) { _, o -> block(o) }



/*


inline fun <T> List<T>.forEachIndexedParallel(
    chunkSize: Int = size / Runtime.getRuntime().availableProcessors(),
    crossinline block: suspend (Int, T) -> Unit
) {
    runBlocking(Dispatchers.Default) {

        chunked(chunkSize).mapIndexed { chunkIndex, chunk ->
            async {
                chunk.mapIndexedNotNull { index, obj ->
                    val actualIndex = (chunkIndex * chunkSize + index)
                    block(actualIndex, obj)
                }
            }
        }.awaitAll().flatten()
    }
}

inline fun <T> List<T>.forEachParallel(
    chunkSize: Int = size / Runtime.getRuntime().availableProcessors(),
    crossinline block: suspend (T) -> Unit
) {
    forEachIndexedParallel(chunkSize) { _, o -> block(o) }
}


 */