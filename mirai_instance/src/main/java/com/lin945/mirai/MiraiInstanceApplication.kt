package com.lin945.mirai

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
open class MiraiInstanceApplication {

}

fun main(vararg args: String) {
    runApplication<MiraiInstanceApplication>(*args)
}