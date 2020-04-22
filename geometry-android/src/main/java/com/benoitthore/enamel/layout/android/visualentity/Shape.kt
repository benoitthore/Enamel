package com.benoitthore.enamel.layout.android.visualentity

import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import com.benoitthore.enamel.geometry.figures.ELine
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.geometry.figures.line
import com.benoitthore.enamel.geometry.primitives.*
import com.benoitthore.enamel.layout.android.extract.drawRect
import com.benoitthore.enamel.layout.android.extract.toLinearGradient

class ETransformation {
    val rotation: EAngleMutable = EAngleMutable()
    val rotationPivot: EPointMutable = EPointMutable.half

    val scale: EPointMutable = EPointMutable.unit
    val scalePivot: EPointMutable = EPointMutable.half

    val translation: EPointMutable = EPointMutable()
}

data class EStyle(
    var fill: Mesh? = null,
    var border: Border? = null,
    var shadow: Shadow? = null
) {
    data class Shadow(var mesh: Mesh, val position: EPointMutable)
    data class Border(var mesh: Mesh, var width: Float) {
        constructor(color: Int, width: Float) : this(Mesh.Color(color), width)
    }

    sealed class Mesh(var alpha: Float) {
        class Color(var color: Int, alpha: Float = 1f) : Mesh(alpha)
        class Gradient(var gradient: EGradient, alpha: Float = 1f) : Mesh(alpha)
    }

}

///
///
///

sealed class EGradient {
    abstract fun getShader(frame: ERect): Shader

    class Diagonal(val line: ELine, val colors: List<Int>) : EGradient() {
        private val shader: LinearGradient by lazy {
            line.toLinearGradient(colors)
        }

        override fun getShader(frame: ERect): LinearGradient = shader
    }

    class DiagonalFill(val angle: EAngle, val colors: List<Int>) : EGradient() {
        override fun getShader(frame: ERect): LinearGradient {
            TODO("Implement when this is done: https://trello.com/c/Db0MDiJb/12-add-rect-to-line-and-line-to-rect-functions")
//            val center = frame.center()
//            // TODO
//            val line = center line center.offsetAngle(angle, 10)
//            return line.toLinearGradient(colors)
        }
    }

    // TODO
    abstract class Radial(val colors: List<Int>) : EGradient() {
    }
}

///
///
///


interface EStyleable {
    var style: EStyle
}

interface ETransformable {
    var transformation: ETransformation
}

class RectVisualEntity : EStyleable, ETransformable {
    override var style: EStyle = EStyle()
        set(value) {
            field = value
            updateStyle()
        }
    override var transformation: ETransformation = ETransformation()


    val rect: ERectMutable = ERectMutable()

    private val fillPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
        }
    }
    private val borderPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
        }
    }
    private val shadowPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
        }
    }

    fun updateStyle() {
        with(style) {
            fill?.let { fillPaint.setMesh(rect, it) }
            border?.let {
                borderPaint.strokeWidth = it.width
                borderPaint.setMesh(rect, it.mesh)
            }

            // TODO
//            shadow?.let {
//                shadowPaint.setMesh(it.mesh)
//            }
        }
    }

    fun draw(canvas: Canvas) {
        canvas.withTransformation(transformation) {
            canvas.drawRect(rect, borderPaint)
            canvas.drawRect(rect, fillPaint)

            // TODO
//            canvas.drawRect(rect, shadowPaint)
        }
    }
}

///
///
///


private fun Paint.setMesh(frame: ERect, mesh: EStyle.Mesh) {
    alpha = ((mesh.alpha) * 255).toInt()
    when (mesh) {
        is EStyle.Mesh.Color -> {
            color = mesh.color
        }
        is EStyle.Mesh.Gradient -> {
            shader = mesh.gradient.getShader(frame)
        }
    }
}

inline fun Canvas.withTransformation(
    transformation: ETransformation,
    crossinline block: Canvas.() -> Unit
) =
    withSave {
        with(transformation) {
            translate(translation.x, translation.y)
            rotate(
                rotation.degrees,
                rotationPivot.x,
                rotationPivot.y
            )
            scale(scale.x, scale.y, scalePivot.x, scalePivot.y)
        }
        block()
    }


// TODO Replace with AndroidX
inline fun Canvas.withSave(crossinline block: Canvas.() -> Unit) = apply {
    val save = save()
    block()
    restoreToCount(save)
}