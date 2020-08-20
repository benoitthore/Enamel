package com.benoitthore.enamel.geometry.figures.rectgroup

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.interfaces.bounds.*
import com.benoitthore.enamel.geometry.svg.SVGContext
import java.lang.IllegalArgumentException

// TODO Rename to EShapeGroup
interface ERectGroup<T : EShape<*>> : EShape<ERectGroup<T>> {
    val frame: ERect
    val rects: List<T>


    override var originX: Float
        get() = frame.originX
        set(value) {
            frame.originX = value
        }

    override var originY: Float
        get() = frame.originY
        set(value) {
            frame.originY = value
        }

    override fun addTo(context: SVGContext) {
        rects.forEach { it.addTo(context) }
    }


    /**
     * Needs be called by the user whenever a change happens in one of the children,
     * before calling any other function as they will use the previous state
     *
     * NOTE: Ideally, the ERectGroupMutable could:
     * - listen for changes in its children
     * - set a flag when one changes
     * - call updateFrame() and set the flag to false whenever a get/set is called on one of the
     *      primary fields: left top right bottom
     *
     */
    fun updateFrame()
    fun aligned(anchor: EPoint, position: EPoint) {
        TODO()
    }

    class ERectGroupImpl<T : EShape<*>>(override val rects: List<T>) : ERectGroup<T> {

        private val _frame = E.Rect()

        override fun copy(): ERectGroup<T> {
            TODO("Not yet implemented")
        }

        override val frame: ERect
            get() = _frame

        init {
            updateFrame()
        }


        override fun updateFrame() {
            rects.union(target = _frame)
        }

        override var left: Float
            get() = frame.left
            set(value) {
                setBounds(left = value)
            }
        override var top: Float
            get() = frame.top
            set(value) {
                setBounds(top = value)
            }
        override var right: Float
            get() = frame.right
            set(value) {
                setBounds(right = value)
            }
        override var bottom: Float
            get() = frame.bottom
            set(value) {
                setBounds(bottom = value)
            }
        override var centerX: Float
            get() = frame.centerX
            set(value) {
                val shift = centerX - value
                setBounds(
                    left = left - shift,
                    right = right - shift,
                    top = top,
                    bottom = bottom
                )
            }
        override var centerY: Float
            get() = frame.centerY
            set(value) {
                val shift = centerX - value
                setBounds(
                    top = top - shift,
                    bottom = bottom - shift,
                    left = left,
                    right = right
                )
            }


        override var width: Float
            get() = frame.width
            set(value) {
                val shift = width - value
                setBounds(
                    left = left + shift,
                    right = right - shift,
                    top = top,
                    bottom = bottom
                )
            }
        override var height: Float
            get() = frame.height
            set(value) {
                val shift = height - value
                setBounds(
                    top = top + shift,
                    bottom = bottom - shift,
                    left = left,
                    right = right
                )
            }

        override fun setBounds(left: Number, top: Number, right: Number, bottom: Number) {
            val fromX = frame.originX
            val fromY = frame.originY
            val fromWidth = frame.width
            val fromHeight = frame.height

            _frame.setSides(left, top, right, bottom)

            val toX = frame.originX
            val toY = frame.originY
            val toWidth = frame.width
            val toHeight = frame.height

            rects.forEach { rect ->
                val anchorLeft =
                    if (fromWidth == 0f) .5f else (rect.originX - fromX.f) / fromWidth.f
                val anchorTop =
                    if (fromHeight == 0f) .5f else (rect.originY - fromY.f) / fromHeight.f
                val anchorRight =
                    if (fromWidth == 0f) .5f else (rect.originX - fromX.f + rect.width) / fromWidth.f
                val anchorBottom =
                    if (fromHeight == 0f) .5f else (rect.originY - fromY.f + rect.height) / fromHeight.f

                val left = toX.f + toWidth.f * anchorLeft.f
                val top = toY.f + toHeight.f * anchorTop.f

                val right = toX.f + toWidth.f * anchorRight.f
                val bottom = toY.f + toHeight.f * anchorBottom.f

                rect.setSides(
                    left = left,
                    top = top,
                    bottom = bottom,
                    right = right
                )
            }
            updateFrame()
        }

        override fun set(other: ERectGroup<T>): ERectGroup<T> {
            if (other.rects.size != this.rects.size) {
                throw IllegalArgumentException("${other.rects.size} shapes given, ${rects.size} expected")
            }
            other.rects.forEachIndexed { i, otherRect ->
                rects[i].setBounds(otherRect)
            }
            return this
        }

    }


}

