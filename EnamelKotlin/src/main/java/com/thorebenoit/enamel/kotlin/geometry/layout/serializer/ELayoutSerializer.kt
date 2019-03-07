package com.thorebenoit.enamel.kotlin.geometry.layout.serializer

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.thorebenoit.enamel.kotlin.core.data.ignoreUnknownObjectMapper
import com.thorebenoit.enamel.kotlin.core.of
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.core.time.ETimer
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.layout.*
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.*


object ELayoutSerializer {
    private val customMapper: ObjectMapper = ignoreUnknownObjectMapper()


    init {
        registerSubtype(EBarLayout::class.java)
        registerSubtype(EBoxLayout::class.java)
        registerSubtype(EDivideLayout::class.java)
        registerSubtype(EJustifiedLayout::class.java)
        registerSubtype(ELayout::class.java)
        registerSubtype(ELayoutLeaf::class.java)
        registerSubtype(EPaddingLayout::class.java)
        registerSubtype(ESizingLayout::class.java)
        registerSubtype(ESnuggingLayout::class.java)
        registerSubtype(EStackLayout::class.java)
    }

    fun <T : ELayout> registerSubtype(clazz: Class<T>) {
        customMapper.registerSubtypes(clazz)

    }


    fun serialize(list: List<ELayout>): String =
        customMapper
            .writerFor(object : TypeReference<List<ELayout>>() {})
            .writeValueAsString(list)

    fun serialize(layout: ELayout): String =
        customMapper
            .writeValueAsString(layout)

    fun deserialize(json: String): ELayout = customMapper.readValue(json)
    fun deserializeList(json: String): List<ELayout> = customMapper.readValue(json)

}

fun main() {
    val layout = 3.of { ELayoutLeaf() }
        .mapIndexed { i, layout ->
            layout.sizedSquare((i + 1) * 100)
        }
        .stacked(EAlignment.rightTop, spacing = 10)
        .snugged()
        .arranged(EAlignment.topLeft)
        .padded(20)


    ELayoutSerializer.apply {
        val jsonLayout = serialize(layout).print
        val jsonListLayout = serialize(listOf(layout)).print

        val newLayout = deserialize(jsonLayout)
        val newLayoutList = deserializeList(jsonListLayout)
    }


}
