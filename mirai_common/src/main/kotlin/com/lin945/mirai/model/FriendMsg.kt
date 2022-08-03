package com.lin945.mirai.model

data class FriendMessageSender @JvmOverloads constructor(
    var friend: Long = -1L,
    override var bot: Number = -1L,
    override var target: Number = friend,
    override var message: String = ""
) : AbstractMsgSender

data class FriendMessage @JvmOverloads constructor(
    var friend: Long = -1L,
    override var type: Int = 1,
    override var bot: Number = -1L,
    override var message: String = "",
    override var sender: Number = friend,
    override var senderName: String = "",
    override var time: Int = -1
) : AbstractMsg

data class NewFriendAddMsg @JvmOverloads constructor(
    public val bot: Number,
    /**
     * 事件唯一识别号
     */
    public val eventId: Long,
    /**
     * 申请好友消息
     */
    public val message: String,
    /**
     * 请求人 [User.id]
     */
    public val fromId: Long,
    /**
     * 来自群 [Group.id], 其他途径时为 0
     */
    public val fromGroupId: Long,
    /**
     * 群名片或好友昵称
     */
    public val fromNick: String
)