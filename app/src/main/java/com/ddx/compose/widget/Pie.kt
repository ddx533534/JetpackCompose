package com.ddx.compose.widget

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp

object Pie {
    @Composable
    fun CustomPie() {
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(Color(0x00, 0x00, 0x00, 0x10)), contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val path = Path()
//                    path.arcTo(Rect(), 0f, 50f, false)
                val bounds = path.getBounds()

                drawArc(Color.Red, 0f, 100f, true)
                drawArc(Color.Yellow, 100f, 100f, true)
                drawArc(Color.Green, 200f, 100f, true)
                drawArc(Color.Blue, 300f, 60f, true)
            }
            Canvas(
                modifier = Modifier
                    .size(100.dp)
            ) {
                drawArc(Color.Black, 0f, 360f, true)
            }
        }
    }
}