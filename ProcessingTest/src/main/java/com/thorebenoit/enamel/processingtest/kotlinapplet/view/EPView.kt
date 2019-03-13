package com.thorebenoit.enamel.processingtest.kotlinapplet.view

import com.thorebenoit.enamel.kotlin.core.backingfield.ExtraValueHolder
import com.thorebenoit.enamel.kotlin.geometry.figures.ERect
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.ELayoutRef
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.ELayoutRefObject
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPAppletLambda


val <T : EPView> T.layoutRef: ELayoutRef<T> by ExtraValueHolder {
    ELayoutRef(
        ELayoutRefObject(this, { isAdded = true }, { isAdded = false }),
        { size -> size },
        { frame -> drawingRect.set(frame) },
        emptyList()
    )
}


abstract class EPView(val applet: KotlinPAppletLambda) {
    abstract var isAdded: Boolean
    open var drawingRect: ERect = ERect()
    fun draw() {
        if (!isAdded) {
            return
        }
        onDraw()
    }

    abstract fun onDraw()
}