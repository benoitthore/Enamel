package com.benoitthore.enamel.visualentity.android

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.line.ELine
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform
import com.benoitthore.enamel.layout.android.draw
import com.benoitthore.visualentity.*
import com.benoitthore.enamel.visualentity.android.utils.VisualEntityDrawer
import com.benoitthore.visualentity.style.EStyleable


fun ELineVisualEntity.toAndroid(): ELineVisualEntityAndroid {
    val copy = copy()
    return ELineVisualEntityAndroidImpl(
        copy,
        VisualEntityDrawer(
            style
        ) { canvas, paint ->
            canvas.draw(
                copy,
                paint
            )
        })
}

interface ELineVisualEntityAndroid : AndroidVisualEntity<ELine>,
    ELineVisualEntity {
    override fun _copy(): ELineVisualEntityAndroid
}


internal class ELineVisualEntityAndroidImpl(
    private val line: ELine,
    override val drawer: VisualEntityDrawer
) :
    ELineVisualEntityAndroid,
    ELine by line,
    EStyleable by drawer {

    override val transform: ETransform = E.Transform()
    override fun _copy(): ELineVisualEntityAndroid = line.copy().toVisualEntity(style).toAndroid()
}