package com.ddx.compose.widget

import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

object DialogUtils {
    @Composable
    fun CustomDialog(show: Boolean, title: String = "标题", content: String = "标题") {
        var showDialog by rememberSaveable {
            mutableStateOf(show)
        }
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