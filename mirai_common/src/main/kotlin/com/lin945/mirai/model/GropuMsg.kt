package com.lin945.mirai.model

data class GroupMessageSender @JvmOverloads constructor(
    var group: Long = -1L,
    override var bot: Number = -1L,
    override var target: Number = group,
    override var message: String = ""
) : AbstractMsgSender

data class GroupMessage @JvmOverloads constructor(
    var group: Long = -1L,
    override var type: Int = 2,
    override var bot: Number = -1L,
    override var message: String = "",
    override var sender: Number = -1L,
    override var senderName: String = "",
    override var time: Int = -1
) : AbstractMsg


data class GroupMemberJoinEvent @JvmOverloads constructor(
    var group: Long = -1L,
    override var bot: Number = -1L,
    override var eventId: Long = -1L,
    override var from: Number = group,
    override var message: String = ""
) : AbstractEventMsg