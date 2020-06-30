package com.benoitthore.visualentity.android

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.line.ELine
import com.benoitthore.enamel.geometry.figures.line.ELine
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransformMutable
import com.benoitthore.enamel.layout.android.draw
import com.benoitthore.visualentity.*
import com.benoitthore.visualentity.android.utils.VisualEntityDrawer
import com.benoitthore.visualentity.style.EStyleable

fun LineVisualEntity.toAndroid(): LineVisualEntityAndroid =
    toMutable().toAndroid()

fun LineVisualEntityMutable.toAndroid(): LineVisualEntityMutableAndroid {
    return LineVisualEntityMutableAndroidImpl(
        this,
        VisualEntityDrawer(style) { canvas, paint ->
            canvas.draw(
                this,
                paint
            )
        })
}

interface LineVisualEntityAndroid : AndroidVisualEntity<ELine, ELine>,
    LineVisualEntity {
    override fun toMutable(): LineVisualEntityMutableAndroid
    override fun toImmutable(): LineVisualEntityAndroid
}

interface LineVisualEntityMutableAndroid : LineVisualEntityAndroid,
    LineVisualEntityMutable,
    AndroidVisualEntityMutable<ELine, ELine>,
    ELine


internal class LineVisualEntityMutableAndroidImpl(
    private val line: ELine,
    override val drawer: VisualEntityDrawer
) :
    LineVisualEntityMutableAndroid,
    ELine by line,
    EStyleable by drawer {

    override val transform: ETransformMutable = E.TransformMutable()

    override fun toMutable(): LineVisualEntityMutableAndroid =
        line.toMutable().toVisualEntity(style).toAndroid()

    override fun toImmutable(): LineVisualEntityAndroid = toMutable()
}