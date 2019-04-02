package com.thorebenoit.enamel.kotlin.geometry.layout.serializer

import com.thorebenoit.enamel.kotlin.core.of
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.layout.*
import com.thorebenoit.enamel.kotlin.geometry.layout.ESizingLayout.ELayoutSpace.*
import com.thorebenoit.enamel.kotlin.geometry.layout.androidlike.ELinearLayout
import com.thorebenoit.enamel.kotlin.geometry.layout.androidlike.dsl.stacked
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.*
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.ELayoutTag
import com.thorebenoit.enamel.kotlin.geometry.primitives.EOffset
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

private fun JSONObject.putSize(size: ESizeType): JSONObject =
    put("width", size.width).put("height", size.height)

private fun JSONObject.putOffset(name: String, offset: EOffset): JSONObject = put(
    name, JSONObject()
        .put("top", offset.top)
        .put("left", offset.left)
        .put("right", offset.right)
        .put("bottom", offset.bottom)
)

class ELayoutSerializer(val serializeClass: JSONObject.(ELayout) -> Unit = { put("layoutClazz", it::class.java) }) {

    private val serializerMap: MutableMap<Class<out ELayout>, ELayoutSerializer.(ELayout) -> JSONObject> =
        mutableMapOf()

    init {

        addSerializer(ELayoutTag::class.java) { layout ->
            JSONObject().apply {
                put("tag", layout.tag)
            }
        }

        addSerializer(ELinearLayout::class.java) { layout ->
            JSONObject().apply {
                put("gravity", layout.gravity)
                put("alignment", layout.alignment)
                put("spacing", layout.spacing)
                put("width", layout.width)
                put("height", layout.height)
                put("children", serialize(layout.childLayouts))
            }
        }

        addSerializer(EBarLayout::class.java) { layout ->
            JSONObject().apply {
                put("side", layout.side)
                put("child", serialize(layout.child))
            }
        }

        addSerializer(EBoxLayout::class.java) { layout ->
            JSONObject().apply {
                put("alignment", layout.alignment.name)
                put("snugged", layout.snugged)
                put("child", layout.child.serialized())
            }
        }

        addSerializer(ELayoutLeaf::class.java) { layout ->
            JSONObject().apply {
                put("color", layout.color)
                put("frame", layout.frame)
                layout.child?.let {
                    put("child", it.serialized())
                }
            }
        }
        addSerializer(EStackLayout::class.java) { layout ->
            JSONObject().apply {
                put("alignment", layout.alignment.name)
                put("spacing", layout.spacing)
                put("children", serialize(layout.childLayouts))
            }
        }
        addSerializer(EDivideLayout::class.java) { layout ->
            JSONObject().apply {
                val by: JSONObject = layout.by.let { by ->
                    when (by) {
                        is EDivideLayout.Division.Distance -> JSONObject()
                            .put("type", "distance")
                            .put("value", by.distance)
                        is EDivideLayout.Division.Fraction -> JSONObject()
                            .put("type", "fraction")
                            .put("value", by.fraction)
                        EDivideLayout.Division.Slice -> JSONObject().put("type", "slice")
                    }
                }
                put("by", by)
                put("edge", layout.edge)
                put("slice", serialize(layout.slice))
                put("remainder", serialize(layout.remainder))
                put("snugged", layout.snugged)
                put("spacing", layout.spacing)
            }
        }
        addSerializer(EJustifiedLayout::class.java) { layout ->
            JSONObject().apply {
                put("alignment", layout.alignment)
                put("children", serialize(layout.childLayouts))
            }
        }
        addSerializer(EPaddingLayout::class.java) { layout ->
            JSONObject().apply {
                putOffset("padding", layout.padding)
                put("child", serialize(layout.child))

            }
        }
        addSerializer(ESizingLayout::class.java) { layout ->
            JSONObject().apply {
                val type: JSONObject = layout.space.let {
                    when (it) {
                        is Size -> JSONObject().putSize(it.size)
                        is Width -> JSONObject()
                            .put("width", it.width)
                        is Height -> JSONObject()
                            .put("height", it.height)
                        is Scale -> JSONObject()
                            .put("horizontal", it.horizontal)
                            .put("vertical", it.vertical)
                        is AspectFitting -> JSONObject().putSize(it.size)
                        is AspectFilling -> JSONObject().putSize(it.size)
                        is Func -> throw Exception("Cannot serialize ELayoutSpace with type Func")
                    }
                        .put("type", it.type)
                }

                put("space", type)
                put("child", serialize(layout.child))
            }
        }
        addSerializer(ESnuggingLayout::class.java) { layout ->
            JSONObject().apply {
                put("child", serialize(layout.child))
            }
        }
        addSerializer(ETrackingLayout::class.java) { layout ->
            JSONObject().apply {
                put("src", serialize(layout.src))
                put("dst", serialize(layout.dst))
            }
        }
    }


    fun <T : ELayout> addSerializer(clazz: Class<T>, serializer: ELayoutSerializer.(T) -> JSONObject) {
        serializerMap[clazz] = serializer as ELayoutSerializer.(ELayout) -> JSONObject
    }

    fun serialize(layouts: List<ELayout>) =
        JSONArray().apply {
            layouts.forEach { put(serialize(it)) }
        }

    fun serialize(layout: ELayout): JSONObject =
        serializerMap[layout::class.java]?.invoke(this, layout)?.apply { serializeClass(layout) }
            ?: throw Exception("No serializer for ${layout.javaClass}")

    fun ELayout.serialized() = serialize(this)

}


fun main() {


    val layout =
        2.of {
            ELayoutLeaf(333).sized(123, 123)
        }
            .justified(EAlignment.leftCenter)
            .snugged()
            .arranged(EAlignment.topLeft)
            .padded(5)

    val layout2 =
        1.of { ELayoutLeaf(123) }
            .mapIndexed { i, layout ->
                layout.sizedSquare((i + 1) * 100)
            }
            .stacked(EAlignment.topLeft, spacing = 321)
            .arranged(EAlignment.topLeft)
            .padded(20)


    val serializer = ELayoutSerializer()


    val json =
        serializer.serialize(listOf(layout, layout2).stacked(EAlignment.bottomCenter, spacing = 2)).toString().print

    ELayoutDeserializer().deserialize(json).print
}