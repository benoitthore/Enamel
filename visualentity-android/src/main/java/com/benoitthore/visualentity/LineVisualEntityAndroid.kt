package com.benoitthore.visualentity

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.line.ELine
import com.benoitthore.enamel.geometry.figures.line.ELineMutable
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.figures.rect.ERectMutable
import com.benoitthore.enamel.geometry.interfaces.bounds.CanSetBounds
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransformMutable
import com.benoitthore.enamel.layout.android.draw
import com.benoitthore.visualentity.style.EStyle
import com.benoitthore.visualentity.style.EStyleable

fun LineVisualEntity.toAndroid(): LineVisualEntityAndroid =
    toMutable().toAndroid()

fun LineVisualEntityMutable.toAndroid(): LineVisualEntityMutableAndroid {
    return LineVisualEntityMutableAndroidImpl(this, VisualEntityDrawer { canvas, paint ->
        canvas.draw(
            this,
            paint
        )
    })
}

interface LineVisualEntityAndroid : AndroidVisualEntity<ELine, ELineMutable>,
    LineVisualEntity{
    override fun toMutable(): LineVisualEntityMutableAndroid
    override fun toImmutable(): LineVisualEntityAndroid
}

interface LineVisualEntityMutableAndroid : LineVisualEntityAndroid, LineVisualEntityMutable,
    AndroidVisualEntityMutable<ELine, ELineMutable>, ELineMutable


internal class LineVisualEntityMutableAndroidImpl(
    private val line: ELineMutable,
    override val drawer: VisualEntityDrawer
) :
    LineVisualEntityMutableAndroid,
    ELineMutable by line,
    EStyleable by drawer {

    override val transform: ETransformMutable = E.TransformMutable()

    override fun toMutable(): LineVisualEntityMutableAndroid =
        line.toMutable().toVisualEntity(style).toAndroid()

    override fun toImmutable(): LineVisualEntityAndroid = toMutable()
}