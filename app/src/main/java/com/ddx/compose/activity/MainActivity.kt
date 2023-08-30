package com.ddx.compose.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ddx.compose.model.UserModel
import com.ddx.compose.model.UserStatus

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @Composable
    override fun setContent() {
        Main_Layout()
    }

    @Composable
    fun Main_Layout(userModel: UserModel = viewModel()) {
        val user by userModel.userLiveData.observeAsState()
        Column {
            Greeting("Hello World!")
            CustomButton(
                name = "自定义组件",
                intent = Intent(LocalContext.current, WidgetActivity::class.java)
            )
            CustomButton(
                name = "流行术语",
                intent = Intent(LocalContext.current, TermsActivity::class.java)
            )
            if (user?.status != UserStatus.ONLINE) {
                CustomButton(
                    name = "登录",
                    intent = Intent(LocalContext.current, LoginActivity::class.java)
                )
            } else {
                Toast(LocalContext.current).setText("您已经登录！")
            }
        }
    }

    @Composable
    fun Greeting(name: String) {
        return Row(modifier = Modifier.padding(20.dp)) {
            Text(
                text = name,
                Modifier.size(60.dp)
            )
        }
    }

    @Composable
    fun CustomButton(name: String, intent: Intent) {
        Button(onClick = {
            startActivity(intent)
        }, Modifier.padding(10.dp)) {
            Text(text = name)
        }
    }

    override fun getPageName(): String {
        return "首页"
    }

}
