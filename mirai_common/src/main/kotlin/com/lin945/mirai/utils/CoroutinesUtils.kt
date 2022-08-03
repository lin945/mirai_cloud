package com.lin945.mirai.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

public inline fun runSuspend(crossinline block: suspend CoroutineScope.() -> Unit) = GlobalScope.launch { block() }