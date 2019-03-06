package com.thorebenoit.enamel.android.elayout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.thorebenoit.enamel.android.dsl.views.backgroundColor
import com.thorebenoit.enamel.android.dsl.withID
import com.thorebenoit.enamel.kotlin.core.backingfield.ExtraValueHolder
import com.thorebenoit.enamel.kotlin.core.color.randomColor
import com.thorebenoit.enamel.kotlin.core.color.*
import com.thorebenoit.enamel.kotlin.core.of
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.figures.ERect
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectSides
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayoutAlongAxis
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayoutLeaf
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.*
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.ELayoutRef
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.ELayoutRefObject
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.getObjects

fun ELayout.getViews() = getObjects<View>()


class ELayoutFrame : ViewGroup {

    private fun View.toLayoutRef(): ELayoutRef<View> {

        return ELayoutRef(
            ELayoutRefObject(
                viewRef = this,
                addToParent = {
                    if (parent == null) {
                        addView(this)
                    }
                },
                removeFromParent = {
                    (parent as? ViewGroup)?.removeView(this)
                }
            ),
            sizeToFIt = { size ->
                size
            },
            arrangeIn = { frame ->

                layout(
                    frame.left.toInt(),
                    frame.top.toInt(),
                    frame.right.toInt(),
                    frame.bottom.toInt()
                )
            },
            childLayouts = emptyList()
        )
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        setWillNotDraw(false)
    }

    val layout: ELayout = 3.of { i ->
        TextView(context).apply {
            text = "TextView #$i"
            backgroundColor = red
        }.toLayoutRef()
    }
        .mapIndexed { i, layout ->
            layout.sizedSquare((i + 1) * 100)
        }
        .stacked(EAlignment.rightTop, spacing = 10)
        .snugged()
        .arranged(EAlignment.middle)
//        .padded(20)

    val eframe: ERectType get() = _eframe
    private var _eframe: ERect = ERect()

    val paddedFrame: ERectType get() = _paddedFrame
    private var _paddedFrame: ERect = ERect()

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val left = l.toFloat()
        val top = t.toFloat()
        val right = r.toFloat()
        val bottom = b.toFloat()

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

        layout.arrange(_eframe)
    }


}

