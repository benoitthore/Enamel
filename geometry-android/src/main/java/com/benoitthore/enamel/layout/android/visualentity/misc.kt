package com.benoitthore.enamel.layout.android.visualentity

import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import com.benoitthore.enamel.geometry.figures.ELine
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.primitives.EAngle
import com.benoitthore.enamel.geometry.primitives.EAngleMutable
import com.benoitthore.enamel.geometry.primitives.EPointMutable
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

///
///
///
