package com.toyproject.plannerv2.view.plan.component.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cheonjaeung.compose.grid.SimpleGridCells
import com.cheonjaeung.compose.grid.VerticalGrid
import com.toyproject.plannerv2.view.ui.theme.PlannerTheme
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.Locale

@Composable
fun CalendarHeader(
    modifier: Modifier = Modifier,
    title: String,
    onRightArrowClick: () -> Unit,
    onLeftArrowClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = "header left arrow icon",
            modifier = Modifier.clickable { onLeftArrowClick() },
            tint = PlannerTheme.colors.primary
        )

        Text(
            text = title,
            style = TextStyle(
                color = PlannerTheme.colors.primary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        )

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "header right arrow icon",
            modifier = Modifier.clickable { onRightArrowClick() },
            tint = PlannerTheme.colors.primary
        )
    }
}

@Composable
fun CalendarMonthContent(
    modifier: Modifier = Modifier,
    currentDate: LocalDate,
    selectedDate: LocalDate,
    onSelectedDate: (LocalDate) -> Unit
) {
    val lastDay by remember { mutableIntStateOf(currentDate.lengthOfMonth()) }
    val firstDayOfWeek by remember { mutableIntStateOf(currentDate.dayOfWeek.value) }
    val days by remember { mutableStateOf(IntRange(1, lastDay).toList()) }

    Column(modifier = modifier) {
        DayOfWeek()

        VerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            columns = SimpleGridCells.Fixed(7),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (firstDayOfWeek < 7) {
                for (i in 0 until firstDayOfWeek) {
                    Box(modifier = Modifier.aspectRatio(1f))
                }
            }

            days.forEach { day ->
                val date = currentDate.withDayOfMonth(day)
                val isSelected = remember(selectedDate) { selectedDate.compareTo(date) == 0 }

                CalendarDayContent(
                    date = date,
                    isSelected = isSelected,
                    onClick = onSelectedDate
                )
            }
        }
    }
}

@Composable
fun CalendarDayContent(
    modifier: Modifier = Modifier,
    date: LocalDate,
    isSelected: Boolean = false,
    onClick: (LocalDate) -> Unit
) {
    val boxColor = if (isSelected) Black
    else Color(0x1A000000)

    Column(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(boxColor)
            .clickable { onClick(date) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val textColor = if (date.dayOfWeek == DayOfWeek.SUNDAY) Color.Red
        else if (date.dayOfWeek == DayOfWeek.SATURDAY) Color.Blue
        else if (isSelected) Color.White
        else Black

        Text(
            text = date.dayOfMonth.toString(),
            textAlign = TextAlign.Center,
            color = textColor
        )
    }
}

@Composable
fun DayOfWeek(modifier: Modifier = Modifier) {
    val dayOfWeek = DayOfWeek.entries
        .sortedBy { it.value % 7 }

    VerticalGrid(
        modifier = modifier,
        columns = SimpleGridCells.Fixed(7),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        dayOfWeek.forEach { dayOfWeek ->
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = dayOfWeek.getDisplayName(java.time.format.TextStyle.NARROW, Locale.KOREAN),
                style = TextStyle(
                    color = Black,
                    textAlign = TextAlign.Center
                ),
            )
        }
    }
}
