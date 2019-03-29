package com.thorebenoit.enamel.android.elayout

import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.addListener
import com.thorebenoit.enamel.android.threading.mainThreadCoroutine
import com.thorebenoit.enamel.kotlin.geometry.figures.ESize
import com.thorebenoit.enamel.kotlin.geometry.layout.playground.PlaygroundServer
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.ELayoutRef
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.ELayoutRefObject
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.ELayoutTag
import com.thorebenoit.enamel.kotlin.geometry.layout.transition.ETransition
import com.thorebenoit.enamel.kotlin.geometry.layout.transition.SingleElementAnimator
import com.thorebenoit.enamel.kotlin.threading.CoroutineLock


fun EDroidLayout.startServer() {

    PlaygroundServer().start(
        newInstance = { clazz ->
            if (clazz == ELayoutTag::class.java) {
                View(context).laidIn(this@startServer)
            } else {
                clazz.newInstance()
            }
        },
        onNewLayout = { newLayout ->
            mainThreadCoroutine {
                goToLayout(newLayout)
            }
        }
    )
}

private suspend fun doAnimationPlug(duration: Long, block: (Float) -> Unit) {
    ValueAnimator.ofFloat(0f, 1f).apply {
        this.duration = duration
        val lock = CoroutineLock()

        addUpdateListener {
            block(it.animatedFraction)
        }

        addListener(onEnd = { lock.unlock() }, onCancel = { lock.unlock() })
        start()
        lock.wait()
    }
}


fun androidDefaultTransition() = ETransition<View>(
    executeOnUiThread = { mainThreadCoroutine(it) },
    doAnimation = { duration, animation -> doAnimationPlug(duration, animation) },
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


private fun <T : View> T.createLayoutRef(viewGroup: EDroidLayout): ELayoutRefObject<T> {
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

fun <T : View> List<T>.laidIn(frame: EDroidLayout): List<ELayoutRef<T>> = map { it.laidIn(frame) }

private inline val <T : View> ELayoutRef<T>.view get() = ref.viewRef
fun <T : View> T.laidIn(viewGroup: EDroidLayout): ELayoutRef<T> {
    val sizeBuffer = ESize()

    return ELayoutRef(
        createLayoutRef(viewGroup),
        sizeToFit = { size ->
            view.measure(
                View.MeasureSpec.makeMeasureSpec(size.width.toInt(), View.MeasureSpec.AT_MOST),
                View.MeasureSpec.makeMeasureSpec(size.height.toInt(), View.MeasureSpec.AT_MOST)
            )
            sizeBuffer.set(view.measuredWidth, view.measuredHeight)
        },
        arrangeIn = { frame ->
            view.layout(
                frame.left.toInt(),
                frame.top.toInt(),
                frame.right.toInt(),
                frame.bottom.toInt()
            )
        },
        _serialize = { serializer ->
            // TODO Inconsistent with _deserialize
            (view.tag as? String)?.let {
                serializer.add(true)
                serializer.add(it)
            } ?: run {
                serializer.add(false)
            }
        },
        _deserialize = { deserializer ->
            val tag = deserializer.readString()

            val newView: T = viewGroup.viewList.find { it.tag == tag } as T

            ref.removeFromParent()
            ref = newView.createLayoutRef(viewGroup)
        }
    )
}

