package com.thorebenoit.enamel.android

import android.content.Context
import android.speech.tts.TextToSpeech
import com.thorebenoit.enamel.kotlin.core.backingfield.ExtraValueHolder
import com.thorebenoit.enamel.kotlin.threading.CoroutineLock
import com.thorebenoit.enamel.kotlin.threading.coroutine
import com.thorebenoit.enamel.kotlin.threading.notify

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
            coroutine {
                isInitLock.wait()
                speak(str)
            }
        }
    }

    fun stop() = speaker.stop()


}