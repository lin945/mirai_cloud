package com.lin945.mirai.mq

import com.lin945.mirai.model.toJsonString
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class MqService {
    @Autowired
    lateinit var rabbitTemplate: RabbitTemplate

    public fun sendMqMessage(exchange: String, routingKey: String, data: Any) {
        rabbitTemplate.convertAndSend(exchange, routingKey, data.toJsonString()){
            it.also { it.messageProperties.expiration="3000"}
        }
    }


}