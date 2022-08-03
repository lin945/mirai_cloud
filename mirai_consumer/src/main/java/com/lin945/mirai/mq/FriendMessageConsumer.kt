package com.lin945.mirai.mq

import com.lin945.mirai.model.*
import com.lin945.mirai.rpc.MiraiMessageApi
import com.lin945.mirai.utils.LimitedQueue
import com.rabbitmq.client.Channel
import org.springframework.amqp.core.ExchangeTypes
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.handler.annotation.Payload

@Configuration
class FriendMessageConsumer {

    val log= getLoggers<FriendMessageConsumer>()
    @Autowired
    lateinit var miraiApi: MiraiMessageApi

    @Autowired
    @Qualifier("friends")
    lateinit var friends: LimitedQueue<FriendMessage>
    /**
     * 处理好友消息
     */
    @RabbitListener(
        bindings = [QueueBinding(
            value = Queue(
                name = MqInfo.FriendInfo.queue,
                arguments = [
                    Argument(name = "x-dead-letter-exchange", value = MqInfo.FriendInfo.dead_exchange),
                    Argument(name = "x-dead-letter-routing-key", value = MqInfo.FriendInfo.dead_key)
                ]
            ),
            exchange = Exchange(name = MqInfo.FriendInfo.exchange, type = ExchangeTypes.DIRECT),
            key = [MqInfo.FriendInfo.key]
        )],
        containerFactory = "listenerContainer",
    )
    open fun consume(@Payload msg: String, channel: Channel, message: Message) {
        //println("收到 GroupMsg---------------$msg")
        val data = convertJsonTo<FriendMessage>(msg)
        friends.add(data)
        log.info(data.toString())
    }

    /**
     * 死信列表
     */

    @RabbitListener(
        bindings = [QueueBinding(
            value = Queue(name = MqInfo.FriendInfo.dead_queue),
            exchange = Exchange(name =MqInfo.FriendInfo.dead_exchange, type = ExchangeTypes.DIRECT),
            key = [MqInfo.FriendInfo.dead_key]
        )],
        containerFactory = "listenerContainer",
    )
    open fun consumeDead(@Payload msg: String, channel: Channel, message: Message) {
        log.info("收到 死信息--------------- $msg")

    }
}