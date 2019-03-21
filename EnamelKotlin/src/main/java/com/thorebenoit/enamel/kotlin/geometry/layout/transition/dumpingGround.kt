package com.thorebenoit.enamel.kotlin.geometry.layout.transition

import com.thorebenoit.enamel.kotlin.animations.lerp
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.figures.ERect
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.ELayoutRef
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.ELayoutRefObject


/*
 UI Framework implementation should provide animator implementation
  */

class EChangeBoundAnimator<T : Any>(
    from: ELayoutRef<T>,
    to: ELayoutRef<T>
) : UpdateAnimator<T>(from, to) {

    companion object {
        fun <T : Any> getBuilder() = object : UpdateAnimator.Builder<T> {
            override fun build(from: ELayoutRef<T>, to: ELayoutRef<T>): UpdateAnimator<T> =
                EChangeBoundAnimator(from = from, to = to)

        }
    }

    val from: ERectType = fromRef.frame.copy()
    val to: ERectType = toRef.frame.copy()
    private val frame = ERect()

    override fun animateTo(progress: Float) {
        frame.lerp(progress, from, to)
        toRef.arrange(frame)
    }

}

interface ETransitionAnimator {
    fun animateTo(progress: Float)
}

abstract class SingleElementAnimator<T : Any>(val ref: ELayoutRef<T>) : ETransitionAnimator
abstract class UpdateAnimator<T : Any>(val fromRef: ELayoutRef<T>, val toRef: ELayoutRef<T>) : ETransitionAnimator {

    interface Builder<T : Any> {
        fun build(from: ELayoutRef<T>, to: ELayoutRef<T>): UpdateAnimator<T>
    }
}

