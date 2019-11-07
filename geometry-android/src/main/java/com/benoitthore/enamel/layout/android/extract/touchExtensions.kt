package com.benoitthore.enamel.layout.android.extract

import android.view.MotionEvent
import android.view.View
import com.benoitthore.enamel.geometry.primitives.EPoint
import com.benoitthore.enamel.geometry.primitives.EPointMutable


fun EPointMutable.set(event: MotionEvent) = apply { set(event.x, event.y) }

/**
 * Allows to work with EPoint when dealing with touch events
 * isDown: Boolean -> true as long as the user is touching the screen
 * current: EPoint? -> current touch location, null when isDown is false
 * previous: EPoint? -> Location of the previous touch, null if isDown has just been set to true
 */
fun View.singleTouch(block: (isDown: Boolean, current: EPoint?, previous: EPoint?) -> Boolean) {
    val previous = EPointMutable()
    val current = EPointMutable()

    setOnTouchListener { _, e ->

        previous.set(current)
        current.set(e)
        return@setOnTouchListener when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                block(true, current, null)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                block(false, null, current)
            }
            MotionEvent.ACTION_MOVE -> {
                block(true, current, previous)
            }
            else -> false
        }


    }
}

/**
 * Like a singleTouch with x and y normalized over the View's size
 * (0,0) being top left and (1,1) bottom right
 */
fun View.normalizedTouch(block: (x: Float, y: Float) -> Boolean) {
    singleTouch { isDown, current, previous ->
        if (current == null) {
            return@singleTouch false
        }
        val x = current.x / width
        val y = current.y / height
        return@singleTouch block(x, y)
    }
}


data class ETouchEvent(
    val position: EPointMutable = EPointMutable(),
    var isDown: Boolean = false,
    var id: Int = -1
) {
    fun reset() {
        position.reset()
        isDown = false
        id = -1
    }

    fun set(other: ETouchEvent) {
        position.set(other.position)
        isDown = other.isDown
        id = other.id
    }
}

fun View.mutliTouch(maxFingers: Int = 10, onTouch: (List<ETouchEvent>) -> Boolean) {

    val touch = EPointMutable()

    val list = List(maxFingers) { ETouchEvent(EPointMutable(), false, -1) }

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
                else -> throw Exception("Unknown MotionEvent action $action")
            }
            list[i].let { eTouchEvent ->
                eTouchEvent.id = id
                eTouchEvent.position.set(x, y)
                eTouchEvent.isDown = isDown

                Unit
            }

        }


        onTouch(list)
    }
}
