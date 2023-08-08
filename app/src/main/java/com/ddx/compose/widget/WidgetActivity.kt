package com.ddx.compose.widget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ddx.compose.R
import com.ddx.compose.ui.theme.ComposeTheme

class WidgetActivity : ComponentActivity() {

    private val tabs = listOf("OKR", "Android", "Language");

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold(
                content = {
                    Column {
                        SubTitle(title = "自定义 tab")
                        CustomTab()
                        SubTitle(title = "自定义 layout")
                        CustomLayout()
                        SubTitle(title = "button")
                        HelloButton()
                        SubTitle(title = "列表")
                        CustomScroll()
                    }
                },
            )
        }
    }

    @Composable
    private fun CustomScroll() {
        Column {
            // 类似于水平方向的 scrollView
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                tabs.forEach {
                    Image(
                        painter = painterResource(R.drawable.car_img), contentDescription = it,
                        modifier = Modifier
                            .width(200.dp)
                            .height(100.dp)
                    )
                }
            }
            // 类似于垂直方向的 scrollView
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                tabs.forEach {
                    Image(
                        painter = painterResource(R.drawable.car_img), contentDescription = it,
                        modifier = Modifier
                            .width(80.dp)
                            .height(80.dp)
                    )
                }
            }
        }
    }

    @Composable
    fun CustomLayout() {
        // Box 布局，类似于 native 的Relative 布局，即层叠布局
        Row {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(Color.Red)
                    .width(100.dp)
                    .height(100.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(Color.Blue)
                        .width(80.dp)
                        .height(80.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(Color.Yellow)
                            .width(60.dp)
                            .height(60.dp)
                    ) {
                        Text(text = "These are Boxes!")
                    }
                }
            }
            // Row 布局，类似于 native 的水平方向的 LinearLayout 布局，即线性布局
            Row(modifier = Modifier.padding(10.dp)) {
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .width(20.dp)
                        .background(Color.Yellow)
                ) {}
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .width(20.dp)
                        .background(Color.Green)
                ) {}
            }

            // Column 布局，类似于 native 的垂直方向的 LinearLayout 布局，即线性布局
            Column(modifier = Modifier.padding(10.dp)) {
                Column(
                    modifier = Modifier
                        .height(20.dp)
                        .width(40.dp)
                        .background(Color.Yellow)
                ) {}
                Column(
                    modifier = Modifier
                        .height(20.dp)
                        .width(40.dp)
                        .background(Color.Green)
                ) {}
            }
        }

    }

    @Composable
    fun HelloButton() {
        // 将变量委托给 rememberSaveable - 销毁重建恢复变量
        var clickCountSave by rememberSaveable {
            mutableStateOf(0)
        }
        // 将变量委托给 remember - 销毁重建无法恢复
        var clickCount by remember {
            mutableStateOf(0)
        }
        Row() {
            Button(onClick = { clickCountSave++ }) {
                Text(text = "点击次数(可恢复):${clickCountSave}")
            }
            Button(onClick = { clickCount++ }) {
                Text(text = "点击次数(不可恢复):${clickCount}")
            }
        }
    }

    @Composable
    fun SubTitle(title: String) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.LightGray)
        ) {
            Text(
                text = title,
                color = Color.Black,
                fontSize = 30.sp,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold
            )
        }
    }

    @Composable
    fun CustomTab() {
        val state = remember { mutableStateOf(0) };
        ScrollableTabRow(
            selectedTabIndex = state.value,
            backgroundColor = Color.Transparent,
            divider = { Divider() },
            modifier = Modifier.fillMaxWidth()
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = state.value == index,
                    onClick = { state.value = index; },
                    text = { Text(tab) },
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Color.Black,
                )
            }

        }
    }


}
