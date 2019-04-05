package com.thorebenoit.enamel.android

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thorebenoit.enamel.android.dsl.customView
import com.thorebenoit.enamel.android.dsl.views.*
import com.thorebenoit.enamel.android.elayout.EDroidLayout
import com.thorebenoit.enamel.kotlin.core.color.*
import com.thorebenoit.enamel.kotlin.core.math.random
import com.thorebenoit.enamel.kotlin.geometry.figures.ESize
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import androidx.core.view.postDelayed
import com.thorebenoit.enamel.android.dsl.contextConstructor
import com.thorebenoit.enamel.android.dsl.withID
import com.thorebenoit.enamel.android.dsl.withTag
import com.thorebenoit.enamel.android.elayout.laidIn
import com.thorebenoit.enamel.android.elayout.startServer
import com.thorebenoit.enamel.android.threading.mainThreadCoroutine
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment.*
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayoutLeaf
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.*
import kotlinx.coroutines.delay


private var screenSizeBuffer = ESize()
private var screenSizePointBuffer = Point()

val Activity.screenSize
    get() = with(screenSizePointBuffer) {
        windowManager.defaultDisplay.getSize(this)
        screenSizeBuffer.set(x, y)
    }

fun Context.eLayout(block: EDroidLayout.() -> ELayout): EDroidLayout = EDroidLayout(this).apply {
    goToLayout(block())
}

val Number.dp: Int
    get() = (toFloat() * Resources.getSystem().displayMetrics.density).toInt()

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FIX_ME()

    }

    private fun FIX_ME() {
        val EL1 = eLayout {
            val viewgroup = this
            val tv1 = context.textView("Text1") {
                backgroundColor = randomColor()
            }
                .withTag("tv1")
            val tv2 = context.textView("Some other text")
            {
                backgroundColor = randomColor()
            }
                .withTag("tv2")


            backgroundColor = dkGray

            val layout = listOf(tv1, tv2).laidIn(viewgroup)
                .stackedBottomRight(10.dp)
                .arranged(topRight)


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
                postDelayed(1000L) {
                    goToLayout(
                        listOf(EL1, EL2).laidIn(this).justified(bottomRight).arranged(topRight)
                    )
                }
                listOf(EL1, EL2).laidIn(this).map { it.sizedSquare(1) }.justified(bottomRight).arranged(topRight)
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

                customView<EDroidLayout> {
                    startServer()

                    goToLayout(
                        listOf(
                            "B".layoutTag,
                            "A".layoutTag
                        ).stacked(bottomCenter).arranged(topRight)
                    )

                    backgroundColor = dkGray
                }.linearLayoutLP {
                    height = 0
                    weight = 1f
                }

//                customView<EDroidLayout> {
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


//                customView<EDroidLayout>() {
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
//                    customView<EDroidLayout>().frameLayoutLP {
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