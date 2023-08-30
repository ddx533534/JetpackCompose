package com.ddx.compose.model;

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ddx.compose.base.LoginCode
import com.ddx.compose.network.LoginResult
import com.ddx.compose.network.Network
import com.ddx.compose.network.RegisterResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserModel : ViewModel() {
    val defaultUser = User("ddx", UserStatus.OFFLINE, 0)
    private val curUser = mutableStateOf<User>(defaultUser);
    val userLiveData: MutableLiveData<User> = MutableLiveData();

    suspend fun register(username: String, firstPassword: String, secondPassword: String) {
        Network.service.register(username, firstPassword, secondPassword)
            .enqueue(object : Callback<RegisterResult> {
                override fun onResponse(
                    call: Call<RegisterResult>,
                    response: Response<RegisterResult>
                ) {
                    response.body()?.let {
                        curUser.value = it.user
                        userLiveData.postValue(curUser.value)
                    }
                }

                override fun onFailure(call: Call<RegisterResult>, t: Throwable) {
                    curUser.value.status = UserStatus.OFFLINE
                    curUser.value.expireTime = 0
                    userLiveData.postValue(curUser.value)
                }

            })
    }

    fun login(username: String, password: String) {
        Log.d("UserModel", "开始进入登录")
        suspend {
            Log.d("UserModel", "开始登录")
            Network.service.login(username, password)
                .enqueue(object : Callback<LoginResult> {
                    override fun onResponse(
                        call: Call<LoginResult>,
                        response: Response<LoginResult>
                    ) {
                        response.body()?.let {
                            Log.d("UserModel", "登录成功")
                            if (it.code == LoginCode.SUCCESS) {
                                curUser.value = it.user
                                userLiveData.postValue(curUser.value)
                            }
                        }
                    }

                    override fun onFailure(call: Call<LoginResult>, t: Throwable) {
                        Log.d("UserModel", "登录失败")
                        curUser.value.username = ""
                        curUser.value.status = UserStatus.OFFLINE
                        curUser.value.expireTime = 0
                        userLiveData.postValue(curUser.value)
                    }

                })
        }
    }
}
