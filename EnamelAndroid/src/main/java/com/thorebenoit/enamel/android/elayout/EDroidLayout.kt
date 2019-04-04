package com.thorebenoit.enamel.android.elayout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.doOnNextLayout
import com.thorebenoit.enamel.android.dsl.contextConstructor
import com.thorebenoit.enamel.android.dsl.views.backgroundColor
import com.thorebenoit.enamel.android.dsl.views.textColor
import com.thorebenoit.enamel.android.dsl.views.textView
import com.thorebenoit.enamel.kotlin.core.color.*
import com.thorebenoit.enamel.kotlin.core.tryAndForget
import com.thorebenoit.enamel.kotlin.geometry.figures.ERect
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayoutLeaf
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.getLeafs
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.getRefs
import java.lang.Exception

class EDroidLayout : ViewGroup {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    fun <T : View> prepareView(clazz: Class<T>, tag: String, builder: T.() -> Unit) {
        val view = clazz.contextConstructor.newInstance(context)

        view.tag = tag
        view.builder()

        _viewList.add(view)
    }

    inline fun <reified T : View> prepareView(tag: String, noinline builder: T.() -> Unit = {}) =
        prepareView(T::class.java, tag, builder)


    val viewList: List<View> get() = _viewList
    private val _viewList: MutableList<View> = mutableListOf()

    init {
        setWillNotDraw(false)

        ('A'..'Z')
            .forEach { _tag ->

                val i = (_tag - 'A' + 1) * 2
                val str = (0 until i).map { _tag }.joinToString(separator = "")

                prepareView<TextView>(_tag.toString()) {
                    backgroundColor = blue
                    textSize = 20f
                    text = str
                    textColor = white
                }
            }
    }


    private val transition = androidDefaultTransition()


    fun goToLayout(layout: ELayout) {
        if (width == 0 || height == 0) {
            doOnNextLayout {
                goToLayout(layout)
            }
        } else {
            transition.to(layout, eframe)
        }
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


    private val debugPaint = Paint().apply {
        style = Paint.Style.FILL
        color = red.withAlpha(0.5)
    }

    override fun onDraw(canvas: Canvas) {
        transition.layout?.getLeafs()?.forEach { leaf ->
            leaf.debugDraw(canvas)
        }
    }


    fun ELayoutLeaf.debugDraw(canvas: Canvas) {
        debugPaint.color = color
        frame.apply {
            canvas.drawRect(left, top, right, bottom, debugPaint)
        }
    }

    // TODO Call this function when transition is done through code instead of network
    /**
     * ELayoutTag shouldn't be converted until goToLayout is called
     */
    fun getRefFromTag(tag: String): ELayout = viewList
        .firstOrNull { it.tag == tag }
        ?.laidIn(this)
        ?: run {
            throw Exception("Unknown tag $tag")
        }
}


