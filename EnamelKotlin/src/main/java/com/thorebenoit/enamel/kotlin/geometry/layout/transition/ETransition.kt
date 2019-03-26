package com.thorebenoit.enamel.kotlin.geometry.layout.transition

import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.ELayoutRef
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.getAllChildren
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.getRefs
import com.thorebenoit.enamel.kotlin.threading.coroutine
import kotlinx.coroutines.CoroutineScope
import java.lang.Exception


/**
 *  DONE - getEnterAnimation and getExitAnimation should take old and new bounds

// TODO
 *  - Create playground version for transition:
 *      -Refactor serialization using fully mutable layouts, child being leafs when constructor just been called
 *
 *      - Should de/serialize be in ELayout or in a separate class?
 *          Needs to be in a separate class for binding
 *          In any case, ELayout should have a default empty constructor
 *
 *      -Find a way to create the appropriate child layout when deserializing
 *          (De)Serialize using an object, this object contains a map with IDs for the different layouts and can create them
 *
 *      Find a way to (de)serialize ELayoutRef across platforms


 *  - return transition object that contains the code of that runs on executeOnUiThread
 *  -
 *  - Improve performance
 */
class ETransition<V : Any>(
    val executeOnUiThread: (suspend CoroutineScope.() -> Unit) -> Unit,
    val doAnimation: suspend (Long, (Float) -> Unit) -> Unit,
    val getEnterAnimation: (ELayoutRef<V>, ERectType) -> SingleElementAnimator<V>,
    val getExitAnimation: (ELayoutRef<V>, ERectType) -> SingleElementAnimator<V>,
    val getUpdateAnimation: UpdateAnimator.Builder<V>,
    var bounds: ERectType? = null
) {

    private var layout: ELayout? = null

    private var isInTransition = false

    fun to(newLayout: ELayout, bounds: ERectType? = null) : Boolean {
        if (isInTransition) {

            return false
        }

        isInTransition = true
        val bounds = bounds ?: this.bounds ?: throw Exception("No bound provided, transition cannot proceed")
        val newRefs = newLayout.getRefs<V>()


        // If we don't have a layout yet, just lay views in without animation
        val oldLayout = this.layout ?: run {

            newLayout.arrange(bounds)
            newRefs.forEach {
                getEnterAnimation(it, it.frame).animateTo(1f)
            }
            this.layout = newLayout
            isInTransition = false
            return true
        }

        this.layout = newLayout

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
                    updateAnimations += getUpdateAnimation.build(from = old, to = new, ref = new)
                }
                new.isInMeasureMode = false
            }
        }

        ////////////////////////////
        //  /Setting Up Animators //
        ////////////////////////////


        // TODO Extract to separate function to increase readability ?
        /// Do animation
        executeOnUiThread {

            "Start: View OUT".print
            // OUT
            doAnimation(100L) { progress ->
                outAnimations.forEach { animator -> animator.animateTo(progress) }
            }
            oldRefs.forEach {
                it.ref.removeFromParent()
            }

            "Start: View UPDATE".print
            // UPDATE
            doAnimation(333L) { progress ->
                updateAnimations.forEach { animator -> animator.animateTo(progress) }
            }

            "Start: View IN".print
            // IN
            newRefs.forEach {
                it.ref.addToParent()
            }

            inAnimations.forEach { animator -> animator.animateTo(0f) }
            newLayout.arrange(bounds)
            doAnimation(100L) { progress ->
                inAnimations.forEach { animator -> animator.animateTo(progress) }
            }
            "Animation done".print

            isInTransition = false
        }


        return true

    }


}