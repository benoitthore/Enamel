package com.benoitthore.enamel.visualentity.android

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.line.ELine
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform
import com.benoitthore.enamel.layout.android.draw
import com.benoitthore.visualentity.*
import com.benoitthore.enamel.visualentity.android.utils.VisualEntityDrawer
import com.benoitthore.visualentity.style.EStyleable


fun LineVisualEntity.toAndroid(): LineVisualEntityAndroid {
    return LineVisualEntityAndroidImpl(
        copy(),
        VisualEntityDrawer(
            style
        ) { canvas, paint ->
            canvas.draw(
                this,
                paint
            )
        })
}

interface LineVisualEntityAndroid : AndroidVisualEntity<ELine>,
    LineVisualEntity {
    override fun copy(): LineVisualEntityAndroid
}


internal class LineVisualEntityAndroidImpl(
    private val line: ELine,
    override val drawer: VisualEntityDrawer
) :
    LineVisualEntityAndroid,
    ELine by line,
    EStyleable by drawer {

    override val transform: ETransform = E.Transform()
    override fun copy(): LineVisualEntityAndroid = line.copy().toVisualEntity(style).toAndroid()
}