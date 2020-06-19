package com.benoitthore.visualentity.android

import android.graphics.Canvas
import com.benoitthore.enamel.geometry.interfaces.bounds.CanSetBounds
import com.benoitthore.enamel.geometry.interfaces.bounds.HasBounds
import com.benoitthore.enamel.layout.android.withTransform
import com.benoitthore.visualentity.VisualEntity
import com.benoitthore.visualentity.VisualEntityMutable
import com.benoitthore.visualentity.android.utils.VisualEntityDrawer

interface AndroidVisualEntity<I, M> :
    VisualEntity<I, M> where I : HasBounds<I, M>, M : CanSetBounds<I, M> {
    val drawer: VisualEntityDrawer
}

interface AndroidVisualEntityMutable<I, M> : AndroidVisualEntity<I, M>,
    VisualEntityMutable<I, M> where I : HasBounds<I, M>, M : CanSetBounds<I, M>


fun Canvas.draw(visualEntity: AndroidVisualEntity<*, *>) {
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
