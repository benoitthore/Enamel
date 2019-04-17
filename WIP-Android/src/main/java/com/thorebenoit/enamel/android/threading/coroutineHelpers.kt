package com.thorebenoit.enamel.android.threading

import com.thorebenoit.enamel.core.threading.coroutine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun mainThreadCoroutine(block: suspend CoroutineScope.() -> Unit) = GlobalScope.launch(context = Dispatchers.Main,block = block)



class AndroidCoroutineRunner : CoroutineRunner {
    override fun onMainThread(block: suspend CoroutineScope.() -> Unit) {
        mainThreadCoroutine(block)
    }

    override fun onIO(block: suspend CoroutineScope.() -> Unit) {
        coroutine(block)
    }

}

interface CoroutineRunner {
    fun onMainThread(block: suspend CoroutineScope.() -> Unit)
    fun onIO(block: suspend CoroutineScope.() -> Unit)
}