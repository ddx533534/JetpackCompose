package com.ddx.compose.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
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
                }

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

}
