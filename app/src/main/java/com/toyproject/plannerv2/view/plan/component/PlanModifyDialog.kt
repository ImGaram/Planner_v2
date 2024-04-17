package com.toyproject.plannerv2.view.plan.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.toyproject.plannerv2.data.PlanData
import com.toyproject.plannerv2.view.component.textfield.PlannerV2TextField

@Composable
fun PlanModifyDialog(
    planData: PlanData,
    onSaveClick: (title: String, description: String) -> Unit,
    onDismissRequest: () -> Unit,
    onCancelClick: () -> Unit
) {
    val titleState = remember { mutableStateOf(planData.title) }
    val descriptionState = remember { mutableStateOf(planData.description) }
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 1.dp, shape = RoundedCornerShape(8.dp))
                .background(Color(0xFFFAFAFA))
                .clip(RoundedCornerShape(8.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PlannerV2TextField(
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp),
                value = titleState.value,
                hint = "변경할 제목을 입력하세요.",
                singleLine = true,
                maxLines = 1,
                textStyle = TextStyle(
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Medium
                ),
                hintTextStyle = TextStyle(
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Medium
                ),
                onValueChange = { titleState.value = it }
            )

            PlannerV2TextField(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .height(85.dp),
                value = descriptionState.value,
                hint = "변경할 상세 내용을 입력하세요.",
                singleLine = false,
                maxLines = 4,
                onValueChange = { descriptionState.value = it }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 15.dp)
            ) {
                Button(
                    modifier = Modifier.width(100.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6EC4A7)),
                    onClick = onCancelClick
                ) { Text(text = "취소") }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    modifier = Modifier.width(100.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFDB86)),
                    onClick = { onSaveClick(titleState.value, descriptionState.value) }
                ) { Text(text = "저장") }
            }
        }
    }
}