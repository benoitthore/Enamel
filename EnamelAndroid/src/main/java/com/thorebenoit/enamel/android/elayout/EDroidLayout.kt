package com.thorebenoit.enamel.android.elayout

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.addListener
import com.thorebenoit.enamel.android.dsl.views.backgroundColor
import com.thorebenoit.enamel.android.dsl.views.textColor
import com.thorebenoit.enamel.android.dsl.views.textView
import com.thorebenoit.enamel.android.threading.mainThreadCoroutine
import com.thorebenoit.enamel.kotlin.animations.lerp
import com.thorebenoit.enamel.kotlin.core.color.*
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.figures.ERect
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESize
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.arranged
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.sizedSquare
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.snugged
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.stacked
import com.thorebenoit.enamel.kotlin.geometry.layout.playground.PlaygroundServer
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.ELayoutRef
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.ELayoutRefObject
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.ELayoutTag
import com.thorebenoit.enamel.kotlin.geometry.layout.transition.ETransition
import com.thorebenoit.enamel.kotlin.geometry.layout.transition.SingleElementAnimator
import com.thorebenoit.enamel.kotlin.threading.CoroutineLock
import com.thorebenoit.enamel.kotlin.threading.coroutine


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
            view.requestLayout()
        },
        _serialize = { serializer ->
            (view.tag as? String)?.let {
                serializer.add(true)
                serializer.add(it)
            } ?: run {
                serializer.add(false)
            }
        },
        _deserialize = { deserializer ->
            // TODO The issue here is that readString corresponds to ELayoutTag add(String) and both aren't really linked in anyway. It's not "safe" no future proof
            val tag = deserializer.readString()

            val newView: T = viewGroup.viewList.find { it.tag == tag } as T

            ref.removeFromParent()
            ref = newView.createLayoutRef(viewGroup)
        }
    )
}


class EDroidLayout : ViewGroup {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        backgroundColor = black
        Color.LTGRAY
        setWillNotDraw(false)

        startServer()
    }

    val tv1 = textView("AA") {
        backgroundColor = red
        textSize = 20f
//        gravity = Gravity.CENTER
        textColor = white
        tag = "A"
    }
    val tv2 = textView("BBBB") {
        backgroundColor = green
        textSize = 20f
        textColor = black
//        gravity = Gravity.CENTER
        tag = "B"
    }
    val tv3 = textView("CCCCCCCC") {
        backgroundColor = blue
        textSize = 20f
        textColor = white
//        gravity = Gravity.CENTER
        tag = "C"
    }

    val viewList = listOf(tv1, tv2, tv3)


    private val transition = ETransition<View>(
        executeOnUiThread = { mainThreadCoroutine(it) },
        doAnimation = { duration, animation -> doAnimationPlug(duration, animation) },
        getExitAnimation = { ref, rect ->
            object : SingleElementAnimator<View>(ref, rect) {
                override fun animateTo(progress: Float) {
                    ref.view.alpha = 1f - progress
                    ref.view.invalidate()
                }

            }
        },
        getEnterAnimation = { ref, rect ->
            object : SingleElementAnimator<View>(ref, rect) {
                override fun animateTo(progress: Float) {
                    ref.view.alpha = progress
                    ref.view.invalidate()
                }

            }
        }

    )

    fun goToLayout(layout: ELayout) {
        transition.to(layout, eframe.copy())
//        layout.arrange(eframe)
    }

    val eframe: ERectType get() = _eframe
    private var _eframe: ERect = ERect()

    val paddedFrame: ERectType get() = _paddedFrame
    private var _paddedFrame: ERect = ERect()

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val left = 0f
        val top = 0f
        val right = r.toFloat() - l
        val bottom = b.toFloat() - t

        _eframe.setSides(
            left = left,
            top = top,
            right = right,
            bottom = bottom
        )

        _eframe.padding(
            left = paddingLeft,
            right = paddingRight,
            top = paddingTop,
            bottom = paddingBottom,
            buffer = _paddedFrame
        )

        // TODO Handle with transition
    }


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