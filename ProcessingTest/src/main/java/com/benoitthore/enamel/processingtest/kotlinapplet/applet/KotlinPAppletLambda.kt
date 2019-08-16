package com.benoitthore.enamel.processingtest.kotlinapplet.applet

internal typealias KotlinPAppletEventListener = KotlinPAppletLambda.() -> Unit
internal typealias OnDrawListener = KotlinPAppletLambda.() -> Unit

open class KotlinPAppletLambda : KotlinPApplet() {


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
        onPreDrawListeners.forEach { it.invoke(this) }
        onDrawListeners.forEach { it.invoke(this) }
        onPostDrawListeners.forEach { it.invoke(this) }
    }

}

