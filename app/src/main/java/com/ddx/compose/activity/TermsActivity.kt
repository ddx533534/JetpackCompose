package com.ddx.compose.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ddx.compose.model.Term
import com.ddx.compose.model.TermModel

class TermsActivity : ComponentActivity() {
    private val TAG = "TermModel";
    private val list = mutableStateListOf<Term>();
    private val termModel by viewModels<TermModel>()
    private val deleteList = mutableStateListOf<Int>();
    private var showDeleteButton = mutableStateOf(false);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        setContent {
            Content()
        }
    }

    private fun initData() {
        repeat(30) {
            list.add(Term("ddx", it.toString(), "ddx"))
        }
    }

    @Composable
    fun Content() {
        Box {
            Main_Layout()
            FloatingButton()
        }

    }

    /**
     * 悬浮按钮，用来添加或者删除元素
     */
    @Composable
    fun FloatingButton() {
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 16.dp, bottom = 16.dp)
        ) {
            FloatingActionButton(onClick = {
                Log.d(TAG, "FloatingActionButton-delete: $deleteList")
                if (deleteList.isNotEmpty()) {
                    termModel.deleteTerms(deleteList)
                    deleteList.clear()
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    Modifier.background(Color.Red),
                    tint = Color.White
                )
            }
            FloatingActionButton(onClick = {

            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    @Preview
    fun Main_Layout() {
//        val list by termModel.termListLiveData.observeAsState(listOf())
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xDE, 0xDE, 0xDE, 0xFF)),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Log.d(TAG, "触发绘制" + "size：" + list.count())
            stickyHeader {
                header()
            }
            itemsIndexed(list) { index, term ->
                item(index, term)
            }

        }
    }

    /**
     * 列表吸顶视图
     */
    @Composable
    fun header() {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .background(Color(0xFF, 0xFA, 0xD1, 0xFF))
        ) {
            Text(
                text = "Terms Set",
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
            )
        }
    }

    /**
     * 列表 item 视图
     */
    @Composable
    fun item(index: Int, term: Term) {
        var showDetail by rememberSaveable {
            mutableStateOf(false)
        }
        Surface(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .pointerInput(index) {
                    detectTapGestures(
                        onLongPress = {
                            showDeleteButton.value = !showDeleteButton.value
                            deleteList.clear()
                        },
                        onTap = { showDetail = !showDetail }
                    )
                }
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(10.dp, 20.dp)
            ) {
                Row {
                    Text(buildAnnotatedString {
                        withStyle(SpanStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)) {
                            append(index.toString() + "." + term.title)
                        }
                        withStyle(SpanStyle(fontSize = 15.sp, fontWeight = FontWeight.Normal)) {
                            append("(${term.subTitle})")
                        }
                    })
                    if (showDeleteButton.value) {
                        Item_choose(index)
                    }
                }
                if (showDetail) {
                    Item_content(content = term.content)
                }
            }
        }

    }

    /**
     * item 单选按钮，可以分享或者删除
     */
    @Composable
    private fun Item_choose(index: Int) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Checkbox(
                checked = deleteList.contains(index), onCheckedChange = { checked ->
                    if (checked) {
                        deleteList.add(index)
                    } else {
                        deleteList.remove(index)
                    }
                    Log.d(TAG, "onCheckedChange: ${deleteList.toList()}")
                }, modifier = Modifier
                    .size(20.dp)

            )
        }
    }

    /**
     * item 展开的具体内容
     */
    @Composable
    private fun Item_content(content: String) {
        Surface(
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(Color(0xB5, 0xB5, 0xB5, 0xFF))
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .wrapContentHeight()
            ) {
                Text(
                    text = content,
                    textAlign = TextAlign.Start,
                    fontSize = 15.sp,
                    modifier = Modifier
                        .padding(10.dp),
                )
            }
        }

    }
}
