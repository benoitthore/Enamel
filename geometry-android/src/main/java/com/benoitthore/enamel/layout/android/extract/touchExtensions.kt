package com.benoitthore.enamel.layout.android.extract

import android.view.MotionEvent
import android.view.View
import com.benoitthore.enamel.geometry.e.E
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.primitives.EPoint
import com.benoitthore.enamel.geometry.primitives.EPointMutable

fun EPointMutable.set(event: MotionEvent) = apply { set(event.x, event.y) }

typealias ETouchListener = (isDown: Boolean, current: EPoint?, previous: EPoint?) -> Boolean

class OnClickTouchListener(
    private val getFrame: () -> ERect,
    private val callback: () -> Unit
) : ETouchListener {

    private var down = false
    private var isTouched = false
    override fun invoke(isDown: Boolean, current: EPoint?, previous: EPoint?): Boolean {
        if (current == null) { // just up
            if (!down) {
                return false
            }

            if (isTouched) {
                down = false
                isTouched = false
                callback()
                return true
            }
            return false
        }

        if (!down && isDown) { // just down
            down = true
        }
        return getFrame().contains(current).also { isTouched = it }
    }
}

class SingleTouchDelegate(val block: ETouchListener) : View.OnTouchListener {

    val previous = E.mpoint()
    val current = E.mpoint()

    override fun onTouch(v: View?, e: MotionEvent): Boolean {
        previous.set(current)
        current.set(e)

        return when (e.action) {
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
 * Allows to work with EPoint when dealing with touch events
 * isDown: Boolean -> true as long as the user is touching the screen
 * current: EPoint? -> current touch location, null when isDown is false
 * previous: EPoint? -> Location of the previous touch, null if isDown has just been set to true
 */
fun View.singleTouch(block: (isDown: Boolean, current: EPoint?, previous: EPoint?) -> Boolean) =
    setOnTouchListener(SingleTouchDelegate(block))

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
    val position: EPointMutable = E.mpoint(),
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

    val touch = E.mpoint()

    val list = List(maxFingers) { ETouchEvent(E.mpoint(), false, -1) }

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
            }

        }


        onTouch(list)
    }
}
