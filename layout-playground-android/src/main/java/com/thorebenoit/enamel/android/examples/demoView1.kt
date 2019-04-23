package com.thorebenoit.enamel.android.examples

import android.content.Context
import android.graphics.Color
import android.widget.TextView
import com.thorebenoit.enamel.android.dp
import com.thorebenoit.enamel.android.eViewGroup
import com.thorebenoit.enamel.android.randomColor
import com.thorebenoit.enamel.geometry.alignement.EAlignment.*
import com.thorebenoit.enamel.geometry.alignement.ERectEdge.*
import com.thorebenoit.enamel.geometry.layout.EEmptyLayout
import com.thorebenoit.enamel.geometry.layout.dsl.*
import com.thorebenoit.enamel.layout_android.EViewGroup
import splitties.views.backgroundColor
import splitties.views.padding


/* KTS
import com.thorebenoit.enamel.geometry.layout.dsl.*
import com.thorebenoit.enamel.geometry.alignement.*
import com.thorebenoit.enamel.geometry.alignement.EAlignment.*
import com.thorebenoit.enamel.geometry.alignement.ERectEdge.*
import com.thorebenoit.enamel.geometry.layout.ELayout
import com.thorebenoit.enamel.geometry.layout.playground.sendToPlayground
import com.thorebenoit.enamel.kotlin.core.color.*

val Number.dp get() = toFloat() * 3


val ELayout.leaf get() = leaf(randomColor().withAlpha(0.25))

val list = ('A'..'F').flatMap { char ->
    (1..3).map { nbChar ->
        (List(nbChar) { char }).joinToString(separator = "")
    }
}

val leftLayout = "AAA".layoutTag
val centerLayout = "BBB".layoutTag
val rightLayout = "CCC".layoutTag


centerLayout.surroundedBy(leftLayout, rightLayout)
    .arranged(topLeft)
    .sendToPlayground()
*/

fun Context.demoView1(): EViewGroup {
    return eViewGroup {

        this@eViewGroup.backgroundColor = Color.LTGRAY

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



        return@eViewGroup list
            .map { it.layoutTag }
            .stacked(bottomCenter, spacing = 16.dp).snugged()
            .padded(16.dp)
            .arranged(topLeft)
    }
}
