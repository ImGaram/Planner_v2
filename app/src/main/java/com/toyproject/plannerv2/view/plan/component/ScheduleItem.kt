package com.toyproject.plannerv2.view.plan.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toyproject.plannerv2.data.PlanData

@Composable
fun ScheduleItem(
    planData: PlanData,
    onCheckBoxClick: (Boolean) -> Unit
) {
    val expendState = remember { mutableStateOf(false) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                modifier = Modifier.padding(vertical = 7.dp, horizontal = 10.dp),
                checked = planData.complete,
                onCheckedChange = {
                    onCheckBoxClick(!planData.complete)
                }
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 10.dp)
            ) {
                Text(
                    text = planData.title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier.padding(top = 5.dp),
                    text = planData.description,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Thin,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            IconButton(
                modifier = Modifier.padding(horizontal = 10.dp),
                onClick = {
                    expendState.value = !expendState.value
                }
            ) {
                Icon(
                    modifier = Modifier.rotate(if (expendState.value) 180f else 0f),
                    imageVector = Icons.Default.KeyboardArrowDown,
                    tint = Color(0xFF6EC4A7),
                    contentDescription = "more icon"
                )
            }
        }

        if (expendState.value) {
            Divider(
                modifier = Modifier
                    .height(0.5.dp)
                    .padding(horizontal = 15.dp)
            )

            PlanMenuItem(
                titleIconImageVector = Icons.Default.DateRange,
                titleIconTint = Color(0xFFFFDB86),
                menuTitle = "일정 수정하기"
            ) {
                
            }
        }
    }
}