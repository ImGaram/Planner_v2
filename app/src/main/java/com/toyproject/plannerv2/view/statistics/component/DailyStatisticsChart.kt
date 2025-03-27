package com.toyproject.plannerv2.view.statistics.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollState
import com.patrykandpatrick.vico.compose.component.lineComponent
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.compose.legend.horizontalLegend
import com.patrykandpatrick.vico.compose.legend.legendItem
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.text.TextComponent
import com.patrykandpatrick.vico.core.component.text.textComponent
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.toyproject.plannerv2.util.intListAsFloatEntryList
import com.toyproject.plannerv2.view.ui.theme.PlannerTheme

@Composable
fun DailyStatisticsChart(dailyPlanList: List<Int>) {
    val chartColumnColor = Color(0xFF6EC4A7)
    val maxYRange = (dailyPlanList.max() / 10 + 1) * 10

    Chart(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(horizontal = 15.dp, vertical = 5.dp),
        chart = columnChart(
            columns = arrayListOf(
                lineComponent(
                    color = chartColumnColor,
                    thickness = 25.dp,
                    shape = Shapes.cutCornerShape(topRightPercent = 20, topLeftPercent = 20)
                )
            ),
            dataLabel = TextComponent.Builder().build(),
            axisValuesOverrider = AxisValuesOverrider.fixed(minY = 0f, maxY = maxYRange.toFloat())
        ),
        legend = horizontalLegend(
            items = List(1) {
                legendItem(
                    icon = shapeComponent(
                        shape =  Shapes.pillShape,
                        color = chartColumnColor
                    ),
                    label = textComponent {
                        color = PlannerTheme.colors.primary.toArgb()
                    },
                    labelText = "생성한 일정(개)"
                )
            },
            iconSize = 10.dp,
            iconPadding = 8.dp,
            spacing = 10.dp,
            padding = dimensionsOf(top = 8.dp)
        ),
        chartModelProducer = ChartEntryModelProducer(intListAsFloatEntryList(dailyPlanList)),
        startAxis = rememberStartAxis(
            itemPlacer = AxisItemPlacer.Vertical.default(maxItemCount = maxYRange / 10 + 2)
        ),
        bottomAxis = rememberBottomAxis(
            valueFormatter = { value, _ ->
                val xAxisLabelData = listOf("일", "월", "화", "수", "목", "금", "토")
                (xAxisLabelData[value.toInt()])
            },
            guideline = null
        ),
        chartScrollState = rememberChartScrollState()
    )
}