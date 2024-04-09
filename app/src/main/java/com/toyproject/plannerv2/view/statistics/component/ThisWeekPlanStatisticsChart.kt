package com.toyproject.plannerv2.view.statistics.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.component.lineComponent
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.text.TextComponent
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entryOf

@Composable
fun ThisWeekPlanStatisticsChart(totalPlanCount: Int, completedPlanCount: Int) {
    val chartColumnColor = Color(0xFF6EC4A7)
    val maxYRange = (totalPlanCount / 10 + 2) * 10

    Chart(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(horizontal = 15.dp, vertical = 5.dp),
        chart = columnChart(
            columns = listOf(
                lineComponent(
                    color = chartColumnColor,
                    thickness = 5.dp,
                    shape = Shapes.cutCornerShape(topRightPercent = 20, topLeftPercent = 20)
                )
            ),
            dataLabel = TextComponent.Builder().build(),
            axisValuesOverrider = AxisValuesOverrider.fixed(minY = 0f, maxY = maxYRange.toFloat())
        ),
        chartModelProducer = ChartEntryModelProducer(
            listOf(
                entryOf(x = 0f, y = totalPlanCount.toFloat()),
                entryOf(x = 1f, y = completedPlanCount.toFloat())
            )
        ),
        startAxis = rememberStartAxis(
            itemPlacer = AxisItemPlacer.Vertical.default(maxItemCount = maxYRange / 10 + 1)
        ),
        bottomAxis = rememberBottomAxis(
            valueFormatter = { value, _ ->
                val xAxisLabelData = listOf("전체 일정", "완료한 일정")
                (xAxisLabelData[value.toInt()])
            }
        ),
        runInitialAnimation = true
    )
}