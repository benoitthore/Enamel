package com.thorebenoit.enamel.kotlin.geometry.layout.serializer

import com.thorebenoit.enamel.kotlin.core.color.red
import com.thorebenoit.enamel.kotlin.core.math.random
import com.thorebenoit.enamel.kotlin.core.of
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayoutLeaf
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.*
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.digital.ELayoutSerializerDigital


fun main() {

    val serializer = ELayoutSerializerDigital.createIntIDSerializer()
    val layout =
        2.of {
            ELayoutLeaf(red).sized(123,123)
        }
//            .stackedRightCenter()
            .justified(EAlignment.leftCenter)
            .snugged()
            .arranged(EAlignment.topLeft)
            .padded(5)

    serializer.add(layout)
    serializer.data.print

    serializer.toDeserializer().readLayout()

//    val layout =
//        1.of { ELayoutLeaf(123) }
//            .mapIndexed { i, layout ->
//                layout.sizedSquare((i + 1) * 100)
//            }
//            .stacked(EAlignment.topLeft, spacing = 321)
//            .snugged()
//            .arranged(EAlignment.topLeft)
//            .padded(20)
//
//
//    val store = ELayoutSerializerDigital.createIntIDSerializer()
//
//    store.add(layout)
//
//    store.data.joinToString().print
//    store.toDeserializer().readLayout().print

}
