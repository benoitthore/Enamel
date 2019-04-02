package com.thorebenoit.enamel.kotlin.geometry.layout.serializer

import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment.*
import com.thorebenoit.enamel.kotlin.geometry.alignement.ERectEdge
import com.thorebenoit.enamel.kotlin.geometry.alignement.ERectEdge.*
import com.thorebenoit.enamel.kotlin.geometry.layout.*
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.aligned
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.arranged
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.stackedTopLeft
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception


fun JSONObject.addLayoutClazz(layout: ELayout) {
    put("layoutClazz", layout::class.java)
}

class ELayoutSerializer {

    val serializerMap: MutableMap<Class<out ELayout>, ELayoutSerializer.(ELayout) -> JSONObject> = mutableMapOf()

    init {
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
                put("child", serialize(layout.child))
            }
        }

        addSerializer(ELayoutLeaf::class.java) { layout ->
            JSONObject().apply {
                put("color", layout.color)
            }
        }
        addSerializer(EStackLayout::class.java) { layout ->
            JSONObject().apply {
                put("alignment", layout.alignment.name)
                put("spacing", layout.spacing)
                put("children", serialize(layout.childLayouts))
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
        serializerMap[layout::class.java]?.invoke(this, layout)?.apply { addLayoutClazz(layout) }
            ?: throw Exception("No serializer for ${layout.javaClass}")


}

fun main() {

    val layout1 = ELayoutLeaf(-123).aligned(top).arranged(topLeft)
    val layout2 = ELayoutLeaf(456)

//    val layout = listOf(layout1, layout2).stackedTopLeft(10)
    val layout = listOf(
        ELayoutLeaf(123),
        ELayoutLeaf(456),
        ELayoutLeaf(789)
    ).stackedTopLeft(10)
    val serializer = ELayoutSerializer()


    val json = serializer.serialize(layout).print

    ELayoutDeserializer().readLayout(json).print
}