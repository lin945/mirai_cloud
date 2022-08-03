package com.lin945.mirai.mirai

import com.lin945.mirai.model.GroupMessageSender
import com.lin945.mirai.model.UserBot
import com.lin945.mirai.mq.MqService
import net.mamoe.mirai.Bot
import net.mamoe.mirai.BotFactory
import net.mamoe.mirai.utils.BotConfiguration
import net.mamoe.mirai.utils.LoggerAdapters.asMiraiLogger
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.ContextRefreshedEvent
import java.io.File

@Configuration
@ConfigurationProperties(prefix = "mirai.config")
@RefreshScope
open class MiraiConfiguration : InitializingBean, ApplicationListener<ContextRefreshedEvent> {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    /**
     * 用户配置属性
     */
    public var bot: List<UserBot>? = null

    var state: String? = null


    var filePath: String = "deviceInfo.json"


    var miraiMessageDispatcher = MiraiMessageDispatcher

    @Autowired
    lateinit var miraiContainer: MiraiContainer

    @Autowired
    lateinit var mqService: MqService

    /**
     * spring属性填填充后
     */
    override fun afterPropertiesSet() {
        miraiMessageDispatcher.mqService=mqService
        bot?.let { refreshBot() }
    }

    /**
     * 刷新当前机器人
     */
    fun refreshBot() {
        if (bot == null) log.error("没有找到机器人配置！")
        miraiContainer.closeAll()
        initConfig()
    }

    fun initConfig() {
        miraiContainer.initBot {
            bot?.filter { it.qq != -1L && it.pass.isNotBlank() }?.forEach {
                val login = BotFactory.newBot(it.qq, it.pass) {
                    fileBasedDeviceInfo(filePath)
                    protocol = BotConfiguration.MiraiProtocol.ANDROID_PHONE
                    botLoggerSupplier = { it1 ->
                        LoggerFactory.getLogger(it1::class.java).asMiraiLogger()
                    }
                    networkLoggerSupplier = { it ->
                        LoggerFactory.getLogger(it::class.java).asMiraiLogger()
                    }
                }.apply { eventChannel.registerListenerHost(miraiMessageDispatcher) }
              put(it.qq,login)
            }
        }
    }


    /**
     * 上下文刷新事件
     */
    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        println("ContextRefreshedEvent   ->    $bot")
    }

}
