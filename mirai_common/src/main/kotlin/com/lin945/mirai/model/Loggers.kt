package com.lin945.mirai.model

import org.slf4j.LoggerFactory

public inline fun <reified T> getLoggers() = LoggerFactory.getLogger(T::class.java)!!
