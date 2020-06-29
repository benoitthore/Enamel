package com.benoitthore.enamel.geometry.figures.rectgroup

import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.figures.rect.ERectMutable
import com.benoitthore.enamel.geometry.figures.rect.union
import com.benoitthore.enamel.geometry.interfaces.bounds.*

interface ERectGroup : HasBounds<ERectGroup, ERectGroupMutable> {
    val frame: ERect
    val rects: List<ERect>
}

interface ERectGroupMutable : ERectGroup, CanSetBounds<ERectGroup, ERectGroupMutable> {

    override val rects: List<ERect>

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

    class ERectGroupImpl(override val rects: List<ERectMutable>) : ERectGroupMutable {

        private val _frame = E.RectMutable()

        override val frame: ERect
            get() = _frame

        init {
            updateFrame()
        }

        override fun toMutable(): ERectGroupMutable = rects.toMutableList().toRectGroup()

        override fun toImmutable(): ERectGroup = toMutable()

        override fun updateFrame() {
            rects.union(target = _frame)
        }

        override val left: Float
            get() = frame.left
        override val top: Float
            get() = frame.top
        override val right: Float
            get() = frame.right
        override val bottom: Float
            get() = frame.bottom
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
                rect.selfMap(
                    fromX = fromX,
                    fromY = fromY,
                    fromWidth = fromWidth,
                    fromHeight = fromHeight,
                    toX = toX,
                    toY = toY,
                    toWidth = toWidth,
                    toHeight = toHeight
                )
            }
            updateFrame()
        }
    }


}

