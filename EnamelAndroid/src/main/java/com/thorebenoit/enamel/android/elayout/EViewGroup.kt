package com.thorebenoit.enamel.android.elayout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.doOnNextLayout
import com.thorebenoit.enamel.android.dsl.contextConstructor
import com.thorebenoit.enamel.android.dsl.views.backgroundColor
import com.thorebenoit.enamel.android.dsl.views.textColor
import com.thorebenoit.enamel.kotlin.core.color.*
import com.thorebenoit.enamel.geometry.figures.ERect
import com.thorebenoit.enamel.geometry.figures.ERectType
import com.thorebenoit.enamel.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayoutLeaf
import com.thorebenoit.enamel.geometry.layout.refs.getLeafs
import java.lang.Exception
import androidx.core.view.children
import com.thorebenoit.enamel.core.math.i
import com.thorebenoit.enamel.geometry.figures.ESizeMutable


class EViewGroup : ViewGroup {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun <T : View> prepareView(clazz: Class<T>, tag: String, builder: T.() -> Unit): T {
        val view = clazz.contextConstructor.newInstance(context)

        view.tag = tag
        view.builder()

        _viewList.add(view)
        return view
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


    private val _measureSizeBuffer = ESizeMutable()
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        _measureSizeBuffer.set(
            width = MeasureSpec.getSize(widthMeasureSpec),
            height = MeasureSpec.getSize(heightMeasureSpec)
        )
        val targetSize = transition.layout?.size(_measureSizeBuffer) ?: _measureSizeBuffer

        super.onMeasure(
            MeasureSpec.makeMeasureSpec(targetSize.width.i,MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(targetSize.height.i,MeasureSpec.EXACTLY))
    }

    private val transition = androidDefaultTransition()


    fun goToLayout(layout: ELayout) {
        if (width == 0 || height == 0) {
            doOnNextLayout {
                goToLayout(layout)
            }
        } else {
            transition.to(layout, eframe)
            requestLayout()
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

//        // TODO Handle with transition
        transition.layout?.let {
            it.arrange(eframe)
        }

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

    fun getRefFromTag(tag: String): ELayout {
        var view = viewList.firstOrNull { it.tag == tag }

        // TODO This isn't perfect + risk of leaks
        if (view == null) {
            view = children
                .firstOrNull { it.tag == tag }
                ?.also {
                    _viewList += it
                }
        }

        return view?.laidIn(this) ?: throw Exception("Unknown tag $tag")

    }


}


