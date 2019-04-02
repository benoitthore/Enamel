package com.thorebenoit.enamel.kotlin.geometry.layout.serializer

import com.thorebenoit.enamel.kotlin.core.math.bool
import com.thorebenoit.enamel.kotlin.core.math.i
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.alignement.ERectEdge
import com.thorebenoit.enamel.kotlin.geometry.figures.ESize
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.layout.*
import com.thorebenoit.enamel.kotlin.geometry.primitives.EOffset
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

private fun String.toRectEdge() = ERectEdge.valueOf(this)
private fun String.toAlignment() = EAlignment.valueOf(this)

class ELayoutDeserializer {


    private val deserializerMap: MutableMap<Class<out ELayout>, ELayoutDeserializer.(JSONObject) -> ELayout> =
        mutableMapOf()


    init {
        addDeserializer(EBarLayout::class.java) { jsonObject ->
            val side = jsonObject.getString("side").toRectEdge()
            val child = readLayout(jsonObject.getJSONObject("child"))
            EBarLayout(child, side)
        }

        addDeserializer(EBoxLayout::class.java) { jsonObject ->
            val alignment = jsonObject.getString("alignment").toAlignment()
            val child = readLayout(jsonObject.getJSONObject("child"))
            val snugged = jsonObject.getBoolean("snugged")
            EBoxLayout(alignment = alignment, child = child, snugged = snugged)
        }

        addDeserializer(ELayoutLeaf::class.java) { jsonObject ->
            ELayoutLeaf(jsonObject.getInt("color"))
        }

        addDeserializer(EStackLayout::class.java) { jsonObject ->
            val alignment = jsonObject.getString("alignment").toAlignment()
            val spacing = jsonObject.getNumber("spacing")
            val children = readLayouts(jsonObject.getJSONArray("children")).toMutableList()
            EStackLayout(childLayouts = children, spacing = spacing, alignment = alignment)
        }

    }


    fun <T : ELayout> addDeserializer(clazz: Class<T>, deserializer: ELayoutDeserializer.(JSONObject) -> T) {
        deserializerMap[clazz] = deserializer as ELayoutDeserializer.(JSONObject) -> ELayout
    }

    fun readLayouts(jsonArray: JSONArray): MutableList<ELayout> {
        return (0 until jsonArray.length())
            .map { readLayout(jsonArray.getJSONObject(it)) }
            .toMutableList()
    }

    fun readLayout(jsonObject: JSONObject): ELayout {
        val clazz = jsonObject.get("layoutClazz") as Class<out ELayout>

        return deserializerMap[clazz]?.invoke(this, jsonObject)
            ?: throw Exception("No deserializer for ${jsonObject.get("layoutClazz")}")
    }
}