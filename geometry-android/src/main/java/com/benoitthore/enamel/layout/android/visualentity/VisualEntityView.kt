package com.benoitthore.enamel.layout.android.visualentity

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

class VisualEntityView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var visualEntities = mutableListOf<VisualEntity>()


    fun show(visualEntity: VisualEntity) {
        visualEntities.clear()
        visualEntities.add(visualEntity)
        invalidate()
    }

    fun show(vararg visualEntity: VisualEntity) = show(visualEntity.toList())

    fun show(visualEntities: List<VisualEntity>) {
        this.visualEntities.clear()
        this.visualEntities.addAll(visualEntities)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        visualEntities.forEach { it.draw(canvas) }
    }

}