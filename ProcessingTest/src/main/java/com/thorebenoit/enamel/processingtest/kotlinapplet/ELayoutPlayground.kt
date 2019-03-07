package com.thorebenoit.enamel.processingtest.kotlinapplet

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.NamedType
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
import com.fasterxml.jackson.databind.jsontype.impl.ClassNameIdResolver
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder
import com.fasterxml.jackson.databind.type.SimpleType
import com.fasterxml.jackson.databind.type.TypeFactory
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.thorebenoit.enamel.kotlin.core.data.toJson
import com.thorebenoit.enamel.kotlin.core.of
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.layout.*
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.*
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPAppletLambda
import org.deeplearning4j.nn.conf.NeuralNetConfiguration.mapper


//            registerSubtypes(
//                ELayout::class.java,
//                EBarLayout::class.java,
//                EBoxLayout::class.java,
//                EDivideLayout::class.java,
//                EJustifiedLayout::class.java,
//                ELayout::class.java,
//                ELayoutLeaf::class.java,
//                EPaddingLayout::class.java,
//                ESizingLayout::class.java,
//                ESnuggingLayout::class.java,
//                EStackLayout::class.java
//            )


//fun customMapper(): ObjectMapper =
//    jacksonObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//        .apply {
//
////            val mapper = this
////            val typeResolverBuilder: StdTypeResolverBuilder =
////                ObjectMapper.DefaultTypeResolverBuilder(ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE)
////                    .apply {
////                        inclusion(JsonTypeInfo.As.PROPERTY)
////                    }
////
////            typeResolverBuilder.init(
////                JsonTypeInfo.Id.CLASS,
////                ClassNameIdResolver(SimpleType.constructUnsafe(ELayout::class.java), TypeFactory.defaultInstance())
////            )
////            mapper.setDefaultTyping(typeResolverBuilder)
//
//
//            val mapper = ObjectMapper()
//            mapper.enableDefaultTypingAsProperty(ObjectMapper.DefaultTyping.NON_FINAL, "@class")
//            val resolverBuilder = TypeResolverBuilder()
//            resolverBuilder.init(JsonTypeInfo.Id.CLASS, null).inclusion(JsonTypeInfo.As.PROPERTY)
//            resolverBuilder.typeProperty("@Class")
//            mapper.setDefaultTyping(resolverBuilder)
//            val subtypeResolver = mapper.subtypeResolver
//            subtypeResolver.registerSubtypes(NamedType(uvw::class.java, "Uvw"))
//            subtypeResolver.registerSubtypes(NamedType(xyz::class.java, "Xyz"))
//            mapper.subtypeResolver = subtypeResolver
//            return mapper
//
//
//        }

fun customMapper(): ObjectMapper {
    val mapper = ObjectMapper()

    var typeResolverBuilder: StdTypeResolverBuilder =
        ObjectMapper.DefaultTypeResolverBuilder(ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE)
    typeResolverBuilder = typeResolverBuilder.inclusion(JsonTypeInfo.As.PROPERTY)
    typeResolverBuilder.init(
        JsonTypeInfo.Id.CLASS,
        ClassNameIdResolver(SimpleType.constructUnsafe(ELayout::class.java), TypeFactory.defaultInstance())
    )
    mapper.setDefaultTyping(typeResolverBuilder)

    return mapper
//    return jacksonObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).apply {
//        enableDefaultTyping()
//    }
}

private fun Any.toJson(): String = customMapper().writeValueAsString(this)

private fun <T : Any> String.fromJson(clazz: Class<T>): T = customMapper().readValue(this, clazz)

private inline fun <reified T : Any> String.fromJson(): T = customMapper().readValue(this)

fun main() {
    val layout = 3.of { ELayoutLeaf() }
//        .mapIndexed { i, layout ->
//            layout.sizedSquare((i + 1) * 100)
//        }
//        .stacked(EAlignment.rightTop, spacing = 10)
//        .snugged()
//        .arranged(EAlignment.topLeft)
//        .padded(20)

    val json = layout.toJson().print
//    json.fromJson<ELayout>().toJson().print
}
