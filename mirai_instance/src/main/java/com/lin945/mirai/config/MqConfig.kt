package com.lin945.mirai.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.amqp.RabbitProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class MqConfig {

    @Autowired
    lateinit var RabbitProperties: RabbitProperties

    @Bean(name = ["connectFactory"])
    fun connectFactory(): ConnectionFactory {
        val connectionFactory = CachingConnectionFactory()
        connectionFactory.host = RabbitProperties.host
        connectionFactory.port = RabbitProperties.port
        connectionFactory.username = RabbitProperties.username
        connectionFactory.setPassword(RabbitProperties.password)
        connectionFactory.virtualHost = "lin945"
        return connectionFactory
    }

    @Bean(name = ["testTemplate"])
    fun orderSummaryAmqpTemplate(@Qualifier("connectFactory") connectionFactory: ConnectionFactory): RabbitTemplate {
        return RabbitTemplate(connectionFactory)
    }

}