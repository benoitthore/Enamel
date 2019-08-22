package com.benoitthore.enamel.android.examples

import android.content.Context
import android.graphics.Color
import android.widget.TextView
import com.benoitthore.enamel.android.randomColor
import com.benoitthore.enamel.geometry.alignement.EAlignment.*
import com.benoitthore.enamel.geometry.layout.dsl.*
import com.benoitthore.enamel.layout.android.EViewGroup
import com.benoitthore.enamel.layout.android.dp
import com.benoitthore.enamel.layout.android.eViewGroup
import splitties.views.backgroundColor
import splitties.views.padding


/* KTS
import com.benoitthore.enamel.geometry.layout.dsl.*
import com.benoitthore.enamel.geometry.alignement.*
import com.benoitthore.enamel.geometry.alignement.EAlignment.*
import com.benoitthore.enamel.geometry.alignement.ERectEdge.*
import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.geometry.layout.playground.sendToPlayground

val Number.dp get() = toFloat() * 3


//val ELayout.leaf get() = leaf(randomColor())

val list = ('A'..'F').flatMap { char ->
    (1..3).map { nbChar ->
        (List(nbChar) { char }).joinToString(separator = "")
    }
}

val leftLayout = "AAA".layoutTag
val centerLayout = "BBB".layoutTag
val rightLayout = "CCC".layoutTag


centerLayout.spreadBetween(leftLayout, rightLayout)
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
