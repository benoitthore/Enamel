package com.thorebenoit.enamel.kotlin.geometry.layout.serializer

import com.thorebenoit.enamel.kotlin.core.math.i
import com.thorebenoit.enamel.kotlin.geometry.layout.*
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ESizingLayoutSerializer.Space.*
import java.lang.Exception

interface ELayoutSerializer<T : ELayout> {
    fun serializeUnsafe(layout: ELayout, dataStore: ELayoutDataStore) {
        serialize(layout as T, dataStore)
    }

    fun serialize(layout: T, dataStore: ELayoutDataStore)
    fun deserialize(dataStore: ELayoutDataStore): T
}


object EBoxLayoutSerializer : ELayoutSerializer<EBoxLayout> {
    override fun deserialize(dataStore: ELayoutDataStore): EBoxLayout {
        return EBoxLayout().apply {
            alignment = dataStore.readAlignment()
            snugged = dataStore.readBool()
            child = dataStore.readLayout()
        }
    }

    override fun serialize(layout: EBoxLayout, dataStore: ELayoutDataStore) {
        dataStore.add(layout.alignment)
        dataStore.add(layout.snugged)
        dataStore.add(layout.child)
    }
}


object EBarLayoutSerializer : ELayoutSerializer<EBarLayout> {
    override fun deserialize(dataStore: ELayoutDataStore): EBarLayout = EBarLayout().apply {
        side = dataStore.readRectEdge()
        child = dataStore.readLayout()
    }

    override fun serialize(layout: EBarLayout, dataStore: ELayoutDataStore) {
        dataStore.add(layout.side)
        dataStore.add(layout.child)
    }
}


object EJustifiedLayoutSerializer : ELayoutSerializer<EJustifiedLayout> {
    override fun deserialize(dataStore: ELayoutDataStore): EJustifiedLayout = EJustifiedLayout().apply {
        alignment = dataStore.readAlignment()
        childLayouts.clear()
        childLayouts.addAll(dataStore.readLayouts())
    }

    override fun serialize(layout: EJustifiedLayout, dataStore: ELayoutDataStore) {
        dataStore.add(layout.alignment)
        dataStore.add(layout.childLayouts)
    }
}

object ELayoutLeafSerializer : ELayoutSerializer<ELayoutLeaf> {
    override fun deserialize(dataStore: ELayoutDataStore): ELayoutLeaf = ELayoutLeaf(dataStore.readNumber().i)


    override fun serialize(layout: ELayoutLeaf, dataStore: ELayoutDataStore) {
        dataStore.add(layout.color)
    }
}

object EPaddingLayoutSerializer : ELayoutSerializer<EPaddingLayout> {
    override fun deserialize(dataStore: ELayoutDataStore): EPaddingLayout = EPaddingLayout().apply {
        padding = dataStore.readOffset()
        child = dataStore.readLayout()
    }

    override fun serialize(layout: EPaddingLayout, dataStore: ELayoutDataStore) {
        dataStore.add(layout.padding)
        dataStore.add(layout.child)
    }
}

object ESnuggingLayoutSerializer : ELayoutSerializer<ESnuggingLayout> {

    override fun deserialize(dataStore: ELayoutDataStore): ESnuggingLayout = ESnuggingLayout().apply {
        child = dataStore.readLayout() as ELayoutAlongAxis
    }

    override fun serialize(layout: ESnuggingLayout, dataStore: ELayoutDataStore) {
        dataStore.add(layout.child)
    }

}

object EStackLayoutSerializer : ELayoutSerializer<EStackLayout> {

    override fun deserialize(dataStore: ELayoutDataStore): EStackLayout = EStackLayout().apply {
        alignment = dataStore.readAlignment()
        spacing = dataStore.readNumber()
        childLayouts.clear()
        childLayouts.addAll(dataStore.readLayouts())
    }

    override fun serialize(layout: EStackLayout, dataStore: ELayoutDataStore) {
        dataStore.add(layout.alignment)
        dataStore.add(layout.spacing)
        dataStore.add(layout.childLayouts)
    }


}

object EDivideLayoutSerializer : ELayoutSerializer<EDivideLayout> {
    override fun deserialize(dataStore: ELayoutDataStore): EDivideLayout {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun serialize(layout: EDivideLayout, dataStore: ELayoutDataStore) {
        val by = layout.by

        val _a: Unit = when (by) {
            is EDivideLayout.Division.Slice -> {
                dataStore.add(0)
            }
            is EDivideLayout.Division.Distance -> {
                dataStore.add(1)
                dataStore.add(by.distance)

            }
            is EDivideLayout.Division.Fraction -> {
                dataStore.add(2)
                dataStore.add(by.fraction)

            }
        }
        dataStore.add(layout.edge)
        dataStore.add(layout.spacing)
        dataStore.add(layout.snugged)

        dataStore.add(layout.childLayouts)

    }
}

object ESizingLayoutSerializer : ELayoutSerializer<ESizingLayout> {

    private enum class Space {
        Size,
        Width,
        Height,
        Scale,
        AspectFitting,
        AspectFilling
    }

    override fun deserialize(dataStore: ELayoutDataStore): ESizingLayout = ESizingLayout().apply {
        val sizingType = values()[dataStore.readNumber().i]
        space = when (sizingType) {

            Size -> {
                ESizingLayout.ELayoutSpace.Size(dataStore.readSize())
            }
            Width -> ESizingLayout.ELayoutSpace.Width(dataStore.readNumber())
            Height -> ESizingLayout.ELayoutSpace.Height(dataStore.readNumber())
            Scale -> {
                val isHorizontal = dataStore.readBool()
                val isVertical = dataStore.readBool()
                val horizontal = if (isHorizontal) dataStore.readNumber() else null
                val vertical = if (isVertical) dataStore.readNumber() else null

                ESizingLayout.ELayoutSpace.Scale(horizontal, vertical)
            }
            AspectFitting -> ESizingLayout.ELayoutSpace.AspectFitting(dataStore.readSize())
            AspectFilling -> ESizingLayout.ELayoutSpace.AspectFilling(dataStore.readSize())
        }
    }

    override fun serialize(layout: ESizingLayout, dataStore: ELayoutDataStore) {
        val space = layout.space
        val _a: Unit? = when (space) {

            is ESizingLayout.ELayoutSpace.Size -> {
                dataStore.add(Size.ordinal)
                dataStore.add(space.size)
            }
            is ESizingLayout.ELayoutSpace.Width -> {
                dataStore.add(Width.ordinal)
                dataStore.add(space.width)

            }
            is ESizingLayout.ELayoutSpace.Height -> {
                dataStore.add(Height.ordinal)
                dataStore.add(space.height)
            }
            is ESizingLayout.ELayoutSpace.Scale -> {
                dataStore.add(Scale.ordinal)
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
                dataStore.add(AspectFitting.ordinal)
                dataStore.add(space.size)

            }
            is ESizingLayout.ELayoutSpace.AspectFilling -> {
                dataStore.add(AspectFilling.ordinal)
                dataStore.add(space.size)

            }
            is ESizingLayout.ELayoutSpace.Func -> {
                throw Exception("Cannot serialize function")

            }
        }

        dataStore.add(layout.childLayouts)

    }
}