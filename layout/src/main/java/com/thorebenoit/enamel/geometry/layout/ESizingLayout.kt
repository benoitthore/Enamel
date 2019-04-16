package com.thorebenoit.enamel.geometry.layout

import com.thorebenoit.enamel.geometry.alignement.fillSize
import com.thorebenoit.enamel.geometry.alignement.fitSize
import com.thorebenoit.enamel.geometry.figures.ERect
import com.thorebenoit.enamel.geometry.figures.ESize
import com.thorebenoit.enamel.geometry.figures.size
import com.thorebenoit.enamel.geometry.primitives.times

class ESizingLayout(child: ELayout, var space: ELayoutSpace) : ELayout {

    var child: ELayout = child
        set(value) {
            field = value
            _childLayouts.clear()
            _childLayouts.add(field)
        }

    private val _childLayouts: MutableList<ELayout> = mutableListOf(child)
    override val childLayouts: List<ELayout> get() = _childLayouts

    override fun size(toFit: ESize): ESize {
        val space = space
        return when (space) {
            is ELayoutSpace.Size -> space.size

            // TODO
            // Should this be "AT_MOST" or "EXACTLY"

            // AT_MOST implementation:
//            is ELayoutSpace.Width -> child.size(toFit.copy(width = space.width))
//            is ELayoutSpace.Height -> child.size(toFit.copy(height = space.height))

            // EXACTLY implementation:
            is ELayoutSpace.Width -> child.size(toFit.copy(width = space.width)).copy(width = space.width)
            is ELayoutSpace.Height -> child.size(toFit.copy(height = space.height)).copy(height = space.height)

            //

            is ELayoutSpace.Scale -> {
                TODO("This is probably not working")
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

    override fun arrange(frame: ERect) {
        child.arrange(frame)
    }


    sealed class ELayoutSpace {
        enum class Type {
            Size,
            Width,
            Height,
            Scale,
            AspectFitting,
            AspectFilling,
            Func
        }

        abstract val type: Type

        class Size(val size: ESize) : ELayoutSpace() {
            constructor(width: Number, height: Number) : this(width size height)

            override val type: Type get() = Type.Size
            override fun toString(): String {
                return "Size(size=$size)"
            }

        }

        class Width(val width: Number) : ELayoutSpace() {
            override val type: Type get() = Type.Width
            override fun toString(): String {
                return "Width(width=$width)"
            }

        }

        class Height(val height: Number) : ELayoutSpace() {
            override val type: Type get() = Type.Height
            override fun toString(): String {
                return "Height(height=$height)"
            }

        }

        class Scale(val horizontal: Number?, val vertical: Number?) : ELayoutSpace() {
            override val type: Type get() = Type.Scale
            override fun toString(): String {
                return "Scale(horizontal=$horizontal, vertical=$vertical)"
            }
        }

        class AspectFitting(val size: ESize) : ELayoutSpace() {
            override val type: Type get() = Type.AspectFitting
            override fun toString(): String {
                return "AspectFitting(size=$size)"
            }

        }

        class AspectFilling(val size: ESize) : ELayoutSpace() {
            override val type: Type get() = Type.AspectFilling
            override fun toString(): String {
                return "AspectFilling(size=$size)"
            }

        }

        class Func(val f: (ESize) -> ESize) : ELayoutSpace() {
            override val type: Type get() = Type.Func
            override fun toString(): String {
                return "Func(f=$f)"
            }

        }
    }


    override fun toString(): String {
        return "ESizingLayout(space=$space, child=$child)"
    }


}

