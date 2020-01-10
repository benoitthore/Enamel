package com.benoitthore.enamel.layout.android

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.benoitthore.enamel.core.WorkInProgress
import java.lang.Exception
import com.benoitthore.enamel.core.math.i
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.geometry.figures.ESizeMutable
import com.benoitthore.enamel.geometry.layout.EEmptyLayout
import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.geometry.layout.ELayoutLeaf
import com.benoitthore.enamel.geometry.layout.refs.*


open class EViewGroup : ViewGroup {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

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

    var layout: ELayout = EEmptyLayout
    private set

    @WorkInProgress
    private fun updateLayout(animate: Boolean = true) {
        if (width == 0 || height == 0) {
            doOnNextLayout {
                transition.to(layout, eframe, animate)
            }
        } else {

            transition.to(layout, eframe, animate)
        }

    }

    @WorkInProgress
    fun transitionTo(layout: ELayout, animate: Boolean = true) {
        this.layout = layout
        updateLayout(animate)
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

//        // TODO Fix this hack, arrange should only be called by ETransition
        if (!transition.isInTransition) {
            layout.arrange(eframe)
        }

    }

    private val debugPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.argb(255 / 4, 255, 0, 0)
    }

    override fun onDraw(canvas: Canvas) {
        layout.getAllChildrenWithType<ELayoutLeaf>()?.forEach { leaf ->
            leaf.debugDraw(canvas)
        }
    }


    private fun ELayoutLeaf.debugDraw(canvas: Canvas) {
        debugPaint.color = color
        frame.apply {
            canvas.drawRect(left, top, right, bottom, debugPaint)
        }
    }

    fun <T : View> T.laid(tag: Any? = null) = laid().apply {
        if (tag != null) {
            withTag(tag)
        }
    }

    fun <T : View> T.laid() = laidIn(this@EViewGroup)
    fun <T : View> List<T>.laid() = laidIn(this@EViewGroup)

}