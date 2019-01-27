package com.thorebenoit.enamel.android.dsl.constraints

import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat
import androidx.core.view.children
import com.thorebenoit.enamel.kotlin.core.f
import com.thorebenoit.enamel.android.dsl.withID
import com.thorebenoit.enamel.android.examples.Example
import com.thorebenoit.enamel.android.examples._Example_ConstraintLayout_Basic

typealias Side = Int
typealias ViewId = Int
typealias ChainStyle = Int
typealias DefaultSize = Int
typealias GuideStyle = Int

fun ConstraintLayout.prepareConstraints(init: ConstraintSetBuilder.() -> Unit): ConstraintSet {

    /* TODO Find a better way
        This is a hack to get the ConstraintLayout to pick-up its children IDs,
        If View.id is set after it's been added to a ConstraintLayout, the ConstraintLayout won't registered the children ID
     */
    val childrens = children.toList()
    removeAllViews()
    childrens.forEach {
        it.withID()
        addView(it)
    }

    return buildConstraintSet(init = init)
}

fun ConstraintLayout.constraints(init: ConstraintSetBuilder.() -> Unit): ConstraintSet {
    val constraintSet = prepareConstraints(init)
    constraintSet.applyTo(this)
    return constraintSet
}

fun ConstraintLayout.buildConstraintSet(init: ConstraintSetBuilder.() -> Unit): ConstraintSet {
    val constraintSet = ConstraintSet()
    constraintSet.clone(this)
    val builder = ConstraintSetBuilder(constraintSet)
    builder.init()
    return builder.constraintSet
}

@Example<_Example_ConstraintLayout_Basic>
@Suppress("MemberVisibilityCanPrivate", "unused", "NOTHING_TO_INLINE", "PropertyName")
class ConstraintSetBuilder(val constraintSet: ConstraintSet) {


    private val TAG: String = javaClass.simpleName

    val parentId: ViewId = ConstraintLayout.LayoutParams.PARENT_ID

    @Deprecated("defaultWidth/Height(matchConstraintWrap) is deprecated. Use width/height(WRAP_CONTENT) and layout_constrainedWidth/Height = true instead.")
    val matchConstraintWrap: DefaultSize = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT_WRAP
    val matchConstraintSpread: DefaultSize = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT_SPREAD
    val matchConstraintPercent: DefaultSize = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT_PERCENT

    val LEFT: Side = ConstraintLayout.LayoutParams.LEFT
    val RIGHT: Side = ConstraintLayout.LayoutParams.RIGHT
    val TOP: Side = ConstraintLayout.LayoutParams.TOP
    val BOTTOM: Side = ConstraintLayout.LayoutParams.BOTTOM
    val BASELINE: Side = ConstraintLayout.LayoutParams.BASELINE
    val START: Side = ConstraintLayout.LayoutParams.START
    val END: Side = ConstraintLayout.LayoutParams.END

    val LEFTS: SideSide = LEFT to LEFT
    val RIGHTS: SideSide = RIGHT to RIGHT
    val TOPS: SideSide = TOP to TOP
    val BOTTOMS: SideSide = BOTTOM to BOTTOM
    val BASELINES: SideSide = BASELINE to BASELINE
    val STARTS: SideSide = START to START
    val ENDS: SideSide = END to END
    val HORIZONTAL: SideSide =
        SideSide(-1, -1)
    val VERTICAL: SideSide =
        SideSide(-2, -2)
    val ALL: SideSide =
        SideSide(-3, -3)

    val CHAIN_SPREAD: ChainStyle = ConstraintLayout.LayoutParams.CHAIN_SPREAD
    val CHAIN_SPREAD_INSIDE: ChainStyle = ConstraintLayout.LayoutParams.CHAIN_SPREAD_INSIDE
    val CHAIN_PACKED: ChainStyle = ConstraintLayout.LayoutParams.CHAIN_PACKED

    val GUIDE_VERTICAL: GuideStyle = ConstraintLayout.LayoutParams.VERTICAL
    val GUIDE_HORIZONTAL: GuideStyle = ConstraintLayout.LayoutParams.HORIZONTAL

    //<editor-fold desc="<< Guidelines definitions >>">
    inline fun guideline(orientation: GuideStyle, init: (Int) -> Unit): Int {
        val guideId = ViewCompat.generateViewId()
        constraintSet.create(guideId, orientation)
        init(guideId)
        return guideId
    }

    inline fun guidelineBegin(orientation: GuideStyle, guide: Int) =
        guideline(orientation) { constraintSet.setGuidelineBegin(it, guide) }

    inline fun guidelineEnd(orientation: GuideStyle, guide: Int) =
        guideline(orientation) { constraintSet.setGuidelineEnd(it, guide) }

    inline fun guidelinePercent(orientation: GuideStyle, guide: Float) =
        guideline(orientation) { constraintSet.setGuidelinePercent(it, guide) }

    inline fun verticalGuidelineBegin(guide: Int) = guidelineBegin(LinearLayout.VERTICAL, guide)
    inline fun verticalGuidelineEnd(guide: Int) = guidelineEnd(LinearLayout.VERTICAL, guide)
    inline fun verticalGuidelinePercent(guide: Float) = guidelinePercent(LinearLayout.VERTICAL, guide)
    inline fun horizontalGuidelineBegin(guide: Int) = guidelineBegin(LinearLayout.HORIZONTAL, guide)
    inline fun horizontalGuidelineEnd(guide: Int) = guidelineEnd(LinearLayout.HORIZONTAL, guide)
    inline fun horizontalGuidelinePercent(guide: Float) = guidelinePercent(LinearLayout.HORIZONTAL, guide)
    //</editor-fold>

    //<editor-fold desc="<< Barrier definitions >>">
    inline fun barrier(direction: Side, vararg views: View): Int {
        val barrierId = ViewCompat.generateViewId()
        constraintSet.createBarrier(barrierId, direction, *views.map { it.id }.toIntArray())
        return barrierId
    }

    inline fun barrierLeft(vararg views: View) = barrier(LEFT, *views)
    inline fun barrierRight(vararg views: View) = barrier(RIGHT, *views)
    inline fun barrierTop(vararg views: View) = barrier(TOP, *views)
    inline fun barrierBottom(vararg views: View) = barrier(BOTTOM, *views)
    inline fun barrierStart(vararg views: View) = barrier(START, *views)
    inline fun barrierEnd(vararg views: View) = barrier(END, *views)
    //</editor-fold>

    //<editor-fold desc="<< Chains definitions >>">


    inline fun <T : View> T.setHorizontalChainStyle(chainStyle: ChainStyle): T {
        constraintSet.setHorizontalChainStyle(this.id, chainStyle)
        return this
    }

    inline fun <T : View> T.setVerticalChainStyle(chainStyle: ChainStyle): T {
        constraintSet.setVerticalChainStyle(this.id, chainStyle)
        return this
    }

    inline fun <T : View> T.addToHorizontalChain(leftId: Int, rightId: Int): T {
        constraintSet.addToHorizontalChain(this.id, leftId, rightId)
        return this
    }

    inline fun <T : View> T.addToHorizontalChainRTL(leftId: Int, rightId: Int): T {
        constraintSet.addToHorizontalChainRTL(this.id, leftId, rightId)
        return this
    }

    inline fun <T : View> T.addToVerticalChain(topId: Int, bottomId: Int): T {
        constraintSet.addToVerticalChain(this.id, topId, bottomId)
        return this
    }

    inline fun <T : View> T.addToHorizontalChain(leftView: View, rightView: View): T {
        constraintSet.addToHorizontalChain(this.id, leftView.id, rightView.id)
        return this
    }

    inline fun <T : View> T.addToHorizontalChainRTL(leftView: View, rightView: View): T {
        constraintSet.addToHorizontalChainRTL(this.id, leftView.id, rightView.id)
        return this
    }

    inline fun <T : View> T.addToVerticalChain(topView: View, bottomView: View): T {
        constraintSet.addToVerticalChain(this.id, topView.id, bottomView.id)
        return this
    }

    inline fun <T : View> T.removeFromVerticalChain(): T {
        constraintSet.removeFromVerticalChain(this.id)
        return this
    }

    inline fun <T : View> T.removeFromHorizontalChain(): T {
        constraintSet.removeFromHorizontalChain(this.id)
        return this
    }

    inline fun <T : View> T.setHorizontalWeight(weight: Float): T {
        constraintSet.setHorizontalWeight(this.id, weight)
        return this
    }

    inline fun <T : View> T.setVerticalWeight(weight: Float): T {
        constraintSet.setVerticalWeight(this.id, weight)
        return this
    }

//</editor-fold>

    //<editor-fold desc="<< Various extensions >>">
    inline fun <T : View> T.clear(): T {
        constraintSet.clear(this.id)
        return this
    }

    inline fun <T : View> T.clear(vararg sides: Side): T {
        sides.forEach {
            constraintSet.clear(this.id, it)
        }
        return this
    }

    fun <T : View> T.center(first: SideViewId, second: SideViewId, bias: Float = 0.5F): T {
        val horizontal = listOf(LEFT, RIGHT)
        val vertical = listOf(TOP, BOTTOM)
        val horizontalRtl = listOf(START, END)

        val firstViewId = first.viewId
        val secondViewId = second.viewId

        val firstSide = first.side
        val secondSide = second.side

        val firstMargin = (first as? SideViewIdMargin)?.margin ?: 0
        val secondMargin = (second as? SideViewIdMargin)?.margin ?: 0

        if (firstSide in horizontal && secondSide in horizontal) {
            constraintSet.centerHorizontally(
                this.id,
                firstViewId, firstSide, firstMargin,
                secondViewId, secondSide, secondMargin,
                bias
            )
        } else if (firstSide in vertical && secondSide in vertical) {
            constraintSet.centerVertically(
                this.id,
                firstViewId, firstSide, firstMargin,
                secondViewId, secondSide, secondMargin,
                bias
            )
        } else if (firstSide in horizontalRtl && secondSide in horizontalRtl) {
            constraintSet.centerHorizontallyRtl(
                this.id,
                firstViewId, firstSide, firstMargin,
                secondViewId, secondSide, secondMargin,
                bias
            )
        } else {
            throw IllegalArgumentException("Cannot center a view for supplied sides: $firstSide together with $secondSide")
        }
        return this
    }

    inline fun <T : View> T.width(width: Int): T {
        constraintSet.constrainWidth(this.id, width)
        return this
    }

    inline fun <T : View> T.maxWidth(width: Int): T {
        constraintSet.constrainMaxWidth(this.id, width)
        return this
    }

    inline fun <T : View> T.minWidth(width: Int): T {
        constraintSet.constrainMinWidth(this.id, width)
        return this
    }

    inline fun <T : View> T.percentWidth(percent: Number): T {
        constraintSet.constrainPercentWidth(this.id, percent.f)
        return this
    }

    inline fun <T : View> T.defaultWidth(width: DefaultSize): T {
        constraintSet.constrainDefaultWidth(this.id, width)
        return this
    }

    inline fun <T : View> T.height(height: Int): T {
        constraintSet.constrainHeight(this.id, height)
        return this
    }

    inline fun <T : View> T.maxHeight(height: Int): T {
        constraintSet.constrainMaxHeight(this.id, height)
        return this
    }

    inline fun <T : View> T.minHeight(height: Int): T {
        constraintSet.constrainMinHeight(this.id, height)
        return this
    }

    inline fun <T : View> T.percentHeight(percent: Number): T {
        constraintSet.constrainPercentHeight(this.id, percent.f)
        return this
    }

    inline fun <T : View> T.defaultHeight(height: DefaultSize): T {
        constraintSet.constrainDefaultHeight(this.id, height)
        return this
    }

    inline fun <T : View> T.circle(viewId: ViewId, radius: Int, angle: Float): T {
        constraintSet.constrainCircle(this.id, viewId, radius, angle)
        return this
    }

    inline fun <T : View> T.circle(view: View, radius: Int, angle: Float): T {
        constraintSet.constrainCircle(this.id, view.id, radius, angle)
        return this
    }

    inline fun <T : View> T.size(width: Int, height: Int): T {
        width(width)
        height(height)
        return this
    }

    inline fun <T : View> T.maxSize(width: Int, height: Int): T {
        maxWidth(width)
        maxHeight(height)
        return this
    }

    inline fun <T : View> T.minSize(width: Int, height: Int): T {
        minWidth(width)
        minHeight(height)
        return this
    }

    inline fun <T : View> T.percentSize(width: Float, height: Float): T {
        percentWidth(width)
        percentHeight(height)
        return this
    }

    inline fun <T : View> T.defaultSize(width: DefaultSize, height: DefaultSize): T {
        defaultWidth(width)
        defaultHeight(height)
        return this
    }

    inline fun <T : View> T.margin(anchor: Int, value: Int): T {
        constraintSet.setMargin(this.id, anchor, value)
        return this
    }

    inline fun <T : View> T.goneMargin(anchor: Int, value: Int): T {
        constraintSet.setGoneMargin(this.id, anchor, value)
        return this
    }

    inline fun <T : View> T.horizontalBias(bias: Number): T {
        constraintSet.setHorizontalBias(this.id, bias.f)
        return this
    }

    inline fun <T : View> T.verticalBias(bias: Number): T {
        constraintSet.setVerticalBias(this.id, bias.f)
        return this
    }

    inline fun <T : View> T.dimensionRatio(ratio: String): T {
        constraintSet.setDimensionRatio(this.id, ratio)
        return this
    }

    inline fun <T : View> T.visibility(visibility: Int): T {
        constraintSet.setVisibility(this.id, visibility)
        return this
    }

    inline fun <T : View> T.alpha(alpha: Number): T {
        constraintSet.setAlpha(this.id, alpha.f)
        return this
    }

    inline var <T : View> T.applyElevation: Boolean
        get() = constraintSet.getApplyElevation(this.id)
        set(value) = constraintSet.setApplyElevation(this.id, value)

    inline fun <T : View> T.applyElevation(): Boolean {
        return constraintSet.getApplyElevation(this.id)
    }

    inline fun <T : View> T.applyElevation(apply: Boolean): T {
        constraintSet.setApplyElevation(this.id, apply)
        return this
    }

    inline fun <T : View> T.elevation(elevation: Float): T {
        constraintSet.setElevation(this.id, elevation)
        return this
    }

    inline fun <T : View> T.rotationX(rotation: Float): T {
        constraintSet.setRotationX(this.id, rotation)
        return this
    }

    inline fun <T : View> T.rotationY(rotation: Float): T {
        constraintSet.setRotationY(this.id, rotation)
        return this
    }

    inline fun <T : View> T.rotation(rotation: Float): T {
        constraintSet.setRotation(this.id, rotation)
        return this
    }

    inline fun <T : View> T.scaleX(scale: Float): T {
        constraintSet.setScaleX(this.id, scale)
        return this
    }

    inline fun <T : View> T.scaleY(scale: Float): T {
        constraintSet.setScaleY(this.id, scale)
        return this
    }

    inline fun <T : View> T.scale(scale: Float): T {
        constraintSet.setScaleX(this.id, scale)
        constraintSet.setScaleY(this.id, scale)
        return this
    }

    inline fun <T : View> T.transformPivotX(x: Float): T {
        constraintSet.setTransformPivotX(this.id, x)
        return this
    }

    inline fun <T : View> T.transformPivotY(y: Float): T {
        constraintSet.setTransformPivotY(this.id, y)
        return this
    }

    inline fun <T : View> T.transformPivot(x: Float, y: Float): T {
        constraintSet.setTransformPivot(this.id, x, y)
        return this
    }

    inline fun <T : View> T.translationX(translationX: Float): T {
        constraintSet.setTranslationX(this.id, translationX)
        return this
    }

    inline fun <T : View> T.translationY(translationY: Float): T {
        constraintSet.setTranslationY(this.id, translationY)
        return this
    }

    inline fun <T : View> T.translationZ(translationZ: Float): T {
        constraintSet.setTranslationZ(this.id, translationZ)
        return this
    }

    inline fun <T : View> T.translation(translationX: Float, translationY: Float): T {
        constraintSet.setTranslation(this.id, translationX, translationY)
        return this
    }
//</editor-fold>

    //<editor-fold desc="<< connect() overloads >>">
    fun <T : View> T.connect(vararg connections: SideSideViewId): T {
        connections.forEach { connection ->

            val sides = connection.sides
            val endId = connection.viewId

            if (connection is SideSideViewIdMargin) {
                val margin = connection.margin
                when (sides) {
                    HORIZONTAL -> connectHorizontal(id, endId, margin)
                    VERTICAL -> connectVertical(id, endId, margin)
                    ALL -> connectAll(id, endId, margin)
                    else -> constraintSet.connect(id, sides.start, endId, sides.end, margin)
                }
            } else {
                when (sides) {
                    HORIZONTAL -> connectHorizontal(id, endId)
                    VERTICAL -> connectVertical(id, endId)
                    ALL -> connectAll(id, endId)
                    else -> constraintSet.connect(id, sides.start, endId, sides.end)
                }
            }

        }
        return this
    }

    private inline fun connectHorizontal(viewId: ViewId, targetId: ViewId) {
        with(constraintSet) {
            connect(viewId, START, targetId, START)
            connect(viewId, LEFT, targetId, LEFT)
            connect(viewId, END, targetId, END)
            connect(viewId, RIGHT, targetId, RIGHT)
        }
    }

    private inline fun connectHorizontal(viewId: ViewId, targetId: ViewId, margin: Int) {
        with(constraintSet) {
            connect(viewId, START, targetId, START, margin)
            connect(viewId, LEFT, targetId, LEFT, margin)
            connect(viewId, END, targetId, END, margin)
            connect(viewId, RIGHT, targetId, RIGHT, margin)
        }
    }

    private inline fun connectVertical(viewId: ViewId, targetId: ViewId) {
        with(constraintSet) {
            connect(viewId, TOP, targetId, TOP)
            connect(viewId, BOTTOM, targetId, BOTTOM)
        }
    }

    private inline fun connectVertical(viewId: ViewId, targetId: ViewId, margin: Int) {
        with(constraintSet) {
            connect(viewId, TOP, targetId, TOP, margin)
            connect(viewId, BOTTOM, targetId, BOTTOM, margin)
        }
    }

    private inline fun connectAll(viewId: ViewId, targetId: ViewId) {
        connectVertical(viewId, targetId)
        connectHorizontal(viewId, targetId)
    }

    private inline fun connectAll(viewId: ViewId, targetId: ViewId, margin: Int) {
        connectVertical(viewId, targetId, margin)
        connectHorizontal(viewId, targetId, margin)
    }


    private fun connect(startID: Int, startSide: Int, endID: Int, endSide: Int, margin: Int) {
        constraintSet.connect(startID, startSide, endID, endSide, margin)
    }
//</editor-fold>

    //<editor-fold desc="<< Helper classes and infix operators >>">
    open class SideSide(val start: Side, val end: Side)

    open class SideSideView(sides: SideSide, val view: View) : SideSideViewId(sides, view.id)
    open class SideSideViewId(val sides: SideSide, val viewId: ViewId)
    open class SideSideViewMargin(sides: SideSide, view: View, margin: Int) :
        SideSideViewIdMargin(sides, view.id, margin)

    open class SideSideViewIdMargin(sides: SideSide, viewId: ViewId, val margin: Int) : SideSideViewId(sides, viewId)
    open class SideView(side: Side, val view: View) : SideViewId(side, view.id)
    open class SideViewId(val side: Side, val viewId: ViewId)
    open class SideViewSide(val sideView: SideView, side: Side) : SideViewIdSide(sideView, side)
    open class SideViewIdSide(val sideViewId: SideViewId, val side: Side)
    open class SideViewSideView(from: SideView, val to: SideView) : SideViewSideViewId(from, to)
    open class SideViewSideViewId(val from: SideView, val toId: SideViewId)
    open class SideViewIdSideView(val from: SideViewId, val to: SideView) : SideViewIdSideViewId(from, to)
    open class SideViewIdSideViewId(val fromId: SideViewId, val toId: SideViewId)
    open class SideViewMargin(sideView: SideView, margin: Int) : SideViewIdMargin(sideView, margin)
    open class SideViewIdMargin(sideViewId: SideViewId, val margin: Int) :
        SideViewId(sideViewId.side, sideViewId.viewId)

    inline infix fun Side.to(side: Side) =
        SideSide(this, side)

    inline infix fun SideSide.of(view: View) =
        SideSideView(this, view)

    inline infix fun SideSide.of(viewId: ViewId) =
        SideSideViewId(this, viewId)

    inline infix fun SideSideView.with(margin: Int) =
        SideSideViewMargin(sides, view, margin)

    inline infix fun SideSideViewId.with(margin: Int) =
        SideSideViewIdMargin(sides, viewId, margin)

    inline infix fun Side.of(view: View) =
        SideView(this, view)

    inline infix fun Side.of(viewId: ViewId) =
        SideViewId(this, viewId)

    inline infix fun SideView.to(side: Side) =
        SideViewSide(this, side)

    inline infix fun SideViewId.to(side: Side) =
        SideViewIdSide(this, side)

    inline infix fun SideViewSide.of(view: View) =
        SideViewSideView(
            sideView,
            SideView(side, view)
        )

    inline infix fun SideViewSide.of(viewId: ViewId) =
        SideViewSideViewId(
            sideView,
            SideViewId(side, viewId)
        )

    inline infix fun SideViewIdSide.of(view: View) =
        SideViewIdSideView(
            sideViewId,
            SideView(side, view)
        )

    inline infix fun SideViewIdSide.of(viewId: ViewId) =
        SideViewIdSideViewId(
            sideViewId,
            SideViewId(side, viewId)
        )

    inline infix fun SideView.with(margin: Int) =
        SideViewMargin(this, margin)

    inline infix fun SideViewId.with(margin: Int) =
        SideViewIdMargin(this, margin)
//</editor-fold>

//// Iterable<View>
//// Iterable<View>
//// Iterable<View>

    fun <T : View> List<T>.alignParentStart(dp: Number = 0): List<T> {
        forEach {
            it.alignParentStart(dp)
        }
        return this
    }

    fun <T : View> List<T>.alignParentEnd(dp: Number = 0): List<T> {
        forEach {
            it.alignParentEnd(dp)
        }
        return this
    }

//// View
//// View
//// View

    fun <T : View> T.alignParentStart(px: Number = 0): T {
        connect(START to START of parentId with px.toInt())
        return this
    }

    fun <T : View> T.alignParentEnd(px: Number = 0): T {
        connect(END to END of parentId with px.toInt())
        return this
    }

    infix fun <T : View> T.topStartOf(other: View): T {
        connect(TOPS of other, STARTS of other)
        return this
    }

    infix fun <T : View> T.topStartOf(other: ViewId): T {
        connect(TOPS of other, STARTS of other)
        return this
    }

    infix fun <T : View> T.centerIn(other: View): T {
        connect(ALL of other)
        return this
    }

    infix fun <T : View> T.centerIn(other: ViewId): T {
        connect(ALL of other)
        return this
    }

    infix fun <T : View> T.centerVerticallyIn(other: View): T {
        connect(TOPS of other, BOTTOMS of other)
        return this
    }

    infix fun <T : View> T.centerVerticallyIn(other: ViewId): T {
        connect(TOPS of other, BOTTOMS of other)
        return this
    }

    infix fun <T : View> T.centerHorizontallyIn(other: View): T {
        connect(STARTS of other, ENDS of other)
        return this
    }

    infix fun <T : View> T.centerHorizontallyIn(other: ViewId): T {
        connect(STARTS of other, ENDS of other)
        return this
    }

    fun <T : View> T.matchConstraints(): T {
        width(matchConstraintSpread).height(matchConstraintSpread)
        return this
    }


    fun <T : View> List<T>.buildChain(inside: View, block: ChainBuilder.() -> Unit): List<T> =
        buildChain(inside.id, block)

    fun <T : View> List<T>.buildChain(inside: ViewId = parentId, block: ChainBuilder.() -> Unit): List<T> {
        buildChain(inside, this, block)
        return this
    }

}


