package com.lin945.mirai.config

import com.lin945.mirai.model.FriendMessage
import com.lin945.mirai.model.GroupMessage
import com.lin945.mirai.utils.LimitedQueue
import org.springframework.amqp.core.AcknowledgeMode
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.amqp.RabbitProperties
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class MqConfig {

    @Bean("groups")
    public fun groups(): LimitedQueue<GroupMessage> {
        return LimitedQueue<GroupMessage>(10)
    }

    @Bean("friends")
    public fun friends(): LimitedQueue<FriendMessage> {
        return LimitedQueue<FriendMessage>(10)
    }

    @Autowired
    lateinit var rabbitProperties: RabbitProperties

    @Bean(name = ["connectFactory"])
    open fun connectFactory(): ConnectionFactory {
        val connectionFactory = CachingConnectionFactory()
        connectionFactory.host = rabbitProperties.host
        connectionFactory.port = rabbitProperties.port
        connectionFactory.username = rabbitProperties.username
        connectionFactory.setPassword(rabbitProperties.password)
        connectionFactory.virtualHost = "lin945"
        return connectionFactory
    }

    @Bean(name = ["testTemplate"])
    open fun orderSummaryAmqpTemplate(@Qualifier("connectFactory") connectionFactory: ConnectionFactory): RabbitTemplate {
        return RabbitTemplate(connectionFactory)
    }

    @Bean(name = ["listenerContainer"])
    open fun listenerContainer(
        @Qualifier("connectFactory") connectionFactory: ConnectionFactory,
        configurer: SimpleRabbitListenerContainerFactoryConfigurer
    ): SimpleRabbitListenerContainerFactory {
        return SimpleRabbitListenerContainerFactory().apply {
            setConnectionFactory(connectionFactory)
            setAcknowledgeMode(AcknowledgeMode.AUTO)  // 手动确认模式
            configurer.configure(this, connectionFactory)
        }
    }
}