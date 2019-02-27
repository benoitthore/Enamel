package com.thorebenoit.enamel.kotlin.geometry.layout

import apple.laf.JRSUIConstants
import com.thorebenoit.enamel.kotlin.geometry.alignement.fillSize
import com.thorebenoit.enamel.kotlin.geometry.alignement.fitSize
import com.thorebenoit.enamel.kotlin.geometry.alignement.with
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.figures.size
import com.thorebenoit.enamel.kotlin.geometry.primitives.times

class ESizingLayout(val child: ELayout, val space: ELayoutSpace) : ELayout {
    sealed class ELayoutSpace {
        class Size(val size: ESizeType) : ELayoutSpace()
        class Width(val width: Number) : ELayoutSpace()
        class Height(val height: Number) : ELayoutSpace()
        class Scale(val horizontal: Number?, val vertical: Number?) : ELayoutSpace()
        class AspectFitting(val size: ESizeType) : ELayoutSpace()
        class AspectFilling(val size: ESizeType) : ELayoutSpace()
        class Func(val f: (ESizeType) -> ESizeType) : ELayoutSpace()
    }

    override val childLayouts: List<ELayout> = mutableListOf(child)

    override fun size(toFit: ESizeType): ESizeType {
        return when (space) {
            is ELayoutSpace.Size -> toFit
            is ELayoutSpace.Width -> child.size(toFit.copy(width = space.width))
            is ELayoutSpace.Height -> child.size(toFit.copy(width = space.height))
            is ELayoutSpace.Scale -> {
                with(space) {
                    return if (horizontal != null && vertical == null) {
                        val toFit = toFit * (horizontal size 1)
                        child.size(toFit).copy(width = toFit.width)

                    } else if (horizontal == null && vertical != null) {
                        val toFit = toFit * (1 size vertical)
                        child.size(toFit).copy(height = toFit.height)

                    } else if (horizontal != null && vertical != null) {
                        toFit * (horizontal size vertical)

                    } else {
                        child.size(toFit)
                    }
                }
            }
            is ELayoutSpace.AspectFitting -> space.size.fitSize(toFit)
            is ELayoutSpace.AspectFilling -> space.size.fillSize(toFit)
            is ELayoutSpace.Func -> space.f(toFit)

        }
    }

    override fun arrange(frame: ERectType) {
        child.arrange(frame)
    }
}

