package com.thorebenoit.enamel.processingtest.kotlinapplet.applet

import com.thorebenoit.enamel.kotlin.core.math.f
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.allocate
import com.thorebenoit.enamel.kotlin.geometry.figures.ECircleType
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType
import com.thorebenoit.enamel.kotlin.geometry.toCircle
import com.thorebenoit.enamel.processingtest.kotlinapplet.esize
import com.thorebenoit.enamel.processingtest.kotlinapplet.modules.jframe
import processing.core.PConstants
import processing.core.PGraphics
import java.lang.NullPointerException

internal typealias KotlinPAppletEventListener = KotlinPAppletLambda.() -> Unit
internal typealias OnDrawListener = PGraphics.() -> Unit

abstract class KotlinPAppletLambda : KotlinPApplet() {


    override fun settings() {
        super.settings()
        onSettingsListeners.forEach { it() }
    }

    override fun setup() {
        super.setup()
        onSetupListeners.forEach { it() }
    }


    private val onSetupListeners = mutableListOf<KotlinPAppletEventListener>()
    private val onSettingsListeners = mutableListOf<KotlinPAppletEventListener>()

    fun onSetup(block: KotlinPAppletEventListener) {
        onSetupListeners += block
    }

    fun onSettings(block: KotlinPAppletEventListener) {
        onSettingsListeners += block
    }


    // Draw override
    var _graphics: PGraphics = PGraphics()
        get() {
            if (field.esize != esize) {
                field = createGraphics(width, height)
                    .apply { init(width, height, PConstants.ARGB) }
            }
            return field
        }

    private val onPreDrawListeners = mutableListOf<OnDrawListener>()
    private val onDrawListeners = mutableListOf<OnDrawListener>()
    private val onPostDrawListeners = mutableListOf<OnDrawListener>()


    fun onPreDraw(block: OnDrawListener) {
        onPreDrawListeners += block
    }


    fun onDraw(block: OnDrawListener) {
        onDrawListeners += block
    }

    fun onPostDraw(block: OnDrawListener) {
        onPostDrawListeners += block
    }


    override fun draw() {
        _graphics.beginDraw()
        onPreDrawListeners.forEach { it.invoke(_graphics) }
        onDrawListeners.forEach { it.invoke(_graphics) }
        _graphics.endDraw()
        
        image(_graphics, 0f, 0f)
        
        onPostDrawListeners.forEach { it.invoke(_graphics) }
    }

    override fun <T : EPointType> T.draw(radius: Number): T {
        allocate { toCircle(radius).draw() }
        return this
    }


    override fun <T : ECircleType> T.draw(): T {
        _graphics.ellipse(x, y, radius * 2, radius * 2)
        return this
    }

    override fun <T : ERectType> T.draw(): T {
        _graphics.rect(x, y, width, height)
        return this
    }

    override fun <E : EPointType> List<E>.draw(closed: Boolean): List<E> {
        with(_graphics) {
            beginShape()

            forEach {
                vertex(it.x, it.y)
            }
            if (closed) {
                endShape(PConstants.CLOSE)
            } else {
                endShape(PConstants.OPEN)
            }
        }

        return this
    }


}

