package com.thorebenoit.enamel.kotlin.core

fun <E> MutableList<E>.limitSize(n: Int) {
    while (size >= n) {
        removeAt(size - 1)
    }
}

fun <E> MutableList<E>.limitSizeLast(n: Int) {
    while (size >= n) {
        removeAt(0)
    }
}