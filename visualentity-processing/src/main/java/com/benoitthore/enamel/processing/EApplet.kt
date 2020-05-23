package com.benoitthore.enamel.processing

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.alignement.EAlignment
import com.benoitthore.enamel.geometry.alignement.selfAlignInside
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rect.ERectMutable
import com.benoitthore.enamel.geometry.interfaces.bounds.setOrigin
import com.benoitthore.enamel.geometry.interfaces.bounds.setOriginSize
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable
import com.benoitthore.enamel.geometry.primitives.point.point
import com.benoitthore.enamel.geometry.primitives.size.size
import com.benoitthore.visualentity.style.EShader
import com.benoitthore.visualentity.style.EStyle
import com.benoitthore.visualentity.style.EMesh
import processing.core.PApplet
import processing.core.PConstants

fun main() {
    PApplet.main(ESketch::class.java)
}

class ESketch : PApplet() {
    private val rect: ERectVisualEntity by lazy {
        E.Rect(size = width / 2 size height / 2).toVisualEntity(
            style {
                fillColor = 0xFF_0000
            }
        )
    }

    override fun settings() {
        size(800, 800)
    }

    override fun draw() {
        background(0)

        rect.selfAlignInside(getBounds(),EAlignment.values().random())

        drawVE(rect)

        Thread.sleep(1000)
    }
}

//region style DSL

inline fun style(crossinline block: StyleBuilder.() -> Unit): EStyle =
    StyleBuilder().apply(block).build()

class StyleBuilder {

    var fillColor: Int? = null
    var fillShader: EShader? = null
    var borderColor: Int? = null
    var borderShader: EShader? = null
    var borderWidth: Number = 1f

    var shadowPositionY: Float
        get() = shadowPosition.y
        set(value) {
            _shadowPosition.y = value.toFloat()
        }

    val shadowPosition: EPoint get() = _shadowPosition

    private var _shadowPosition: EPointMutable = E.PointMutable()

    fun build(): EStyle = EStyle(
        fill = buildFillMesh(),
        border = buildBorder(),
        shadow = buildShadow()
    )


    private fun buildFillMesh(): EMesh? =
        if (fillColor == null && fillShader == null) {
            null
        } else {
            EMesh(color = fillColor, shader = fillShader)
        }

    private fun buildBorder(): EStyle.Border? =
        if (borderColor == null && borderShader == null) {
            null
        } else {
            EStyle.Border(
                mesh = EMesh(color = borderColor, shader = borderShader),
                width = borderWidth.toFloat()
            )
        }

    private fun buildShadow(): EStyle.Shadow? = null //TODO
}
//endregion