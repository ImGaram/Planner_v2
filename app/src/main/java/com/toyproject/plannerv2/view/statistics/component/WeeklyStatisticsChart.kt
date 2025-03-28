package com.toyproject.plannerv2.view.statistics.component

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
import com.patrykandpatrick.vico.core.entry.composed.ComposedChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.composed.plus
import com.patrykandpatrick.vico.core.legend.HorizontalLegend
import com.toyproject.plannerv2.util.intListAsFloatEntryList
import com.toyproject.plannerv2.view.ui.theme.PlannerTheme

@Composable
fun WeeklyCompletionStatisticsChart(
    totalPlanList: List<Int>,
    completedPlanList: List<Int>
) {
    val maxYRange = if (totalPlanList.max() >= 100) (totalPlanList.max() / 10 + 2) * 10
    else (completedPlanList.max() / 10 + 2) * 10

    val colorList = listOf(PlannerTheme.colors.green, PlannerTheme.colors.yellow)

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

        val completedPlanEntry = ChartEntryModelProducer(intListAsFloatEntryList(totalPlanList))
        val completedRateEntry = ChartEntryModelProducer(intListAsFloatEntryList(completedPlanList))

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
                },
                guideline = null
            ),
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
            axis = ChartStyle.Axis(
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
    val labelTextList = listOf("생성한 일정(개)", "완료한 일정(개)")

    return horizontalLegend(
        items = List(labelTextList.size) { index ->
            legendItem(
                icon = shapeComponent(
                    shape = Shapes.pillShape,
                    color = colors[index]
                ),
                label = textComponent {
                    color = PlannerTheme.colors.primary.toArgb()
                },
                labelText = labelTextList[index]
            )
        },
        iconSize = 10.dp,
        iconPadding = 8.dp,
        spacing = 10.dp,
        padding = dimensionsOf(top = 8.dp)
    )
}