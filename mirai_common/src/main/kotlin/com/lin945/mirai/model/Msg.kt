package com.lin945.mirai.model


interface Msg

public interface AbstractEventMsg :Msg{
    var bot: Number
    /**
     * 事件唯一识别号
     */
    var eventId: Long
    /**
     * 入群申请消息
     */
    var message: String

    /**
     * 来源
     */
    var from:Number

}
public interface AbstractMsg :Msg{
    var bot: Number
    var sender: Number
    var senderName: String
    var message: String
    var time: Int
    var type: Int
}

public interface AbstractMsgSender :Msg{
    var bot: Number
    var target: Number
    var message: String
}




