package com.lin945.mirai.mirai

import com.lin945.mirai.model.FriendMessageSender
import com.lin945.mirai.model.GroupMessageSender
import com.lin945.mirai.model.getLoggers
import com.lin945.mirai.utils.runSuspend
import net.mamoe.mirai.Bot
import org.springframework.stereotype.Component

@Component
open class MiraiContainer {
    val log = getLoggers<MiraiContainer>()

    /**
     * bot实例
     */
    val instances = HashMap<Long, Bot>()


    /**
     * 获取bot执行
     */
    public fun runWithBot(bot: Long, block: suspend Bot.() -> Unit) {
        runSuspend {
            instances[bot]?.apply { block() } ?: log.error("没有bot实例 ：$bot")
        }
    }

    /**
     * 登录所有机器人
     */
    public suspend fun loginAll() {
        for (instance in instances) {
            log.info("try to login ${instance.key}")
            instance.value.login()
        }
    }

    public fun initBot(bots: HashMap<Long, Bot>.() -> Unit) {
        instances.apply(bots)
    }


    /**
     * 关闭并清空所有机器人实例
     */
    public fun closeAll() {
        if (instances.isNotEmpty()) {
            instances.forEach { (_, u) -> if (u.isOnline) u.close() }
            log.info("清除现有bot: $instances")
            instances.clear()
        }
    }

    /**
     * 发送群消息
     */
    public suspend fun sendGroupMessage(message: GroupMessageSender): Boolean {
        instances[message.group]?.apply {
            getGroup(message.group)?.let {
                return it.sendMessage(message.message).also { log.info("send message ${message.message}") }.isToGroup
            }
        }
        return false
    }

    /**
     * 发送好友消息
     */
    public suspend fun sendFriendMessage(message: FriendMessageSender) {
        instances[message.bot]?.apply {
            getFriend(message.friend)?.sendMessage(message.message)
        }
    }
}