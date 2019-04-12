package com.thorebenoit.enamel.android

import android.content.Context
import android.speech.tts.TextToSpeech
import com.thorebenoit.enamel.core.threading.CoroutineLock
import com.thorebenoit.enamel.kotlin.core.backingfield.ExtraValueHolder
import com.thorebenoit.enamel.core.threading.coroutine

val Context.speaker by ExtraValueHolder<Context, ESpeaker> { ESpeaker(this) }

class ESpeaker(val context: Context) {

    private var isInit: Boolean = false

    private val isInitLock = CoroutineLock()

    private val speaker by lazy {
        TextToSpeech(context, TextToSpeech.OnInitListener {
            isInit = true
            isInitLock.unlock()
        })
    }

    fun speak(str: String) {
        if (isInit) {
            speaker.speak(str, TextToSpeech.QUEUE_FLUSH, null)
        } else {
            speaker // init lazy
            coroutine {
                isInitLock.wait()
                speak(str)
            }
        }
    }

    fun stop() = speaker.stop()


}