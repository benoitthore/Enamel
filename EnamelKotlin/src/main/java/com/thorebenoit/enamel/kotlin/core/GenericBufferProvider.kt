package com.thorebenoit.enamel.kotlin.core

class GenericBufferProvider<T>(bufferSize: Int = 100, val default: () -> T) {
    private var index = 0
    private val list: List<T> = List(bufferSize) { default() }
    fun get(): T = list[index++ % list.size]

    operator fun invoke() = get()
    operator fun invoke(n: Int): List<T> = (0 until n).map { get() }

    fun save(){
//        TODO
    }

    fun restore(){
//        TODO
    }
}