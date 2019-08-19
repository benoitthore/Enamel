package com.benoitthore.enamel.layout_android

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnNextLayout
import java.lang.Exception
import androidx.core.view.children
import com.benoitthore.enamel.core.math.i
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.geometry.figures.ESizeMutable
import com.benoitthore.enamel.geometry.layout.EEmptyLayout
import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.geometry.layout.refs.getLeaves
import com.benoitthore.enamel.geometry.layout.ELayoutLeaf
import com.benoitthore.enamel.geometry.layout.refs.ELayoutRef
import com.benoitthore.enamel.geometry.layout.refs.ELayoutTag
import com.benoitthore.enamel.geometry.layout.refs.getAllChildren
import java.lang.reflect.Constructor


private inline val <T : View> Class<T>.contextConstructor: Constructor<T>
    get() = constructors.filter {
        val params = it.parameterTypes
        return@filter params.size == 1 && params[0] == Context::class.java
    }.first() as Constructor<T>


open class EViewGroup : ViewGroup {

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


    // List of views that can be found by tags
    val viewList: Set<View> get() = _viewList
    private val _viewList: MutableSet<View> = mutableSetOf()

    init {
        post { setWillNotDraw(false) }
    }


    private val _measureSizeBuffer = ESizeMutable()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var w: Number = MeasureSpec.getSize(widthMeasureSpec)
        var h: Number = MeasureSpec.getSize(heightMeasureSpec)
        _measureSizeBuffer.set(
            width = w,
            height = h
        )

        val targetSize = layout.size(_measureSizeBuffer)

        w =
            if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) w else targetSize.width
        h =
            if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) h else targetSize.height

        super.onMeasure(
            MeasureSpec.makeMeasureSpec(w.i, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(h.i, MeasureSpec.EXACTLY)
        )
    }

    private val transition = androidDefaultTransition()

    val isInTransition get() = transition.isInTransition

    var layout: ELayout
        get() = transition.layout ?: EEmptyLayout
        set(value) {
            val originalLayout = layout
            transition.layout = value

            if (width == 0 || height == 0) {
                doOnNextLayout {
                    layout = value
                }
            } else {

                // remove previous children
                originalLayout.getAllChildren().forEach {
                    if (it is ELayoutRef<*>) {
                        (it.ref.viewRef as View).let(::removeView)
                    }
                }

                layout.updateLeaves()
                value.arrange(eframe)
            }
        }

    fun transitionTo(layout: ELayout) {

        if (width == 0 || height == 0) {
            doOnNextLayout {
                transitionTo(layout)
            }
        } else {
        layout.updateLeaves()
            transition.to(layout, eframe)
        }
    }


    val eframe: ERect get() = _eframe
    private var _eframe: ERectMutable = ERectMutable()

    val paddedFrame: ERect get() = _paddedFrame
    private var _paddedFrame: ERectMutable = ERectMutable()

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

//        // TODO Fix this hack
        if (!transition.isInTransition) {
            layout.arrange(eframe)
        }

    }

    private fun ELayout.updateLeaves() {
        getLeaves().forEach { leaf ->
            (leaf.child as? ELayoutTag)?.let {
                leaf.child = getRefFromTag(it.tag)
            }
        }
    }

    private val debugPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.argb(255 / 4, 255, 0, 0)
    }

    override fun onDraw(canvas: Canvas) {
        transition.layout?.getLeaves()?.forEach { leaf ->
            leaf.debugDraw(canvas)
        }
    }


    private fun ELayoutLeaf.debugDraw(canvas: Canvas) {
        debugPaint.color = color
        frame.apply {
            canvas.drawRect(left, top, right, bottom, debugPaint)
        }
    }

    open fun getRefFromTag(tag: String): ELayout {
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


    fun <T : View> T.eLayoutRef(tag: Any? = null) = eLayoutRef().apply {
        if (tag != null) {
            withTag(tag)
        }
    }
    fun <T : View> T.eLayoutRef() = laidIn(this@EViewGroup)
    fun <T : View> List<T>.eLayoutRef() = laidIn(this@EViewGroup)

}