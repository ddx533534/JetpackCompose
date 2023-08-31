package com.ddx.compose.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ddx.compose.model.User
import com.ddx.compose.model.UserModel
import com.ddx.compose.model.UserStatus
import org.json.JSONObject
import java.security.Timestamp
import java.sql.Time
import java.util.Date

class LoginActivity : BaseActivity() {
    private val userModel: UserModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @Composable
    override fun setContent() {
        Login_surface()
    }

    @Composable
    fun Login_surface() {
        val curUser by userModel.userLiveData.observeAsState()
        var username by rememberSaveable {
            mutableStateOf("")
        }
        var password by rememberSaveable {
            mutableStateOf("")
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (curUser?.status == UserStatus.ONLINE) {
                Text(
                    text = "欢迎${curUser?.username}登录！过期时间${
                        curUser?.expireTime?.let {
                            Date(it).toString()
                        }
                    }!", fontSize = 20.sp, fontWeight = FontWeight.Bold
                )
                return@Column
            } else {
                TextField(
                    value = username,
                    onValueChange = {
                        username = it
                    },
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable(onClick = { username = "" })
                        )
                    },

                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )
                Column(modifier = Modifier.height(10.dp)) {}
                TextField(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    singleLine = true,

                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Lock,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable(onClick = { password = "" })
                        )
                    },
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    visualTransformation = PasswordVisualTransformation()
                )
                Column(modifier = Modifier.height(10.dp)) {}
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Button(onClick = { }) {
                        Text(text = "Register")
                    }
                    Button(onClick = { userModel.login(username, password) }) {
                        Text(text = "Login")
                    }
                    Button(onClick = { userModel.testMain() }) {
                        Text(text = "TestMain")
                    }
                }
            }
        }
    }

    override fun getPageName(): String {
        return "登录页面"
    }
}