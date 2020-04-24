package com.benoitthore.enamel.layout.android.visualentity

import android.graphics.Canvas
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.geometry.figures.ESize
import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.geometry.primitives.ETransformation
import com.benoitthore.enamel.layout.android.visualentity.style.EStyle
import com.benoitthore.enamel.layout.android.visualentity.style.EStyleable
import com.benoitthore.enamel.layout.android.visualentity.style.VisualEntityDrawer

abstract class BaseVisualEntity private constructor(protected val drawer: VisualEntityDrawer) :
    VisualEntity,
    EStyleable by drawer {

    constructor() : this(VisualEntityDrawer())

    private val _frame: ERectMutable = ERectMutable()
    override val frame: ERect get() = _frame

    override val transformation: ETransformation = ETransformation()

    override fun draw(canvas: Canvas) {
        canvas.withSave {
            canvas.translate(_frame.x, _frame.y)
            canvas.withTransformation(transformation) {
                onDraw(canvas)
            }
        }
    }

    abstract fun onDraw(canvas: Canvas)

    override fun updateFrame(frame: ERect) {
        _frame.set(frame)
    }
}

fun <T : VisualEntity> T.asLayout() = EDrawableLayout(this)
fun <T : VisualEntity> Iterable<T>.asLayout() = map { it.asLayout() }


class EDrawableLayout<T : VisualEntity>(val entity: T) : ELayout {
    override val children: List<ELayout>
        get() = emptyList()

    override fun size(toFit: ESize): ESize = entity.intrinsicSize


    override fun arrange(frame: ERect) {
        entity.updateFrame(frame)
    }
}