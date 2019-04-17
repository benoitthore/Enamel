package com.thorebenoit.enamel.android.elayout

import android.animation.ValueAnimator
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.addListener
import com.thorebenoit.enamel.android.threading.mainThreadCoroutine
import com.thorebenoit.enamel.core.animations.EasingInterpolators
import com.thorebenoit.enamel.core.threading.CoroutineLock
import com.thorebenoit.enamel.geometry.figures.ESizeMutable
import com.thorebenoit.enamel.geometry.layout.playground.PlaygroundServer
import com.thorebenoit.enamel.geometry.layout.refs.ELayoutRef
import com.thorebenoit.enamel.geometry.layout.refs.ELayoutRefObject
import com.thorebenoit.enamel.geometry.layout.refs.ELayoutTag
import com.thorebenoit.enamel.geometry.layout.refs.getLeafs
import com.thorebenoit.enamel.geometry.layout.serializer.ELayoutDeserializer
import com.thorebenoit.enamel.geometry.layout.transition.ETransition
import com.thorebenoit.enamel.geometry.layout.transition.SingleElementAnimator

private fun Throwable.log(tag: String = "ERROR") = Log.e(tag, message, this)

fun EViewGroup.startServer(port: Int = PlaygroundServer.defaultPort) {

    val deserializer = ELayoutDeserializer()
    // Add more deserializer here

    PlaygroundServer.start(
        deserializer = deserializer,
        port = port,
        onError = { e -> e.log(); System.exit(0) },
        onNewLayout = { newLayout ->
            mainThreadCoroutine {

                newLayout.getLeafs().forEach { leaf ->
                    (leaf.child as? ELayoutTag)?.let {
                        leaf.child = getRefFromTag(it.tag)
                    }
                }

                transitionTo(newLayout)
            }
        }
    )
}

private typealias Interpolator = (Float) -> Float

private val Interpolator.android
    get() = object : android.view.animation.Interpolator {
        override fun getInterpolation(input: Float): Float = invoke(input)
    }

fun androidDefaultTransition() = ETransition<View>(
    executeOnUiThread = { mainThreadCoroutine(it) },
    doAnimation = { duration, animator ->
        ValueAnimator.ofFloat(0f, 1f).apply {
            this.duration = duration
            val lock = CoroutineLock()

            interpolator = EasingInterpolators.cubicInOut.android

            addUpdateListener {
                animator(it.animatedFraction)
            }

            addListener(onEnd = { lock.unlock() }, onCancel = { lock.unlock() })
            start()
            lock.wait()
        }
    },
    getExitAnimation = { ref, rect ->
        object : SingleElementAnimator<View>(ref, rect) {
            override fun animateTo(progress: Float) {
                ref.view.alpha = 1f - progress
            }

        }
    },
    getEnterAnimation = { ref, rect ->
        object : SingleElementAnimator<View>(ref, rect) {
            override fun animateTo(progress: Float) {
                ref.view.alpha = progress
            }

        }
    }
)


fun <T : View> List<T>.laidIn(frame: EViewGroup): List<ELayoutRef<T>> = map { it.laidIn(frame) }

private fun <T : View> T.createLayoutRef(viewGroup: EViewGroup): ELayoutRefObject<T> {
    return ELayoutRefObject(
        viewRef = this,
        addToParent = {
            if (parent != viewGroup) {
                (parent as? ViewGroup)?.removeView(this)
            }
            if (parent == null) {
                viewGroup.addView(this)
            }
        },
        removeFromParent = {
            (parent as? ViewGroup)?.removeView(this)
        },
        isSameView = { this.tag == it.tag }
    )
}


private inline val <T : View> ELayoutRef<T>.view get() = ref.viewRef
fun <T : View> T.laidIn(viewGroup: EViewGroup): ELayoutRef<T> {
    val sizeBuffer = ESizeMutable()

    return ELayoutRef(
        createLayoutRef(viewGroup),
        sizeToFit = { size ->
            if (view.visibility == View.GONE) {
                sizeBuffer.set(0, 0)
            } else {
                view.measure(
                    View.MeasureSpec.makeMeasureSpec(size.width.toInt(), View.MeasureSpec.AT_MOST),
                    View.MeasureSpec.makeMeasureSpec(size.height.toInt(), View.MeasureSpec.AT_MOST)
                )
                sizeBuffer.set(view.measuredWidth, view.measuredHeight)
            }
        },
        arrangeIn = { frame ->
            view.layout(
                frame.left.toInt(),
                frame.top.toInt(),
                frame.right.toInt(),
                frame.bottom.toInt()
            )
            view.requestLayout()
        }
    )
}