package com.lin945.mirai.mirai

import com.lin945.mirai.model.GroupMessage
import com.lin945.mirai.model.MqExchange
import com.lin945.mirai.model.MqInfo
import com.lin945.mirai.mq.MqService
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.SimpleListenerHost
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.event.events.MemberJoinRequestEvent
import net.mamoe.mirai.event.events.NewFriendRequestEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import kotlin.coroutines.CoroutineContext


object MiraiMessageDispatcher : SimpleListenerHost() {
    val log: Logger = LoggerFactory.getLogger(this.javaClass)

    lateinit var mqService: MqService

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        super.handleException(context, exception)
    }

    @EventHandler
    suspend fun GroupMessageEvent.onMessage() {
        log.info(message.contentToString())
        mqService.sendMqMessage(MqInfo.GroupInfo.exchange,MqInfo.GroupInfo.key,toGroupMessage())
//        rabbitTemplate.convertAndSend()
    }

    private fun GroupMessageEvent.toGroupMessage(): GroupMessage {
        return GroupMessage(
            group = group.id,
            message = message.contentToString(),
            bot = bot.id, sender = sender.id,
            senderName = senderName,
            time = time
        )
    }

    @EventHandler
    suspend fun FriendMessageEvent.onMessage() {
        sender.sendMessage("hello")
        log.info(message.contentToString())
    }

    @EventHandler
    suspend fun NewFriendRequestEvent.onMessage() {
        accept()
    }

    @EventHandler
    suspend fun MemberJoinRequestEvent.onMessage() {
        accept()
    }
}