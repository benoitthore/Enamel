package com.thorebenoit.enamel.kotlin.geometry.layout.refs


//// This Layout is the interface between ELayout and any UI framework
class ELayoutRefObject<V : Any>(
    val viewRef: V,
    private val addToParent: V.() -> Unit,
    private val removeFromParent: V.() -> Unit,
    val isSameView: (V) -> Boolean
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