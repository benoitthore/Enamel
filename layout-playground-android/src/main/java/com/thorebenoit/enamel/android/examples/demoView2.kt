package com.thorebenoit.enamel.android.examples

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import com.thorebenoit.enamel.android.dp
import com.thorebenoit.enamel.android.eLayout
import com.thorebenoit.enamel.geometry.alignement.EAlignment.*
import com.thorebenoit.enamel.geometry.alignement.ERectEdge.*
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

val layout1 = "layout1".layoutTag
val layout2 = "layout2".layoutTag

layout1
    .aligned(left, of = layout2, sizedBy = slice(), snugged = true)
    .padded(16.dp)
    .arranged(topLeft)

    .sendToPlayground()
 */
fun Context.demoView2(): EViewGroup {
    val EL1 = eLayout {
        val viewgroup = this
        val tv1 = textView {

            text = "Text1"
            backgroundColor = Color.GREEN
            padding = 16.dp
        }
            .withTag("tv1")
        val tv2 = textView {
            text = "Some other text"
            backgroundColor = Color.GREEN
            padding = 16.dp
        }
            .withTag("tv2")


        backgroundColor = Color.argb(255 / 4, 0, 0, 255)

        val layout = listOf(tv1, tv2).laidIn(viewgroup)
            .stackedBottomRight(10.dp)
            .snugged()
            .arranged(middle)


        layout
    }.withTag("layout1")

    val EL2 = eLayout {
        val tv = textView {
            text = "ABCDEF"
            textSize = 26f
        }

        backgroundColor = Color.argb(255 / 4, 255, 0, 0)

        tv.laidIn(this).arranged(middle)
    }.withTag("layout2")

    return eLayout {
        backgroundColor = Color.DKGRAY
        listOf(EL1, EL2).laidIn(this).justified(bottomRight).arranged(topRight)
    }
}