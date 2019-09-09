package com.benoitthore.enamel.android.examples

import android.content.Context
import android.graphics.Color
import android.widget.TextView
import com.benoitthore.enamel.android.randomColor
import com.benoitthore.enamel.geometry.alignement.EAlignment.*
import com.benoitthore.enamel.geometry.layout.dsl.*
import com.benoitthore.enamel.geometry.layout.playground.PlaygroundServer
import com.benoitthore.enamel.layout.android.EViewGroup
import com.benoitthore.enamel.layout.android.dp
import com.benoitthore.enamel.layout.android.eViewGroup
import com.benoitthore.enamel.layout.android.startServer
import splitties.views.backgroundColor
import splitties.views.padding


/* KTS
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

fun ELayoutTestingView(
    context: Context,
    startServer: Boolean = false,
    serverPort: Int = PlaygroundServer.defaultPort
): EViewGroup {
    return context.eViewGroup {

        this@eViewGroup.backgroundColor = Color.LTGRAY

        //      Generates [A, AA, AAA, B, BB, BBB, ...]
        val list = ('A'..'F').flatMap { char ->
            (1..3).map { nbChar ->
                (List(nbChar) { char }).joinToString(separator = "")
            }
        }


        list.forEach { layoutTag ->
            prepareView<TextView>(tag = layoutTag) {
                backgroundColor = randomColor()

                padding = 16.dp
                text = layoutTag
                tag = layoutTag
            }
        }



        return@eViewGroup list
            .map { it.layoutTag }
            .stacked(bottomCenter, spacing = 16.dp).snugged()
            .padded(16.dp)
            .arranged(topLeft)
    }.also { viewGroup ->
        if (startServer) {
            viewGroup.startServer(serverPort)
        }
    }
}
