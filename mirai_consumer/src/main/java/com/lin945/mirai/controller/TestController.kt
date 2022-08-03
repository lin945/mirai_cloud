package com.lin945.mirai.controller

import com.lin945.mirai.model.GroupMessage
import com.lin945.mirai.model.GroupMessageSender
import com.lin945.mirai.model.toJsonString
import com.lin945.mirai.rpc.MiraiMessageApi
import com.lin945.mirai.utils.LimitedQueue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @Autowired
    lateinit var miraiApi: MiraiMessageApi
    @Autowired
    @Qualifier("groups")
    lateinit var groups: LimitedQueue<GroupMessage>

    @RequestMapping("/test")
    public fun test() {
        miraiApi.sendGroupMessage(GroupMessageSender(group = 666))
    }

    @RequestMapping("/messages")
    public fun getMeesages(): String {
        return groups.toJsonString()
    }

}


