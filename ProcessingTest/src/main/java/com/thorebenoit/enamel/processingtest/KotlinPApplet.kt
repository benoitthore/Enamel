package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.geometry.figures.*
import com.thorebenoit.enamel.kotlin.i
import com.thorebenoit.enamel.kotlin.print
import com.thorebenoit.enamel.kotlin.threading.CoroutineLock
import processing.core.PApplet
import processing.core.PConstants
import processing.event.KeyEvent


abstract class KotlinPApplet : PApplet() {

//    private val keyLock = CoroutineLock()
//    private var unlockKey : Char? = 'U'
//    override fun keyPressed(event: KeyEvent) {
//        if(event.key == unlockKey){
//            keyLock.unlock()
//            unlockKey = null
//        }
//    }


    val eframe get() = ERect(size = esize)
    var esize : ESize
        get() = width size height
        set(value) {
            size(value.width.i, value.height.i)
        }

    fun ECircle.draw(): ECircle {
        ellipse(x, y, radius, radius)
        return this
    }

    fun ERect.draw(): ERect {
        rect(x, y, width, height)
        return this
    }

    override fun settings() {
        size(400, 400)
    }
}