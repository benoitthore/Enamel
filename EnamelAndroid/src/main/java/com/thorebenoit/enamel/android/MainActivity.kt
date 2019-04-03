package com.thorebenoit.enamel.android

import android.app.Activity
import android.content.Context
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
import com.thorebenoit.enamel.android.elayout.startServer
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.arranged
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.layoutTag
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.stacked


private var screenSizeBuffer = ESize()
private var screenSizePointBuffer = Point()

val Activity.screenSize
    get() = with(screenSizePointBuffer) {
        windowManager.defaultDisplay.getSize(this)
        screenSizeBuffer.set(x, y)
    }

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

                customView<EDroidLayout>() {
                    startServer()
                    backgroundColor = dkGray
                    post {
                        goToLayout(listOf("B".layoutTag, "A".layoutTag).stacked(EAlignment.bottomCenter))
                    }
                }.linearLayoutLP {
                    height = 0
                    weight = 1f
                }


//                customView<EDroidLayout>() {
//                    backgroundColor = ltGray
//                    startServer()
//                    post {
//                        goToLayout("A".layoutTag.arranged(EAlignment.topRight))
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