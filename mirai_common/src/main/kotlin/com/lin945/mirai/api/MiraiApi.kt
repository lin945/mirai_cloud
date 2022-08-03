package com.lin945.mirai.api

import com.lin945.mirai.model.FriendMessageSender
import com.lin945.mirai.model.GroupMessageSender
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

interface MiraiApi {

    @PostMapping("/message/group")
    public fun sendGroupMessage(@RequestBody message: GroupMessageSender)

    @PostMapping("/message/friend")
    public fun sendFriendMessage(@RequestBody message: FriendMessageSender)
}