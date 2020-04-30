package com.benoitthore.enamel.layout.android.extract

import android.view.MotionEvent
import android.view.View
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.primitives.EPoint
import com.benoitthore.enamel.geometry.primitives.EPointMutable

fun EPointMutable.set(event: MotionEvent) = apply { set(event.x, event.y) }

typealias ETouchListener = (ETouchEvent) -> Boolean

sealed class ETouchEvent(val position: EPointMutable) {
    class Up(position: EPointMutable) : ETouchEvent(position)
    class Move(position: EPointMutable, val previous: EPointMutable) :
        ETouchEvent(position)

    class Down(position: EPointMutable) : ETouchEvent(position)
}

class SingleTouchDelegate(val block: ETouchListener) : View.OnTouchListener {

    private val previous = E.PointMutable()
    private val current = E.PointMutable()
    private val up = ETouchEvent.Up(current)
    private val move = ETouchEvent.Move(position = current, previous = previous)
    private val down = ETouchEvent.Down(current)

    override fun onTouch(v: View?, e: MotionEvent): Boolean {
        previous.set(current)
        current.set(e)

        return when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                block(move)
            }
            MotionEvent.ACTION_MOVE -> {
                block(down)
            }
            else -> {
                block(up)
            }
        }
    }
}

/**
 * Allows to work with EPointMutable when dealing with touch events
 */
fun View.singleTouch(block: (ETouchEvent) -> Boolean) =
    setOnTouchListener(SingleTouchDelegate(block))




data class ETouchInstance(
    val position: EPointMutable = E.PointMutable(),
    var isDown: Boolean = false,
    var id: Int = -1
) {
    fun reset() {
        position.reset()
        isDown = false
        id = -1
    }

    fun set(other: ETouchInstance) {
        position.set(other.position)
        isDown = other.isDown
        id = other.id
    }
}

fun View.mutliTouch(maxFingers: Int = 10, onTouch: (List<ETouchInstance>) -> Boolean) {

    val touch = E.PointMutable()

    val list = List(maxFingers) { ETouchInstance(E.PointMutable(), false, -1) }

    setOnTouchListener { v, e ->
        touch.set(e)

        val pointerCount = kotlin.math.min(e.pointerCount, list.size)

        list.forEach { it.reset() }
        for (i in 0 until pointerCount) {
            val x = e.getX(i)
            val y = e.getY(i)
            val id = e.getPointerId(i)

            val isDown = when (val action = e.actionMasked) {
                MotionEvent.ACTION_DOWN -> true
                MotionEvent.ACTION_UP -> false
                MotionEvent.ACTION_POINTER_DOWN -> true
                MotionEvent.ACTION_POINTER_UP -> false
                MotionEvent.ACTION_MOVE -> true
                else -> false
            }
            list[i].let { eTouchEvent ->
                eTouchEvent.id = id
                eTouchEvent.position.set(x, y)
                eTouchEvent.isDown = isDown
            }

        }


        onTouch(list)
    }
}
