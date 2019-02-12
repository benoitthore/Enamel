package com.thorebenoit.enamel.processingtest.kotlinapplet.applet

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

}

