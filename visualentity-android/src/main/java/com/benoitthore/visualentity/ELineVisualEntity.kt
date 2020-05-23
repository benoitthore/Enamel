package com.benoitthore.visualentity

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.line.ELine
import com.benoitthore.enamel.geometry.figures.line.ELineMutable
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.figures.rect.ERectMutable
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransformMutable
import com.benoitthore.enamel.geometry.toMutable
import com.benoitthore.enamel.layout.android.draw
import com.benoitthore.visualentity.style.EStyle
import com.benoitthore.visualentity.style.EStyleable


fun ELine.toVisualEntity(style: EStyle = EStyle()) = ELineVisualEntity(toMutable()).apply {
    this.style = style
}

class ELineVisualEntity internal constructor(
    val shape: ELineMutable,
    override val drawer: VisualEntityDrawer = VisualEntityDrawer { canvas, paint ->
        canvas.draw(shape, paint)
    }
) :
    AndroidVisualEntity,
    EStyleable by drawer,
    ELineMutable by shape {
    override val transform: ETransformMutable = E.TransformMutable()
}

