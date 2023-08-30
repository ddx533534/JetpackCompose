package com.ddx.compose.model

import java.io.Serializable

data class User(var username: String, var status: UserStatus, var expireTime: Int) :
    Serializable

enum class UserStatus{
    ONLINE,
    OFFLINE,
    UNKNOWN
}