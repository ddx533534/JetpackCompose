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
import kotlin.random.Random

object Pie {
    @Composable
    fun CustomPie(list: List<Float>) {

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
                var count = 0f
                var colorList = listOf(Color.Red, Color.Yellow, Color.Green, Color.Blue)
                list.forEach {
                    count += it
                }
                var startAngle = 0f
                var sweepAngle = 0f
                list.forEachIndexed { index, it ->
                    if (index == list.count() - 1) {
                        drawArc(
                            colorList[index.rem(colorList.count())],
                            startAngle,
                            360 - startAngle,
                            true
                        )
                        return@forEachIndexed
                    }
                    sweepAngle = it.div(count).times(360f)
                    drawArc(colorList[index.rem(colorList.count())], startAngle, sweepAngle, true)
                    startAngle += sweepAngle
                }
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