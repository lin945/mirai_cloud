package com.lin945.mirai

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import java.io.File

@SpringBootTest
class MIraiTest {
    @Test
    public fun test1() {

    }
}

val log = LoggerFactory.getLogger(MIraiTest::class.java)
fun main() {
//    println("{\"time\":\"aaa\",\"group\":\"111\",\"message\":\"aaa\"}".convertTo<GroupMessage>())
    println(File(".").absolutePath)
    GlobalScope.launch {
        launch { log.info("66") }
        async {
            log.info("async")
            delay(1000)
        }
        log.info("222")
    }

    log.info("555")
}