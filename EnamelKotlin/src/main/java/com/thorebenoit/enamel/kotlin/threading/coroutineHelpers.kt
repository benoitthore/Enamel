package com.thorebenoit.enamel.kotlin.threading

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun coroutine(block: suspend CoroutineScope.()->Unit) = GlobalScope.launch(block = block)
