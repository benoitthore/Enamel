package com.thorebenoit.enamel.kotlin.core.backingfield

import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference
import java.util.concurrent.ConcurrentHashMap



class IdentityWeakReference<T> : WeakReference<T> {

    private val hash: Int


    constructor(p0: T) : super(p0) {
        hash = System.identityHashCode(p0)
    }

    constructor(p0: T, p1: ReferenceQueue<in T>?) : super(p0, p1) {
        hash = System.identityHashCode(p0)
    }


    override fun equals(other: Any?): Boolean {
        return (other as? WeakReference<T>)?.let { it.get() == get() } ?: false
    }

    override fun hashCode(): Int {
        return hash
    }


}