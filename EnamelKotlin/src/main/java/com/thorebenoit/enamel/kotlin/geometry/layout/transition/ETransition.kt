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
    val defaultEnterAnimation: (ELayoutRef<V>) -> SingleElementAnimator<V>,
    val defaultExitAnimation: (ELayoutRef<V>) -> SingleElementAnimator<V>,
    val defaultUpdateAnimation: UpdateAnimator.Builder<V>,
    var bounds: ERectType? = null
) {

    private var layout: ELayout? = null

    fun to(newLayout: ELayout, bounds: ERectType? = null) {
        val bounds = bounds ?: this.bounds ?: throw Exception("No bound provided, transition cannot proceed")

        // If we don't have a layout yet, just lay views in without anymations
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

        val changeBounds = mutableListOf<EChangeBoundAnimator<V>>()

        newLayout.getAllChildren().forEach {
            (it as? ELayoutRef<V>)?.let { new ->

                updatingRefs[new]?.let { old ->
                    changeBounds += EChangeBoundAnimator(from = old, to = new)
                }
                new.isInMeasureMode = false
            }
        }

        executeOnUiThread {

        }


//        oldRefs.forEach {
//            it.ref.removeFromParent()
//        }
//
//        newRefs.forEach {
//            it.ref.addToParent()
//        }

    }


}
