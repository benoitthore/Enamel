package com.thorebenoit.enamel.android.examples

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.widget.TextView
import com.thorebenoit.enamel.android.dp
import com.thorebenoit.enamel.android.eLayout
import com.thorebenoit.enamel.android.randomColor
import com.thorebenoit.enamel.geometry.alignement.EAlignment.*
import com.thorebenoit.enamel.geometry.alignement.ERectEdge.*
import com.thorebenoit.enamel.geometry.layout.EEmptyLayout
import com.thorebenoit.enamel.geometry.layout.ELayout
import com.thorebenoit.enamel.geometry.layout.dsl.arranged
import com.thorebenoit.enamel.geometry.layout.dsl.justified
import com.thorebenoit.enamel.geometry.layout.dsl.snugged
import com.thorebenoit.enamel.geometry.layout.dsl.stackedBottomRight
import com.thorebenoit.enamel.layout_android.EViewGroup
import com.thorebenoit.enamel.layout_android.laidIn
import com.thorebenoit.enamel.layout_android.withTag
import splitties.views.backgroundColor
import splitties.views.dsl.core.textView
import splitties.views.padding
import java.util.*


/* KTS
import com.thorebenoit.enamel.core.print
import com.thorebenoit.enamel.geometry.layout.dsl.*
import com.thorebenoit.enamel.geometry.alignement.*
import com.thorebenoit.enamel.geometry.alignement.EAlignment.*
import com.thorebenoit.enamel.geometry.alignement.ERectEdge.*
import com.thorebenoit.enamel.geometry.layout.EDivideLayout
import com.thorebenoit.enamel.geometry.layout.ELayout
import com.thorebenoit.enamel.geometry.layout.playground.sendToPlayground
import com.thorebenoit.enamel.kotlin.core.color.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

val Number.dp get() = toFloat() * 3


val ELayout.leaf get() = leaf(randomColor().withAlpha(0.25))

val list = ('A'..'F').flatMap { char ->
    (1..3).map { nbChar ->
        (List(nbChar) { char }).joinToString(separator = "")
    }
}


listOf("A","B","D","FFF","C")
    .shuffled()
    .layoutTag
    .justified(bottomRight)
    .arranged(topRight)
    .padded(16.dp)
    .sendToPlayground()
*/

fun Context.demoView1(): EViewGroup {
    return eLayout {

        this@eLayout.backgroundColor = Color.LTGRAY

        //      Generates [A, AA, AAA, B, BB, BBB, ...]
        val list = ('A'..'F').flatMap { char ->
            (1..3).map { nbChar ->
                (List(nbChar) { char }).joinToString(separator = "")
            }
        }


        list.forEach { content ->
            prepareView<TextView>(tag = content) {
                backgroundColor = randomColor()

                padding = 16.dp
                text = content
                tag = content
            }
        }

        return@eLayout EEmptyLayout
    }
}
