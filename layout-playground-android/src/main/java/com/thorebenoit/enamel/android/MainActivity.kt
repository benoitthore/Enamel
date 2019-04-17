package com.thorebenoit.enamel.android

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thorebenoit.enamel.android.dsl.customView
import com.thorebenoit.enamel.android.dsl.views.*
import com.thorebenoit.enamel.layout_android.EViewGroup
import com.thorebenoit.enamel.kotlin.core.color.*
import com.thorebenoit.enamel.geometry.figures.ESizeMutable
import android.net.wifi.WifiManager
import com.thorebenoit.enamel.android.dsl.withTag
import com.thorebenoit.enamel.layout_android.laidIn
import com.thorebenoit.enamel.layout_android.startServer
import com.thorebenoit.enamel.geometry.layout.ELayout
import com.thorebenoit.enamel.geometry.layout.dsl.*
//
import com.thorebenoit.enamel.geometry.alignement.EAlignment.*
import com.thorebenoit.enamel.geometry.alignement.ERectEdge.*
//

private var screenSizeBuffer = ESizeMutable()
private var screenSizePointBuffer = Point()

val Activity.screenSize
    get() = with(screenSizePointBuffer) {
        windowManager.defaultDisplay.getSize(this)
        screenSizeBuffer.set(x, y)
    }

fun Context.eLayout(block: EViewGroup.() -> ELayout): EViewGroup = EViewGroup(
    this
).apply {
    layout = block()
}

val Number.dp: Int
    get() = (toFloat() * Resources.getSystem().displayMetrics.density).toInt()

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FIX_ME()
//        view1()
    }

    private fun FIX_ME() {
        val EL1 = eLayout {
            val viewgroup = this
            val tv1 = context.textView("Text1") {
                backgroundColor = green
                padding = 16.dp
            }
                .withTag("tv1")
            val tv2 = context.textView("Some other text")
            {
                backgroundColor = green
                padding = 16.dp
            }
                .withTag("tv2")


            backgroundColor = blue.withAlpha(0.25)

            val layout = listOf(tv1, tv2).laidIn(viewgroup)
                .stackedBottomRight(10.dp)
                .snugged()
                .arranged(middle)


            layout
        }.withTag("layout1")

        val EL2 = eLayout {
            val tv = context.textView("ABCDEF") {
                textSize = 26f
            }

            backgroundColor = red.withAlpha(0.5)

            tv.laidIn(this).arranged(middle)
        }.withTag("layout2")


        setContentView(
            eLayout {
                startServer()
                backgroundColor = dkGray
                listOf(EL1, EL2).laidIn(this).justified(bottomRight).arranged(topRight)
            }
        )
    }

    private fun view1() {
        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        val ip = wifiInfo.ipAddress
        val ipString = String.format(
            "%d.%d.%d.%d",
            ip and 0xff,
            ip shr 8 and 0xff,
            ip shr 16 and 0xff,
            ip shr 24 and 0xff
        )


        setContentView(
            verticalLayout {
                textView(ipString) {
                    textSize = 33f
                    backgroundColor = black
                    textColor = white
                }.linearLayoutLP(wrapContent, wrapContent)

                customView<EViewGroup> {
                    startServer()

                    layout =
                        listOf(
                            "B".layoutTag,
                            "A".layoutTag
                        ).stacked(bottomCenter).arranged(topRight)


                    backgroundColor = dkGray
                }.linearLayoutLP {
                    height = 0
                    weight = 1f
                }

//                customView<EViewGroup> {
//                    backgroundColor = red
//                    goToLayout(
//                        listOf(
//                            "B".layoutTag,
//                            "A".layoutTag
//                        ).stacked(bottomCenter).arranged(topRight)
//                    )
//                }.linearLayoutLP {
//                    height = 0
//                    weight = 1f
//                }


//                customView<EViewGroup>() {
//                    backgroundColor = ltGray
//                    startServer()
//                    post {
//                        goToLayout("A".layoutTag.arranged(topRight))
//                    }
//                }.linearLayoutLP {
//                    height = 0
//                    weight = 1f
//                }
                backgroundColor = black


//                (0 until 50).forEach {
//                    customView<EViewGroup>().frameLayoutLP {
//                        width = random(0, screenSize.width).toInt()
//                        height = random(0, screenSize.height).toInt()
//
//                        setMargins(
//                            random(-screenSize.width / 2, screenSize.width).toInt(),
//                            random(-screenSize.height / 2, screenSize.height).toInt(),
//                            0,
//                            0
//                        )
//                    }
//                }
            }
        )
    }
}