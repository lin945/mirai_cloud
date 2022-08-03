package com.lin945.mirai.controller

import com.lin945.mirai.api.MiraiApi
import com.lin945.mirai.mirai.MiraiConfiguration
import com.lin945.mirai.mirai.MiraiContainer
import com.lin945.mirai.model.FriendMessageSender
import com.lin945.mirai.model.GroupMessageSender
import com.lin945.mirai.model.getLoggers
import com.lin945.mirai.utils.runSuspend
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
open class MiraiController : MiraiApi {
    val log = getLoggers<MiraiController>()

    @Autowired
    @Qualifier("testTemplate")
    lateinit var rabbitTemplate: RabbitTemplate

    @Autowired
    lateinit var miraiConfiguration: MiraiConfiguration

    @Autowired
    lateinit var miraiContainer: MiraiContainer

    override fun sendGroupMessage(message: GroupMessageSender) {
        log.info("接收到rpc 消息 $message")
        runSuspend {
            miraiContainer.sendGroupMessage(message)
        }
    }

    override fun sendFriendMessage(message: FriendMessageSender) {
        log.info("接收到rpc信息 $message")
        runSuspend {

        }
    }

    @RequestMapping("/test")
    fun test(key: String? = "key", msg: String? = "msg", ex: String? = "ex"): String {
        println("发送---------------")
        rabbitTemplate.convertAndSend(ex!!, key!!, msg!!)
        return "ok"
    }

    @RequestMapping("/c")
    fun config(): String {
        return miraiConfiguration.bot?.toString() ?: ""
    }

    @RequestMapping("/login")
    public fun login(): String {
        runSuspend {
            miraiContainer.loginAll()
        }
        return "ok"
    }
}