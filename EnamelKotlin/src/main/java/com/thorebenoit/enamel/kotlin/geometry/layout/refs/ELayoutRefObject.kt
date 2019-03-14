package com.thorebenoit.enamel.kotlin.geometry.layout.refs

//// This Layout is the interface between ELayout and any UI framework
class ELayoutRefObject<V : Any>(
    val viewRef: V,
    internal val addToParent: V.() -> Unit,
    internal val removeFromParent: V.() -> Unit
) {
    fun addToParent() {
        apply {
            viewRef.addToParent()
        }
    }

    fun removeFromParent() {
        apply {
            viewRef.removeFromParent()
        }
    }
}