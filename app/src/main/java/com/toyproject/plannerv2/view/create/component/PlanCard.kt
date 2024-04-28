package com.toyproject.plannerv2.view.create.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toyproject.plannerv2.R
import com.toyproject.plannerv2.data.PlanData
import com.toyproject.plannerv2.view.component.textfield.PlannerV2TextField

@Composable
fun PlanCard(
    planData: PlanData,
    savePlanLogic: (title: String, description: String) -> Unit,
    deleteLogic: () -> Unit
) {
    var isModifyPlanState by remember { mutableStateOf(false) }
    var titleState by remember { mutableStateOf(planData.title) }
    var descriptionState by remember { mutableStateOf(planData.description) }

    if (isModifyPlanState) {
        ModifyPlanCard(
            title = planData.title,
            description = planData.description,
            onTitleChange = { titleState = it },
            onDescriptionChange = { descriptionState = it },
            onSaveButtonClick = {
                savePlanLogic(titleState, descriptionState)
                isModifyPlanState = false
            },
            onCancelButtonClick = { isModifyPlanState = false }
        )
    } else {
        PlanInfoCard(
            title = planData.title,
            description = planData.description,
            onCardClick = { isModifyPlanState = true },
            onIconClick = deleteLogic
        )
    }
}

@Composable
fun PlanInfoCard(
    title: String,
    description: String,
    onCardClick: () -> Unit,
    onIconClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .shadow(elevation = 1.dp, shape = RoundedCornerShape(8.dp))
            .background(Color(0xFFFAFAFA))
            .clip(RoundedCornerShape(8.dp))
            .clickable { onCardClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(15.dp)
        ) {
            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = title.ifEmpty { "새 일정" },
                fontSize = 19.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = description.ifEmpty { "비어 있음" },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            CategoryDropdown(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp)),
                onMenuClick = {

                }
            )
        }

        IconButton(
            modifier = Modifier
                .clip(CircleShape)
                .padding(end = 15.dp)
                .size(40.dp),
            onClick = onIconClick
        ) {
            Icon(
                modifier = Modifier.padding(8.dp),
                painter = painterResource(id = R.drawable.ic_delete_plan),
                contentDescription = "delete plan",
                tint = Color.Red
            )
        }
    }
}

@Composable
fun ModifyPlanCard(
    title: String,
    description: String,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onSaveButtonClick: () -> Unit,
    onCancelButtonClick: () -> Unit
) {
    var titleState by remember { mutableStateOf(title) }
    var descriptionState by remember { mutableStateOf(description) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .shadow(elevation = 1.dp, shape = RoundedCornerShape(8.dp))
            .background(Color(0xFFFAFAFA))
            .clip(RoundedCornerShape(8.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PlannerV2TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 10.dp),
            value = title,
            hint = "제목을 입력하세요.",
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
            onValueChange = {
                titleState = it
                onTitleChange(titleState)
            }
        )

        PlannerV2TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .height(85.dp),
            value = description,
            hint = "상세 내용을 입력하세요.",
            singleLine = false,
            maxLines = 4,
            onValueChange = {
                descriptionState = it
                onDescriptionChange(descriptionState)
            }
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
                onClick = onCancelButtonClick
            ) { Text(text = "취소") }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                modifier = Modifier.width(100.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFDB86)),
                onClick = onSaveButtonClick
            ) { Text(text = "저장") }
        }
    }
}