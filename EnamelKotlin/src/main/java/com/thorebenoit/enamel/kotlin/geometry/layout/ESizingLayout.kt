package com.thorebenoit.enamel.kotlin.geometry.layout

import com.thorebenoit.enamel.kotlin.core.math.i
import com.thorebenoit.enamel.kotlin.geometry.alignement.fillSize
import com.thorebenoit.enamel.kotlin.geometry.alignement.fitSize
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.figures.size
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ELayoutDeserializer
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ELayoutSerializer
import com.thorebenoit.enamel.kotlin.geometry.primitives.times

class ESizingLayout(child: ELayout = ELayoutLeaf(), var space: ELayoutSpace = ELayoutSpace.Width(0)) : ELayout {

    var child: ELayout = child
        set(value) {
            field = value
            _childLayouts.clear()
            _childLayouts.add(field)
        }

    private val _childLayouts: MutableList<ELayout> = mutableListOf(child)
    override val childLayouts: List<ELayout> get() = _childLayouts

    override fun size(toFit: ESizeType): ESizeType {
        val space = space
        return when (space) {
            is ELayoutSpace.Size -> space.size
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

        class Size(val size: ESizeType) : ELayoutSpace() {
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

        class AspectFitting(val size: ESizeType) : ELayoutSpace() {
            override val type: Type get() = Type.AspectFitting
            override fun toString(): String {
                return "AspectFitting(size=$size)"
            }

        }

        class AspectFilling(val size: ESizeType) : ELayoutSpace() {
            override val type: Type get() = Type.AspectFilling
            override fun toString(): String {
                return "AspectFilling(size=$size)"
            }

        }

        class Func(val f: (ESizeType) -> ESizeType) : ELayoutSpace() {
            override val type: Type get() = Type.Func
            override fun toString(): String {
                return "Func(f=$f)"
            }

        }
    }


    override fun serialize(dataStore: ELayoutSerializer) {
        val space = space
        dataStore.add(space.type.ordinal)
        val _a: Unit? = when (space) {

            is ESizingLayout.ELayoutSpace.Size -> {
                dataStore.add(space.size)
            }
            is ESizingLayout.ELayoutSpace.Width -> {
                dataStore.add(space.width)

            }
            is ESizingLayout.ELayoutSpace.Height -> {
                dataStore.add(space.height)
            }
            is ESizingLayout.ELayoutSpace.Scale -> {
                dataStore.add(space.horizontal != null)
                dataStore.add(space.vertical != null)
                space.horizontal?.let {
                    dataStore.add(it)
                }
                space.vertical?.let {
                    dataStore.add(it)
                }
            }
            is ESizingLayout.ELayoutSpace.AspectFitting -> {
                dataStore.add(space.size)

            }
            is ESizingLayout.ELayoutSpace.AspectFilling -> {
                dataStore.add(space.size)

            }
            is ESizingLayout.ELayoutSpace.Func -> {
                throw Exception("Cannot serialize function")

            }
        }

        dataStore.add(child)
    }

    override fun deserialize(dataStore: ELayoutDeserializer) {
        val spaceType = ELayoutSpace.Type.values()[dataStore.readNumber().i]
        space = when (spaceType) {

            ELayoutSpace.Type.Size -> {
                ESizingLayout.ELayoutSpace.Size(dataStore.readSize())
            }
            ELayoutSpace.Type.Width -> ESizingLayout.ELayoutSpace.Width(dataStore.readNumber())
            ELayoutSpace.Type.Height -> ESizingLayout.ELayoutSpace.Height(dataStore.readNumber())
            ELayoutSpace.Type.Scale -> {
                val isHorizontal = dataStore.readBool()
                val isVertical = dataStore.readBool()
                val horizontal = if (isHorizontal) dataStore.readNumber() else null
                val vertical = if (isVertical) dataStore.readNumber() else null

                ESizingLayout.ELayoutSpace.Scale(horizontal, vertical)
            }
            ELayoutSpace.Type.AspectFitting -> ESizingLayout.ELayoutSpace.AspectFitting(dataStore.readSize())
            ELayoutSpace.Type.AspectFilling -> ESizingLayout.ELayoutSpace.AspectFilling(dataStore.readSize())
            else -> {
                throw Exception("Unknown space type $spaceType")
            }
        }
        child = dataStore.readLayout()

    }

    override fun toString(): String {
        return "ESizingLayout(space=$space, child=$child)"
    }


}

