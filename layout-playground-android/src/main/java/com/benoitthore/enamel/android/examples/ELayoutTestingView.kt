package com.benoitthore.enamel.android.examples

import android.content.Context
import android.graphics.Color
import com.benoitthore.enamel.android.randomColor
import com.benoitthore.enamel.geometry.alignement.EAlignment.*
import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.geometry.layout.ELayoutLeaf
import com.benoitthore.enamel.geometry.layout.dsl.*
import com.benoitthore.enamel.geometry.layout.playground.PlaygroundServer
import com.benoitthore.enamel.geometry.layout.refs.ELayoutTag
import com.benoitthore.enamel.geometry.layout.refs.getAllChildrenWithType
import com.benoitthore.enamel.geometry.layout.refs.getLeaves
import com.benoitthore.enamel.layout.android.*
import splitties.views.backgroundColor
import splitties.views.dsl.core.textView
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
    //      Generates [A, AA, AAA, B, BB, BBB, ...]
    val textViewList = ('A'..'F').flatMap { char ->
        (1..3).map { nbChar ->
            (List(nbChar) { char }).joinToString(separator = "")
        }
    }
        .map { layoutTag ->
            context.textView {
                backgroundColor = randomColor()

                padding = 16.dp
                text = layoutTag
                tag = layoutTag
            }
                .withTag(layoutTag)

        }


    val viewGroup = context.eViewGroup {

        this@eViewGroup.backgroundColor = Color.LTGRAY

        textViewList.laid()
            .stacked(bottomCenter, spacing = 16.dp).snugged()
            .padded(16.dp)
            .arranged(topLeft)
    }

    fun refFromTag(tag: String): ELayout =
        textViewList.firstOrNull { it.tag == tag }?.laidIn(viewGroup)
            ?: throw Exception("Unknown tag $tag")


    if (startServer) {
        viewGroup.startServer(serverPort) { layout ->

            layout.getAllChildrenWithType<ELayoutLeaf>().forEach { leaf ->
                (leaf.child as? ELayoutTag)?.let {
                    leaf.child = refFromTag(it.tag)
                }
            }

            layout
        }
    }

    return viewGroup
}
