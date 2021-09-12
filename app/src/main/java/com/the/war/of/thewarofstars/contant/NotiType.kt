package com.the.war.of.thewarofstars.contant

enum class NotiType(val type: String) {
    // message type
    CHATTING("CHATTING"),
    PAY("PAY"),
}

enum class NotiInfo(val type: String) {
    // message type
    NOTI_TYPE("notiType"),
    SENDER_UID("senderUID"),
    SENDER_NAME("senderName"),
    SENDER_TYPE("senderType"),
}