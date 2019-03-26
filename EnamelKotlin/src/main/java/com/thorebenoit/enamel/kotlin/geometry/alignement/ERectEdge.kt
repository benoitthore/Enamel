package com.thorebenoit.enamel.kotlin.geometry.alignement

enum class ERectEdge {
    left, top, right, bottom
}

val ERectEdge.alignement
    get() = when (this) {
        ERectEdge.left -> EAlignment.leftCenter
        ERectEdge.top -> EAlignment.topCenter
        ERectEdge.right -> EAlignment.rightCenter
        ERectEdge.bottom -> EAlignment.bottomCenter
    }

val ERectEdge.isVertical
    get() = when (this) {
        ERectEdge.left, ERectEdge.right -> false
        else -> true
    }
val ERectEdge.isHorizontal get() = !isVertical