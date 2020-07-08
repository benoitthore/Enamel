package com.benoitthore.visualentity.android

import android.graphics.Canvas
import com.benoitthore.enamel.geometry.interfaces.bounds.EShape
import com.benoitthore.enamel.layout.android.withTransform
import com.benoitthore.visualentity.VisualEntity
import com.benoitthore.visualentity.android.utils.VisualEntityDrawer

interface AndroidVisualEntity<T : EShape<T>> : VisualEntity<T>  {
    val drawer: VisualEntityDrawer
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
