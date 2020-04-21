package com.benoitthore.enamel.geometry

class GenericBufferProvider<T : Resetable>(targetSize: Int = 20, val default: () -> T) {
    private var index = 0
    private val list: List<T> = List(targetSize) { default() }
    fun get(): T = list[index++ % list.size]

    operator fun invoke() = get().apply { reset() }
    operator fun invoke(n: Int): List<T> = (0 until n).map { get() }
}

interface Resetable {
    fun reset()
}