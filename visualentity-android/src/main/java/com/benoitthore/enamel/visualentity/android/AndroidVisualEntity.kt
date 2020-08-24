package com.benoitthore.enamel.visualentity.android

import android.graphics.Canvas
import com.benoitthore.enamel.geometry.functions.EShape
import com.benoitthore.enamel.layout.android.withSave
import com.benoitthore.enamel.layout.android.withTransform
import com.benoitthore.visualentity.VisualEntity
import com.benoitthore.enamel.visualentity.android.utils.VisualEntityDrawer

interface AndroidVisualEntity<T : EShape<T>> : VisualEntity<T> {
    val drawer: VisualEntityDrawer
}


inline fun Canvas.drawFromCenter(crossinline block: Canvas.() -> Unit) {
    withSave {
        translate(width / 2f, height / 2f)
        block()
    }
}

fun Canvas.draw(visualEntity: AndroidVisualEntity<*>) {
    val canvas = this
    withTransform(visualEntity.transform) {
        with(visualEntity.drawer) {
            if (style.shadow != null) {
                draw(canvas, shadowPaint)
            }
            if (style.fill != null) {
                draw(canvas, fillPaint)
            }
            if (style.border != null) {
                draw(canvas, borderPaint)
            }
        }
    }
}

