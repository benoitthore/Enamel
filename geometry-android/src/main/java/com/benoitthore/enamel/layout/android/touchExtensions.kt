package com.benoitthore.enamel.layout.android

import android.view.MotionEvent.*
import android.view.MotionEvent
import android.view.View
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable
import kotlin.math.min

fun EPointMutable.set(event: MotionEvent) = apply { set(event.x, event.y) }

typealias ETouchListener = (ETouchEvent) -> Boolean

fun View.singleTouch(block: (ETouchEvent) -> Boolean): Unit = multiTouch { block(it.first()) }

sealed class ETouchEvent {
    val position: EPointMutable = E.PointMutable()

    open fun set(x: Number, y: Number, id: Int) {
        position.set(x, y)
    }

    class Down : ETouchEvent()

    class Move(val previous: EPointMutable = E.PointMutable()) : ETouchEvent() {
        override fun set(x: Number, y: Number, id: Int) {
            previous.set(position)
            super.set(x, y, id)
        }
    }

    class Up : ETouchEvent()
}


class ETouchInstanceMutable {

    private val up = ETouchEvent.Up()
    private val down = ETouchEvent.Down()
    private val move = ETouchEvent.Move()

    var event: ETouchEvent? = null

    fun setUp(x: Float, y: Float, id: Int) {
        up.set(x, y, id)
        event = up
    }

    fun setDown(x: Float, y: Float, id: Int) {
        move.set(x, y, id)
        down.set(x, y, id)
        event = down
    }

    fun setMove(x: Float, y: Float, id: Int) {
        move.set(x, y, id)
        event = move
    }

    fun reset() {
        event = null
    }
}

/**
 * The iterable, its iterator as well as the ETouchEvent instances shouldn't be shared,
 * as they get updated on every touch event
 */
fun View.multiTouch(maxFingers: Int = 10, onTouch: (Iterable<ETouchEvent>) -> Boolean) {

    val list = List(maxFingers) { ETouchInstanceMutable() }

    // Iterator being re-used across touch events
    val iterator = object : Iterator<ETouchEvent>, Iterable<ETouchEvent> {

        private var nextIndex = 0

        override fun hasNext(): Boolean = nextIndex < list.size && list[nextIndex].event != null

        override fun next(): ETouchEvent = list[nextIndex++].event!!

        fun reset() {
            nextIndex = 0
        }

        override fun iterator(): Iterator<ETouchEvent> = this
    }


    setOnTouchListener { v, e ->

        val pointerCount = min(e.pointerCount, list.size)

        list.forEachIndexed { i, touchInstance ->
            touchInstance.reset()
            if (i < pointerCount) {
                touchInstance.set(e, i)
            }
        }

        iterator.reset()
        onTouch(iterator)
    }
}

private fun ETouchInstanceMutable.set(e: MotionEvent, pointerIndex: Int = 0) {
    val x = e.getX(pointerIndex)
    val y = e.getY(pointerIndex)
    val id = e.getPointerId(pointerIndex)

    when (e.actionMasked) {
        ACTION_DOWN, ACTION_POINTER_DOWN -> setDown(x, y, id)
        ACTION_MOVE -> setMove(x, y, id)
        else -> setUp(x, y, id)
    }

}