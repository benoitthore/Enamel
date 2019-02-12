package com.thorebenoit.enamel.processingtest.kotlinapplet.applet

import com.thorebenoit.enamel.kotlin.geometry.allocate
import com.thorebenoit.enamel.kotlin.geometry.figures.ECircleImmutable
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType
import com.thorebenoit.enamel.kotlin.geometry.toCircle
import com.thorebenoit.enamel.processingtest.kotlinapplet.modules.jframe
import processing.core.PConstants
import processing.core.PGraphics

private typealias KotlinPAppletEventListener = KotlinPAppletModule.() -> Unit
private typealias OnDrawListener = PGraphics.() -> Unit

abstract class KotlinPAppletModule : KotlinPApplet() {


    override fun settings() {
        super.settings()
        size(800, 800)
        onSettingsListeners.forEach { it() }
    }

    override fun setup() {
        super.setup()
        jframe // init jframe
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
    var _graphics: PGraphics? = null

    override fun getGraphics(): PGraphics {
        if (_graphics?.esize != esize) {
            _graphics = createGraphics(width, height)
                .apply { init(width, height, PConstants.ARGB) }
        }
        return _graphics!!
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
        graphics.beginDraw()
        onPreDrawListeners.forEach { it.invoke(graphics) }
        onDrawListeners.forEach { it.invoke(graphics) }
        graphics.endDraw()
        onPostDrawListeners.forEach { it.invoke(graphics) }
    }

    override fun <T : EPointType> T.draw(): T {
        allocate { toCircle(5).draw() }
        return this
    }


    override fun <T : ECircleImmutable> T.draw(): T {
        graphics.ellipse(x, y, radius * 2, radius * 2)
        return this
    }

    override fun <T : ERectType> T.draw(): T {
        graphics.rect(x, y, width, height)
        return this
    }

    override fun <E : EPointType> List<E>.draw(closed: Boolean): List<E> {
        with(graphics) {
            beginShape()

            forEach {
                vertex(it.x, it.y)
            }
            if (closed) {
                endShape(PConstants.CLOSE)
            } else {
                endShape()
            }
        }

        return this
    }

}

