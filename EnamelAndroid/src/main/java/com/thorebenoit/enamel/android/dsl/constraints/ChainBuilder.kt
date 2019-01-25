package com.thorebenoit.enamel.android.dsl.constraints

import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import com.thorebenoit.enamel.android.dsl.withID

/*
         buildChain{
          +someView
          space(4.dp)
          +someOtherView withWeight 1f
          space(4.dp)

          vertical()
          spread()

         }
          */
inline fun ConstraintSetBuilder.buildChain(
    inside: View,
    viewList: List<View> = emptyList(),
    crossinline block: ChainBuilder.() -> Unit
) = buildChain(inside.withID().id, viewList, block)

inline fun ConstraintSetBuilder.buildChain(
    inside: ViewId = parentId,
    viewList: List<View> = emptyList(),
    block: ChainBuilder.() -> Unit
) =
    with(ChainBuilder(viewList)) {
        block()
        build(inside)
    }

class ChainBuilder(viewList: List<View> = emptyList()) {
    private val views = viewList.toMutableList()
    private val viewWeightMap = mutableMapOf<View, Float>()
    private val viewMarginMap = mutableMapOf<View, Int>()
    private val viewGoneMarginMap = mutableMapOf<View, Int>()

    private var startMargin: Int? = null
    private var startGoneMargin: Int? = null
    var vertical = true
    var horizontal
        get() = !vertical
        set(value) {
            vertical = !value
        }

    var defaultMargin = 0
    var defaultGoneMargin = 0


    private var chainStyle: ChainStyle = ConstraintSet.CHAIN_PACKED
    fun packed() {
        chainStyle = ConstraintSet.CHAIN_PACKED
    }

    fun spread() {
        chainStyle = ConstraintSet.CHAIN_SPREAD
    }

    fun spreadInside() {
        chainStyle = ConstraintSet.CHAIN_SPREAD_INSIDE
    }

    fun space(margin: Number, goneMargin: Number = margin) {
        val lastView = views.lastOrNull()
        if (lastView == null) {
            startMargin = margin.toInt()
            startGoneMargin = goneMargin.toInt()
        } else {
            viewMarginMap[lastView] = margin.toInt()
            viewGoneMarginMap[lastView] = goneMargin.toInt()
        }
    }

    infix fun View.withWeight(weight: Number) {
        viewWeightMap[this] = weight.toFloat()
    }

    operator fun View.unaryPlus() = addView(this)

    fun addView(v: View): View {
        views += v
        return v
    }

    fun build(inside: View, with: ConstraintSetBuilder) = with(with) { build(inside) }
    fun build(inside: ViewId, with: ConstraintSetBuilder) = with(with) { build(inside) }


    fun ConstraintSetBuilder.build(inside: View) = build(inside.withID().id)

    fun ConstraintSetBuilder.build(inside: ViewId) {
        with(views) {
            firstOrNull()?.let { v ->
                if (vertical) {
                    v.setVerticalChainStyle(chainStyle)
                } else {
                    v.setHorizontalChainStyle(chainStyle)
                }
            }
            val start: Side = if (vertical) ConstraintSet.TOP else ConstraintSet.START
            val end: Side = if (vertical) ConstraintSet.BOTTOM else ConstraintSet.END

            forEachIndexed { index, v ->
                val margin = viewMarginMap[v] ?: defaultMargin
                val goneMargin = viewGoneMarginMap[v] ?: defaultGoneMargin

                if (index == 0) {
                    v.connect(start to start of inside with (startMargin ?: defaultMargin))
                    constraintSet.setGoneMargin(v.id, start, (startGoneMargin ?: defaultGoneMargin))
                }
                if (index == lastIndex) {
                    v.connect(end to end of inside with margin)
                    constraintSet.setGoneMargin(v.id, end, goneMargin)
                }

                if (index > 0) {
                    val prev = get(index - 1)
                    v.connect(start to end of prev)
                    constraintSet.setGoneMargin(v.id, start, goneMargin)
                }
                if (index < lastIndex) {
                    val next = get(index + 1)
                    v.connect(end to start of next with margin)
                    constraintSet.setGoneMargin(v.id, end, goneMargin)
                }

                viewWeightMap[v]?.let { weight ->
                    if (vertical) {
                        v.setVerticalWeight(weight)
                    } else {
                        v.setHorizontalWeight(weight)
                    }
                }
            }
        }
    }

}