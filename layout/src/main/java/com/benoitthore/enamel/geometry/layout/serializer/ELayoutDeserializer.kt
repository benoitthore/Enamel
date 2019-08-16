package com.benoitthore.enamel.geometry.layout.serializer

import com.benoitthore.enamel.geometry.alignement.EAlignment
import com.benoitthore.enamel.geometry.alignement.ERectEdge
import com.benoitthore.enamel.geometry.figures.ESizeMutable
import com.benoitthore.enamel.geometry.layout.*
import com.benoitthore.enamel.geometry.layout.ESizingLayout.ELayoutSpace
import com.benoitthore.enamel.geometry.layout.ESizingLayout.ELayoutSpace.*
import com.benoitthore.enamel.geometry.layout.refs.ELayoutTag
import com.benoitthore.enamel.geometry.primitives.EOffset
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

private fun JSONObject._getNumber(key: String) = get(key) as Number // Required to run on Android
private fun JSONObject._optNumber(key: String) = opt(key) as? Number // Required to run on Android

private fun String.toRectEdge() = ERectEdge.valueOf(this)
private fun String.toAlignment() = EAlignment.valueOf(this)
private fun JSONObject.toSize() = ESizeMutable(width = _getNumber("height"), height = _getNumber("height"))
private fun JSONObject.toOffset() =
    EOffset(
        left = _getNumber("left"),
        top = _getNumber("top"),
        right = _getNumber("right"),
        bottom = _getNumber("bottom")
    )

class ELayoutDeserializer(
    val deserializeClass: (JSONObject) -> Class<out ELayout> = {
        Class.forName(
            it.getString("layoutClazz")
                .substringAfter(' ') // String is "class com.domain.layout.ELayout"

        ) as Class<out ELayout>
    }
) {


    private val deserializerMap: MutableMap<Class<out ELayout>, ELayoutDeserializer.(JSONObject) -> ELayout> =
        mutableMapOf()


    init {

        addDeserializer(ELayoutTag::class.java) { jsonObject ->
            val tag = jsonObject.getString("tag")
            ELayoutTag(tag)
        }

        addDeserializer(EBarLayout::class.java) { jsonObject ->
            EBarLayout(
                child = jsonObject.getJSONObject("child").deserialize(),
                side = jsonObject.getString("side").toRectEdge()
            )
        }

        addDeserializer(EBoxLayout::class.java) { jsonObject ->
            EBoxLayout(
                alignment = jsonObject.getString("alignment").toAlignment(),
                child = jsonObject.getJSONObject("child").deserialize(),
                snugged = jsonObject.getBoolean("snugged")
            )
        }

        addDeserializer(ELayoutLeaf::class.java) { jsonObject ->
            ELayoutLeaf(
                color = jsonObject.getInt("color"),
                child = jsonObject.optJSONObject("child")?.deserialize()
            )
        }

        addDeserializer(EStackLayout::class.java) { jsonObject ->
            EStackLayout(
                childLayouts = jsonObject.getJSONArray("children").deserialize().toMutableList(),
                spacing = jsonObject._getNumber("spacing"),
                alignment = jsonObject.getString("alignment").toAlignment()
            )
        }

        addDeserializer(EDivideLayout::class.java) { jsonObject ->
            val byJson = jsonObject.getJSONObject("by")
            val by: EDivideLayout.Division = when (byJson.getString("type")) {
                "slice" -> EDivideLayout.Division.Slice
                "fraction" -> EDivideLayout.Division.Fraction(byJson._getNumber("value"))
                "distance" -> EDivideLayout.Division.Distance(byJson._getNumber("value"))
                else -> throw Exception("Unknown divide type ${byJson.getString("type")}")
            }
            EDivideLayout(
                slice = jsonObject.getJSONObject("slice").deserialize(),
                remainder = jsonObject.getJSONObject("remainder").deserialize(),
                by = by,
                edge = jsonObject.getString("edge").toRectEdge(),
                spacing = jsonObject._getNumber("spacing"),
                snugged = jsonObject.getBoolean("snugged")
            )
        }
        addDeserializer(EJustifiedLayout::class.java) { jsonObject ->
            EJustifiedLayout(
                alignment = jsonObject.getString("alignment").toAlignment(),
                childLayouts = jsonObject.getJSONArray("children").deserialize()
            )
        }
        addDeserializer(EPaddingLayout::class.java) { jsonObject ->
            EPaddingLayout(
                child = jsonObject.getJSONObject("child").deserialize(),
                padding = jsonObject.getJSONObject("padding").toOffset()
            )
        }
        addDeserializer(ESizingLayout::class.java) { jsonObject ->
            val jsonSpace = jsonObject.getJSONObject("space")
            val type = ELayoutSpace.Type.valueOf(jsonSpace.getString("type"))
            val space: ESizingLayout.ELayoutSpace = when (type) {
                Type.Size -> Size(jsonSpace.toSize())
                Type.Width -> Width(jsonSpace._getNumber("width"))
                Type.Height -> Height(jsonSpace._getNumber("height"))
                Type.Scale -> Scale(
                    horizontal = jsonSpace._optNumber("horizontal"),
                    vertical = jsonSpace._optNumber("vertical")
                )
                Type.AspectFitting -> AspectFitting(jsonSpace.toSize())
                Type.AspectFilling -> AspectFilling(jsonSpace.toSize())
                Type.Func -> throw Exception("Cannot serialize ELayoutSpace with type Func")
            }
            ESizingLayout(space = space, child = jsonObject.getJSONObject("child").deserialize())
        }
        addDeserializer(ESnuggingLayout::class.java) { jsonObject ->
            ESnuggingLayout(jsonObject.getJSONObject("child").deserialize() as ELayoutAlongAxis)
        }
        addDeserializer(ETrackingLayout::class.java) { jsonObject ->
            ETrackingLayout(
                src = jsonObject.getJSONObject("src").deserialize(),
                dst = jsonObject.getJSONObject("dst").deserialize()
            )
        }

    }


    fun <T : ELayout> addDeserializer(clazz: Class<T>, deserializer: ELayoutDeserializer.(JSONObject) -> ELayout) {
        deserializerMap[clazz] = deserializer as ELayoutDeserializer.(JSONObject) -> ELayout
    }

    fun readLayouts(jsonArray: JSONArray): MutableList<ELayout> {
        return (0 until jsonArray.length())
            .map { readLayout(jsonArray.getJSONObject(it)) }
            .toMutableList()
    }

    fun JSONObject.deserialize() = readLayout(this)
    fun JSONArray.deserialize() = readLayouts(this)

    fun readLayout(jsonObject: JSONObject): ELayout {
        val clazz = deserializeClass(jsonObject)

        return deserializerMap[clazz]?.invoke(this, jsonObject)
            ?: throw Exception("No deserializer for ${jsonObject.get("layoutClazz")}")
    }

    fun deserialize(json: String) = readLayout(JSONObject(json))
}