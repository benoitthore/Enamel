package com.benoitthore.enamel.geometry.alignement

enum class ERectEdge {
    left, top, right, bottom;


    val opposite: ERectEdge
        get() = when (this) {
            left -> right
            top -> bottom
            right -> left
            bottom -> top
        }


    val alignement
        get() = when (this) {
            left -> EAlignment.leftCenter
            top -> EAlignment.topCenter
            right -> EAlignment.rightCenter
            bottom -> EAlignment.bottomCenter
        }

    val isVertical
        get() = when (this) {
            left, right -> false
            else -> true
        }
    val isHorizontal get() = !isVertical

}