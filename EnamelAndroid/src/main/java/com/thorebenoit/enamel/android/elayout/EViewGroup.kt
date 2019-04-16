package com.thorebenoit.enamel.android.elayout
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnNextLayout
import com.thorebenoit.enamel.kotlin.core.color.*
import java.lang.Exception
import androidx.core.view.children
import com.thorebenoit.enamel.core.math.i
import com.thorebenoit.enamel.geometry.figures.ERect
import com.thorebenoit.enamel.geometry.figures.ERectMutable
import com.thorebenoit.enamel.geometry.figures.ESizeMutable
import com.thorebenoit.enamel.geometry.layout.EEmptyLayout
import com.thorebenoit.enamel.geometry.layout.ELayout
import com.thorebenoit.enamel.geometry.layout.refs.getLeafs
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayoutLeaf
import java.lang.reflect.Constructor


private inline val <T : View> Class<T>.contextConstructor: Constructor<T>
    get() = constructors.filter {
        val params = it.parameterTypes
        return@filter params.size == 1 && params[0] == Context::class.java
    }.first() as Constructor<T>


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
    }


    private val _measureSizeBuffer = ESizeMutable()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var w: Number = MeasureSpec.getSize(widthMeasureSpec)
        var h: Number = MeasureSpec.getSize(heightMeasureSpec)
        _measureSizeBuffer.set(
            width = w,
            height = h
        )

        val targetSize = transition.layout?.size(_measureSizeBuffer) ?: _measureSizeBuffer

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

    var layout: ELayout
        get() = transition.layout ?: EEmptyLayout
        set(value) {
            if (width == 0 || height == 0) {
                doOnNextLayout {
                    layout = value
                }
            } else {
                transition.layout = value
                value.arrange(eframe)
            }
        }

    fun transitionTo(layout: ELayout) {
        if (width == 0 || height == 0) {
            doOnNextLayout {
                transitionTo(layout)
            }
        } else {
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

            transition.layout?.let {
                it.arrange(eframe)
            }
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