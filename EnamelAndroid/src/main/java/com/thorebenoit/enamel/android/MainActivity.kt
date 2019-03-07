package com.thorebenoit.enamel.android

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.thorebenoit.enamel.android.dsl.constraints.constraints
import com.thorebenoit.enamel.android.dsl.customView
import com.thorebenoit.enamel.android.dsl.enamelContext
import com.thorebenoit.enamel.android.dsl.views.*
import com.thorebenoit.enamel.android.elayout.ELayoutFrame
import com.thorebenoit.enamel.kotlin.core.color.*
import android.view.Gravity
import androidx.core.view.marginLeft
import com.thorebenoit.enamel.kotlin.core.math.random
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.figures.ESize
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream
import java.io.Serializable

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

        val s_lambda = byteArrayOf(
            -84,
            -19,
            0,
            5,
            115,
            114,
            0,
            51,
            99,
            111,
            109,
            46,
            116,
            104,
            111,
            114,
            101,
            98,
            101,
            110,
            111,
            105,
            116,
            46,
            101,
            110,
            97,
            109,
            101,
            108,
            46,
            97,
            110,
            100,
            114,
            111,
            105,
            100,
            46,
            84,
            101,
            115,
            116,
            75,
            116,
            36,
            109,
            97,
            105,
            110,
            36,
            108,
            97,
            109,
            98,
            100,
            97,
            36,
            49,
            69,
            -64,
            -30,
            124,
            3,
            -85,
            44,
            94,
            2,
            0,
            0,
            120,
            114,
            0,
            26,
            107,
            111,
            116,
            108,
            105,
            110,
            46,
            106,
            118,
            109,
            46,
            105,
            110,
            116,
            101,
            114,
            110,
            97,
            108,
            46,
            76,
            97,
            109,
            98,
            100,
            97,
            -111,
            78,
            26,
            -16,
            -49,
            -23,
            59,
            55,
            2,
            0,
            1,
            73,
            0,
            5,
            97,
            114,
            105,
            116,
            121,
            120,
            112,
            0,
            0,
            0,
            0
        )
        val ds_lambda = s_lambda.deserialize<() -> Unit>()
        ds_lambda()


        setContentView(
            frameLayout {
                customView<ELayoutFrame>()
                backgroundColor = black

//                (0 until 50).forEach {
//                    customView<ELayoutFrame>().frameLayoutLP {
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
