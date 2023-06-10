package com.ddx.compose

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddx.compose.ui.theme.ComposeTheme

class MainActivity : ComponentActivity() {

    private val tabs = listOf("OKR", "Android", "Language");

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column {
                        Greeting("Android")
                        CustomTab()
                    }

                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        ComposeTheme {
            Column {
                Greeting("Android")
                CustomTab()
            }
        }
    }

    @Composable
    fun CustomTab() {
        val state = remember { mutableStateOf(0) };
        ScrollableTabRow(
            selectedTabIndex = state.value,
            backgroundColor = Color.Transparent,
            divider = { Divider() }) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = state.value == index,
                    onClick = { state.value = index; },
                    text = { Text(tab) },
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Color.Black,
                    modifier = Modifier.size(40.dp)
                )
            }

        }
    }


    @Composable
    fun Greeting(name: String) {
        return Row(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Home",
                Modifier.size(60.dp)
            )
        }
    }


}
