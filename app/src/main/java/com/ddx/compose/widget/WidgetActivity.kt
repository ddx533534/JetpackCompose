package com.ddx.compose.widget

import android.graphics.RectF
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ddx.compose.R
import com.ddx.compose.widget.Pie.CustomPie
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState

class WidgetActivity : ComponentActivity() {

    private val tabs = listOf("OKR", "Android", "Language");
    private val items = listOf("112312", "112312", "112312", "112312", "112312", "112312");

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold(
                content = {
                    Box {
                        Column(Modifier.horizontalScroll(rememberScrollState())) {
                            SubTitle(title = "自定义 tab")
                            CustomTab()
                            SubTitle(title = "自定义 layout")
                            CustomLayout()
                            SubTitle(title = "button")
                            HelloButton()
//                        SubTitle(title = "列表")
//                        CustomScroll()
                            SubTitle(title = "文本")
                            CustomText()
                            SubTitle(title = "图片")
                            CustomPicture()
                            SubTitle(title = "自定义 Canvas")
                            CustomCanvas()
                        }
                        hair()
                    }
                },
            )
        }
    }


    @Composable
    private fun CustomText() {
        Row() {
            Text(buildAnnotatedString {
                append("black base text!")
                withStyle(SpanStyle(color = Color.Red, fontSize = 12.sp)) {
                    append("red fu text")
                }
            })
            Text(buildAnnotatedString {
                withStyle(SpanStyle()) {
                    append("hello world!")
                }
            })
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun CustomScroll() {
        Row {
            // 类似于水平方向的 scrollView
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .width(200.dp)
                    .height(100.dp)
            ) {
                tabs.forEach {
                    Image(
                        painter = painterResource(R.drawable.car_img), contentDescription = it,
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp)
                    )
                }
            }
            // 类似于垂直方向的 scrollView
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .width(200.dp)
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
            // 粘性标题
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier
                    .height(150.dp)
                    .width(200.dp)
            ) {
                item {
                    Text(text = "i am a text!")
                }
                stickyHeader {
                    Row(
                        modifier = Modifier
                            .height(15.dp)
                            .fillMaxWidth()
                            .background(Color.White)
                    ) {
                        Text(text = "i am a stickyHeader!")
                    }
                }
                items(items) {
                    Text(text = "i am a text part of tabs!")
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
        var content: String = "hello world!"
        var showDialog by rememberSaveable {
            mutableStateOf(false)
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.LightGray)
        ) {
            TextButton(onClick = { showDialog = true }) {
                Text(
                    text = title,
                    color = Color.Black,
                    fontSize = 30.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold
                )
            }
            // 自定义弹窗
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = {
                        showDialog = false;
                    },

                    title = {
                        Text(text = title, fontWeight = FontWeight.Bold)
                    },
                    text = {
                        Text(text = content)
                    },

                    confirmButton = {
                        TextButton(
                            onClick = {
                                showDialog = false;
                            },
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Text("关闭")
                        }
                    },
                )
            }
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

    @Composable
    fun CustomPicture() {
        val painter =
            rememberCoilPainter(request = "https://p0.meituan.net/travelcube/f78b0d491cb9a68382d4154c1a50b6104090560.jpg")
        Row {
            // surface 可以用来实现圆角和圆形
            Surface(
                shape = RoundedCornerShape(20.dp), modifier = Modifier.background(Color.Cyan)
            ) {
                Image(
                    painter = painter,
                    contentDescription = "1",
                    modifier = Modifier
                        .width(200.dp)
                        .height(200.dp),
                    contentScale = ContentScale.Inside
                )
            }

            Box {
                Surface(
                    shape = RoundedCornerShape(50), modifier = Modifier.background(Color.Gray)
                ) {
                    Image(
                        painter = painter,
                        contentDescription = "1",
                        modifier = Modifier
                            .width(200.dp)
                            .height(200.dp),
                        contentScale = ContentScale.Inside
                    )
                    when (painter.loadState) {
                        is ImageLoadState.Loading -> {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier
                                    .size(20.dp)
                                    .align(Alignment.Center)
                            )
                        }
                        is ImageLoadState.Error -> {
                            Toast.makeText(LocalContext.current, "加载失败!", LENGTH_LONG).show()
                        }
                        is ImageLoadState.Success -> {
                            Toast.makeText(LocalContext.current, "加载成功!", LENGTH_LONG).show()
                        }
                        else -> {
                            //do nothing
                        }
                    }
                }
            }
        }
    }


    @Composable
    private fun CustomCanvas() {
        Row {
            Canvas(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color(0x00, 0x00, 0x00, 0x10))
            ) {
                var path = Path()
                path.cubicTo(0f, 100f, 100f, 0f, 200f, 100f)
                path.cubicTo(200f, 100f, 250f, 160f, 300f, 100f)
                path.cubicTo(300f, 100f, 350f, 160f, 300f, 300f)

                drawPath(path, Color.Black, 1f, Stroke(1f))
            }
            CustomPie()

        }
    }

    @Composable
    private fun hair() {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            val path = Path()
            path.cubicTo(500f, 100f, 800f, 300f, 1200f, 600f)
            path.cubicTo(1200f, 600f, 1400f, 500f, 1300f, 1300f)

            drawPath(path, Color.Black, 1f, Stroke(2f))
        }
    }

    /**
     * 全局暗影状态下的手电筒效果
     */
    @Composable
    private fun light() {
        var pointerOffset by remember {
            mutableStateOf(Offset(0f, 0f))
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput("dragging") {
                    detectDragGestures { _, dragAmount ->
                        {
                            pointerOffset += dragAmount
                        }
                    }
                }
                .onSizeChanged {
                    pointerOffset = Offset(it.width / 2f, it.height / 2f)
                }
                .drawWithContent {
                    drawContent()
                    drawRect(
                        Brush.radialGradient(
                            listOf(Color.Transparent, Color.Black),
                            center = pointerOffset,
                            radius = 100.dp.toPx()
                        )
                    )
                }
        ) {
        }
    }

}
