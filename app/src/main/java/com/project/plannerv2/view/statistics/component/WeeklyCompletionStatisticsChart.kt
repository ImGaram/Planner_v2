package com.project.plannerv2.view.statistics.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollState
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.compose.legend.horizontalLegend
import com.patrykandpatrick.vico.compose.legend.legendItem
import com.patrykandpatrick.vico.compose.style.ChartStyle
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.DefaultColors
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.chart.column.ColumnChart
import com.patrykandpatrick.vico.core.chart.composed.plus
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.text.TextComponent
import com.patrykandpatrick.vico.core.component.text.textComponent
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.composed.ComposedChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.composed.plus
import com.patrykandpatrick.vico.core.entry.entryOf
import com.patrykandpatrick.vico.core.legend.HorizontalLegend

@Composable
fun WeeklyCompletionStatisticsChart(
    completedPlanList: List<Int>,
    completedRateList: List<Int>
) {
    val maxYRange = if (completedPlanList.max() >= 100) (completedPlanList.max() / 10 + 2) * 10
    else (completedRateList.max() / 10 + 2) * 10

    val colorList = listOf(Color(0xFF6EC4A7), Color(0xFFFFDB86))

    ProvideChartStyle(rememberChartStyle(columnChartColors = colorList)) {
        val completedPlanChart = columnChart(
            mergeMode = ColumnChart.MergeMode.Grouped,
            axisValuesOverrider = AxisValuesOverrider.fixed(
                minY = 0f,
                maxY = maxYRange.toFloat()
            ),
            spacing = 100.dp
        )
        val completedRateChart = columnChart(
            mergeMode = ColumnChart.MergeMode.Grouped,
            axisValuesOverrider = AxisValuesOverrider.fixed(
                minY = 0f,
                maxY = maxYRange.toFloat()
            ),
            spacing = 100.dp
        )

        val completedPlanEntry = ChartEntryModelProducer(intListAsFloatEntryList(completedPlanList))
        val completedRateEntry = ChartEntryModelProducer(intListAsFloatEntryList(completedRateList))

        Chart(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(horizontal = 15.dp, vertical = 5.dp),
            chart = remember(completedPlanChart, completedRateChart) {
                completedPlanChart + completedRateChart
            },
            legend = rememberLegend(colors = colorList),
            chartModelProducer = ComposedChartEntryModelProducer(completedPlanEntry.plus(completedRateEntry)),
            startAxis = rememberStartAxis(
                itemPlacer = AxisItemPlacer.Vertical.default(maxItemCount = maxYRange / 10 + 1)
            ),
            bottomAxis = rememberBottomAxis(
                valueFormatter = { value, _ ->
                    ("${value.toInt()+1}주 전")
                }
            ),
            runInitialAnimation = true,
            chartScrollState = rememberChartScrollState()
        )
    }
}

@Composable
fun rememberChartStyle(columnChartColors: List<Color>): ChartStyle {
    val isSystemInDarkTheme = isSystemInDarkTheme()
    return remember(columnChartColors, isSystemInDarkTheme) {
        val defaultColors = if (isSystemInDarkTheme) DefaultColors.Dark else DefaultColors.Light

        ChartStyle(
            ChartStyle.Axis(
                axisLabelColor = Color(defaultColors.axisLabelColor),
                axisGuidelineColor = Color(defaultColors.axisGuidelineColor),
                axisLineColor = Color(defaultColors.axisLineColor)
            ),
            columnChart = ChartStyle.ColumnChart(
                columns = columnChartColors.map { columnColor ->
                    LineComponent(
                        color = columnColor.toArgb(),
                        thicknessDp = 25f,
                        shape = Shapes.cutCornerShape(topRightPercent = 20, topLeftPercent = 20)
                    )
                },
                dataLabel = TextComponent.Builder().build()
            ),
            lineChart = ChartStyle.LineChart(lines = emptyList()),
            marker = ChartStyle.Marker(),
            elevationOverlayColor = Color(defaultColors.elevationOverlayColor)
        )
    }
}

@Composable
fun rememberLegend(colors: List<Color>): HorizontalLegend {
    val labelTextList = listOf("완료한 일정(개)", "일정 완료율(%)")

    return horizontalLegend(
        items = List(labelTextList.size) { index ->
            legendItem(
                icon = shapeComponent(
                    shape = Shapes.pillShape,
                    color = colors[index]
                ),
                label = textComponent(),
                labelText = labelTextList[index]
            )
        },
        iconSize = 10.dp,
        iconPadding = 8.dp,
        spacing = 10.dp,
        padding = dimensionsOf(top = 8.dp)
    )
}

private fun intListAsFloatEntryList(list: List<Int>): List<FloatEntry> {
    val floatEntryList = arrayListOf<FloatEntry>()
    floatEntryList.clear()

    list.forEachIndexed { index, item ->
        floatEntryList.add(entryOf(x = index.toFloat(), y = item.toFloat()))
    }

    return floatEntryList
}