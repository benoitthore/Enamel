package com.thorebenoit.enamel.kotlin.geometry.layout.transition

import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.ELayoutRef
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.getAllChildren
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.getRefs
import com.thorebenoit.enamel.kotlin.threading.coroutine
import kotlinx.coroutines.CoroutineScope
import java.lang.Exception

enum class ETransitionState {
    start,
    prepareToEnter,
    exit,
    prepareToUpdate,
    update,
    enter,
    end
}

class ETransition<V : Any>(
    val executeOnUiThread: (suspend CoroutineScope.() -> Unit) -> Unit,
    val doAnimation: suspend (Long, (Float) -> Unit) -> Unit,
    val getEnterAnimation: (ELayoutRef<V>) -> SingleElementAnimator<V>,
    val getExitAnimation: (ELayoutRef<V>) -> SingleElementAnimator<V>,
    val getUpdateAnimation: UpdateAnimator.Builder<V>,
    var bounds: ERectType? = null
) {

    private var layout: ELayout? = null

    fun to(newLayout: ELayout, bounds: ERectType? = null) {
        val bounds = bounds ?: this.bounds ?: throw Exception("No bound provided, transition cannot proceed")

        // If we don't have a layout yet, just lay views in without animation
        val oldLayout = this.layout ?: run {
            newLayout.arrange(bounds)
            return
        }


        ///////////////////////////////
        //  Getting Transition data  //
        ///////////////////////////////
        val updatingRefs = mutableMapOf<ELayoutRef<V>, ELayoutRef<V>>() // new -> old
        val goingOutRefs = mutableListOf<ELayoutRef<V>>()
        val goingInRefs = mutableListOf<ELayoutRef<V>>()

        val oldRefs = oldLayout.getRefs<V>()
        val newRefs = newLayout.getRefs<V>()


        var found: Boolean
        // TODO Optimize double `for` loop
        oldRefs.forEach { old ->
            found = false
            newRefs.forEach { new ->
                if (new.ref.isSameView(old.ref.viewRef)) {
                    updatingRefs[new] = old
                    found = true
                }
            }

            if (!found) {
                goingOutRefs += old
            }
        }

        newRefs.forEach { new ->
            if (!updatingRefs.containsKey(new)) {
                goingInRefs += new
            }
        }
        /////////////////////////////////
        //  /Getting Transition data   //
        /////////////////////////////////


        // TODO Optimize getAllChildrenCall
        newLayout.getAllChildren().forEach {
            if (it is ELayoutRef<*>) {
                it.isInMeasureMode = true
            }
        }

        // Measure layout without applying changes
        newLayout.arrange(bounds)

        val outAnimations = oldRefs.map { getExitAnimation(it) }
        val inAnimations = newRefs.map { getEnterAnimation(it) }
        val updateAnimations = mutableListOf<UpdateAnimator<V>>()

        newLayout.getAllChildren().forEach {
            (it as? ELayoutRef<V>)?.let { new ->

                updatingRefs[new]?.let { old ->
                    updateAnimations += getUpdateAnimation.build(from = old, to = new)
                }
                new.isInMeasureMode = false
            }
        }



        executeOnUiThread {

            // OUT
            doAnimation(1000L) { progress ->
                outAnimations.forEach { animator -> animator.animateTo(progress) }
            }
            oldRefs.forEach {
                it.ref.removeFromParent()
            }

            // UPDATE
            doAnimation(1000L) { progress ->
                updateAnimations.forEach { animator -> animator.animateTo(progress) }
            }

            // IN
            newRefs.forEach {
                it.ref.addToParent()
            }
            doAnimation(1000L) { progress ->
                inAnimations.forEach { animator -> animator.animateTo(progress) }
            }
        }


    }


}