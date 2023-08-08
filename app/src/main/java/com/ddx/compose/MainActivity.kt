package com.ddx.compose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ddx.compose.ui.theme.ComposeTheme
import com.ddx.compose.widget.WidgetActivity

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column {
                        Greeting("Hello World!")
                        CustomButton(name = "自定义组件",
                            intent = Intent(LocalContext.current,WidgetActivity::class.java))
                    }

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
