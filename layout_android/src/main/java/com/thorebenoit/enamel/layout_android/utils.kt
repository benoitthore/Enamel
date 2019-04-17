package com.thorebenoit.enamel.layout_android

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

internal fun mainThreadCoroutine(block: suspend CoroutineScope.() -> Unit) = GlobalScope.launch(context = Dispatchers.Main,block = block)