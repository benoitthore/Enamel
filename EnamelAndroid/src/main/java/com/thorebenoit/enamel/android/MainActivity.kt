package com.thorebenoit.enamel.android

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.appcompat.app.AppCompatActivity
import com.thorebenoit.enamel.android.dsl.constraints.constraints
import com.thorebenoit.enamel.android.dsl.enamelContext
import com.thorebenoit.enamel.android.dsl.views.button
import com.thorebenoit.enamel.android.dsl.views.constraintLayout
import com.thorebenoit.enamel.android.dsl.views.editText
import com.thorebenoit.enamel.android.dsl.views.textView

class MainActivity : AppCompatActivity() {

    lateinit var speaker: TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        speaker = TextToSpeech(this, TextToSpeech.OnInitListener {
            
        })

        val v = enamelContext {
            constraintLayout {
                val input = editText("Type something")
                val button = button("Ok") {
                    setOnClickListener {
                        speaker.speak(input.text.toString(), TextToSpeech.QUEUE_FLUSH, null)
                    }
                }

                constraints {
                    val list = listOf(button, input)
                    list.buildChain {
                        vertical()
                        packed()
                        defaultMargin = 16.dp
                    }

                    list.forEach { it.centerHorizontallyIn(parentId) }
                }
            }
        }

        setContentView(v)

    }
}
