package com.thorebenoit.enamel.kotlin.threading

import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.core.time.ETimer
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import java.lang.Thread.sleep
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

/*
TODO Fix this
private val _executorMap = mutableMapOf<Int, Scheduler>()
private fun getScheduler(threads: Int) =
    _executorMap.getOrPut(threads) { Schedulers.from(Executors.newFixedThreadPool(threads)) }


fun <T> Iterable<T>.forEachParallel(threads: Int = Runtime.getRuntime().availableProcessors(), block: (T) -> Unit) {

    Flowable.fromIterable(this)
        .parallel(threads)
        .runOn(getScheduler(threads))
        .map(block)
        .sequential()
        .subscribe()
}

fun <T, R> Iterable<T>.mapParallel(
    threads: Int = Runtime.getRuntime().availableProcessors(),
    block: (T) -> R
): List<R> {

    return Flowable.fromIterable(this)
        .parallel(threads)
        .runOn(getScheduler(threads))
        .map(block)
        .sequential()
        .blockingIterable().toList()
}

fun main(){
    val list = (0 .. 100).toList()
    list.size.print
    list.mapParallel { it.print }.size
}
 */