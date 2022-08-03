package com.lin945.mirai

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
open class MiraiConsumerApplication {
}

fun main(vararg args: String) {
    runApplication<MiraiConsumerApplication>(*args)
}