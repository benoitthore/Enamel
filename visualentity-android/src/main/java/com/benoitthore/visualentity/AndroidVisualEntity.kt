package com.benoitthore.visualentity

import android.graphics.Canvas
import com.benoitthore.enamel.layout.android.withTransform

interface AndroidVisualEntity : VisualEntity {
    val drawer: VisualEntityDrawer
}


fun Canvas.draw(visualEntity: AndroidVisualEntity) {
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
