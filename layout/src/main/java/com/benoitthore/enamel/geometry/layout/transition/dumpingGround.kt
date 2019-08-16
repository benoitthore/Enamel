package com.benoitthore.enamel.geometry.layout.transition

import com.benoitthore.enamel.geometry.lerp
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.layout.refs.ELayoutRef


/*
 UI Framework implementation should provide animator implementation
  */

class EChangeBoundAnimator<T : Any>(ref: ELayoutRef<T>, from: ERect, to: ERect) :
    UpdateAnimator<T>(ref, from, to) {


    companion object {
        fun <T : Any> getBuilder() = object : Builder<T> {
            override fun build(ref: ELayoutRef<T>, from: ELayoutRef<T>, to: ELayoutRef<T>): UpdateAnimator<T> =
                EChangeBoundAnimator(ref = ref, from = from.frame, to = to.frame)


        }
    }

    private val frame = ERectMutable()

    override fun animateTo(progress: Float) {
        frame.lerp(progress, from, to)
        ref.arrange(frame)
    }

}

interface ETransitionAnimator<T : Any> {
    fun animateTo(progress: Float)
    val ref: ELayoutRef<T>
}

abstract class SingleElementAnimator<T : Any>(override val ref: ELayoutRef<T>, val bounds: ERect) :
    ETransitionAnimator<T>

abstract class UpdateAnimator<T : Any>(
    override val ref: ELayoutRef<T>,
    from: ERect,
    to: ERect
) : ETransitionAnimator<T> {

    val from = from.copy()
    val to = to.copy()

    interface Builder<T : Any> {
        fun build(ref: ELayoutRef<T>, from: ELayoutRef<T>, to: ELayoutRef<T>): UpdateAnimator<T>
    }
}

