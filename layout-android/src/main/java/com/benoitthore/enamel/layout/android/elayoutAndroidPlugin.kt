package com.benoitthore.enamel.layout.android

import android.animation.Animator
import android.animation.ValueAnimator
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.benoitthore.enamel.core.animations.EasingInterpolators
import com.benoitthore.enamel.core.threading.CoroutineLock
import com.benoitthore.enamel.geometry.figures.ESizeMutable
import com.benoitthore.enamel.geometry.layout.playground.PlaygroundServer
import com.benoitthore.enamel.geometry.layout.refs.ELayoutRef
import com.benoitthore.enamel.geometry.layout.refs.ELayoutRefObject
import com.benoitthore.enamel.geometry.layout.serializer.ELayoutDeserializer
import com.benoitthore.enamel.geometry.layout.transition.ETransition
import com.benoitthore.enamel.geometry.layout.transition.SingleElementAnimator
import kotlinx.coroutines.Dispatchers
import kotlin.system.exitProcess

private fun Throwable.log(tag: String = "ERROR") = Log.e(tag, message, this)

fun <T : EViewGroup> T.startServer(port: Int = PlaygroundServer.defaultPort): T {

    val deserializer = ELayoutDeserializer()
    // Add more deserializer here

    PlaygroundServer.start(
        deserializer = deserializer,
        port = port,
        onError = { e -> e.log(); exitProcess(0) },
        onNewLayout = { newLayout ->
            mainThreadCoroutine {
                transitionTo(newLayout)
            }
        }
    )

    Log.i("ELayout", "Make sure to run \"adb forward tcp:$port tcp:$port\"")

    return this
}

private typealias Interpolator = (Float) -> Float

private val Interpolator.android
    get() = object : android.view.animation.Interpolator {
        override fun getInterpolation(input: Float): Float = invoke(input)
    }

fun androidDefaultTransition() = ETransition<View>(
    mainThreadDispatcher = Dispatchers.Main,
    doAnimation = { duration, animator ->
        ValueAnimator.ofFloat(0f, 1f).apply {
            this.duration = duration
            val lock = CoroutineLock()

            interpolator = EasingInterpolators.cubicInOut.android

            addUpdateListener {
                animator(it.animatedFraction)
            }

            addListener(
                object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        lock.unlock()
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                        lock.unlock()
                    }

                    override fun onAnimationStart(animation: Animator?) {

                    }
                }
            )
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
        isSameView = { this.tag != null && this.tag == it.tag }
    )
}


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
        }
    )
}