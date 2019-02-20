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
internal typealias OnDrawListener = KotlinPAppletLambda.() -> Unit

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

