package com.lin945.mirai.model

public enum class MqExchange(val exchange:String){
    GROUP_EX("mirai.group"),
    FRIEND_EX("mirai.friend"),
    ORTHER_EX("mirai.orther")
}

sealed class MqInfo {
    class GroupInfo{
        companion object{
            const val exchange: String = "mirai.group"
            const val key: String = "mirai.group"
            const val queue: String = "mirai.message.group"

            const val dead_exchange="mirai.group.death"
            const val dead_key="mirai.group.death_key"
            const val dead_queue="mirai.message.group.death"
        }
    }
    class FriendInfo{
        companion object{
            const val exchange: String = "mirai.friend"
            const val key: String = "mirai.friend"
            const val queue: String = "mirai.message.friend"

            const val dead_exchange="mirai.friend.death"
            const val dead_key="mirai.friend.death_key"
            const val dead_queue="mirai.message.friend.death"
        }
    }
}