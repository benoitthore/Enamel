package com.thorebenoit.enamel.kotlin.threading

import com.thorebenoit.enamel.kotlin.core.print
import java.util.*
import java.util.concurrent.Executor
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