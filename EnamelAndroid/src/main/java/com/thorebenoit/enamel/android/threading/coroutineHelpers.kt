package com.thorebenoit.enamel.android.threading

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun mainThreadCoroutine(block: suspend CoroutineScope.() -> Unit) = GlobalScope.launch(context = Dispatchers.Main,block = block)