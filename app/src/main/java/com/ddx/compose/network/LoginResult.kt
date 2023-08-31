package com.ddx.compose.network

import com.ddx.compose.model.User
import com.ddx.compose.model.UserStatus
import java.io.Serializable

data class LoginResult(var code: Int, var user: User) :
    Serializable

data class RegisterResult(var code: Int, var user: User) :
    Serializable

data class MainResult(var code: Int) :
    Serializable