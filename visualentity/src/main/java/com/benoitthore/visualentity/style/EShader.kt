package com.benoitthore.visualentity.style

import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.figures.line.ELine
import com.benoitthore.enamel.geometry.figures.rect.ERect

data class EShader(
    val shaderType: ShaderType,
    val shaderMode: ShaderTileMode,
    val stops: List<Float>?,
    val colors: List<Int>,
    val frame: ERect
) {
    sealed class ShaderType {
        class Radial(val circle: ECircle) : ShaderType() {
            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (other !is Radial) return false

                if (circle != other.circle) return false

                return true
            }

            override fun hashCode(): Int {
                return circle.hashCode()
            }
        }

        class Linear(val line: ELine) : ShaderType() {
            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (other !is Linear) return false

                if (line != other.line) return false

                return true
            }

            override fun hashCode(): Int {
                return line.hashCode()
            }
        }
    }

    enum class ShaderTileMode {
        CLAMP, REPEAT, MIRROR
    }
}
