package com.benoitthore.enamel.geometry.layout.transition

import com.benoitthore.enamel.core.math.d
import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.core.time.ETimerAnimator
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.geometry.layout.refs.ELayoutRef
import com.benoitthore.enamel.geometry.layout.refs.getAllChildren
import com.benoitthore.enamel.geometry.layout.refs.getRefs
import kotlinx.coroutines.*
import java.lang.Exception

val defaultDoAnim: suspend (Long, (Float) -> Unit) -> Unit = { duration, animator ->
    val timer = ETimerAnimator(duration)

    timer.start()
    while (timer.progress < 1f) {
        val progress = Math.min(timer.progress.d, 1.0).f
        animator(progress)
        delay(8)
    }
    animator(1f)

}

/**
// TODO
 *  - return transition object that contains the code of that runs on executeOnUiThread
 *  -
 *  - Improve performance
 */
class ETransition<V : Any>(
    val mainThreadDispatcher: CoroutineDispatcher,
    val doAnimation: suspend (Long, (Float) -> Unit) -> Unit = defaultDoAnim,
    val getEnterAnimation: (ELayoutRef<V>, ERect) -> SingleElementAnimator<V>,
    val getExitAnimation: (ELayoutRef<V>, ERect) -> SingleElementAnimator<V>,
    val getUpdateAnimation: UpdateAnimator.Builder<V> = EChangeBoundAnimator.getBuilder(),
    var bounds: ERect? = null
) {

    var layout: ELayout? = null

    var isInTransition = false
        private set

    private fun executeOnUiThread(block: suspend CoroutineScope.() -> Unit) {
        GlobalScope.launch(mainThreadDispatcher, block = block)
    }

    fun to(newLayout: ELayout, bounds: ERect? = null, animate: Boolean = true) {
        val transition = this

        val bounds =
            bounds ?: transition.bounds
            ?: throw Exception("No bound provided, transition cannot proceed")

        if (!animate) {
            layout?.getRefs<V>()?.forEach { it.ref.removeFromParent() }
            newLayout.arrange(bounds)
            layout = newLayout
            return
        }

        executeOnUiThread {

            if (isInTransition) {

                return@executeOnUiThread
            }

            isInTransition = true

            val newRefs = newLayout.getRefs<V>()


            // If we don't have a layout yet, just lay views in without animation
            val oldLayout = transition.layout ?: run {

                newLayout.arrange(bounds)
                newRefs.forEach {
                    getEnterAnimation(it, it.frame).animateTo(1f)
                }
                transition.layout = newLayout
                isInTransition = false
                return@executeOnUiThread
            }

            transition.layout = newLayout

            ///////////////////////////////
            //  Getting Transition data  //
            ///////////////////////////////
            val updatingRefs = mutableMapOf<ELayoutRef<V>, ELayoutRef<V>>() // new -> old
            val goingOutRefs = mutableListOf<ELayoutRef<V>>()
            val goingInRefs = mutableListOf<ELayoutRef<V>>()

            val oldRefs = oldLayout.getRefs<V>()


            var found: Boolean
            // TODO Optimize double `for` loop
            oldRefs.forEach { old ->
                found = false
                newRefs.forEach { new ->
                    // Check view equality
                    if (new.ref.isSameView(old.ref.viewRef)) {
                        // Add mapping between new and old view
                        updatingRefs[new] = old
                        found = true
                    }
                }

                // If you not found in new layout, view going out
                if (!found) {
                    goingOutRefs += old
                }
            }

            // Going through refs in new layout
            newRefs.forEach { new ->
                // If the new ref isn't part of the ones being updated, it has to go in
                if (!updatingRefs.containsKey(new)) {
                    goingInRefs += new
                }
            }
            /////////////////////////////////
            //  /Getting Transition data   //
            /////////////////////////////////


            // TODO Optimize getAllChildrenCall
            newLayout.getAllChildren().forEach {
                if (it as? ELayoutRef<V> != null) {
                    it.isInMeasureMode = true
                }
            }

            // Measure layout without applying changes
            newLayout.arrange(bounds)

            ////////////////////////////
            //  Setting Up Animators  //
            ////////////////////////////


            val outAnimations = goingOutRefs.map { getExitAnimation(it, it.frame) }
            val inAnimations = goingInRefs.map { getEnterAnimation(it, it.frame) }
            val updateAnimations = mutableListOf<UpdateAnimator<V>>()

            // TODO use newRefs instead
            newLayout.getAllChildren().forEach {
                (it as? ELayoutRef<V>)?.let { new ->

                    updatingRefs[new]?.let { old ->
                        updateAnimations += getUpdateAnimation.build(
                            from = old,
                            to = new,
                            ref = new
                        )
                    }
                    new.isInMeasureMode = false
                }
            }

            ////////////////////////////
            //  /Setting Up Animators //
            ////////////////////////////


            // TODO Extract to separate function to increase readability ?
            /// Do animation

            // OUT
            if (outAnimations.isNotEmpty()) {
                doAnimation(250L) { progress ->
                    outAnimations.forEach { animator -> animator.animateTo(progress) }
                }
            }
            oldRefs.forEach {
                it.ref.removeFromParent()
            }


            if (updateAnimations.isNotEmpty()) {
                // UPDATE
                doAnimation(1000L) { progress ->
                    updateAnimations.forEach { animator -> animator.animateTo(progress) }
                }
            }

            // IN
            newRefs.forEach {
                it.ref.addToParent()
            }

            inAnimations.forEach { animator -> animator.animateTo(0f) }
            newLayout.arrange(bounds)

            if (inAnimations.isNotEmpty()) {

                doAnimation(250L) { progress ->
                    inAnimations.forEach { animator -> animator.animateTo(progress) }
                }
            }

            transition.isInTransition = false

        }
    }
}