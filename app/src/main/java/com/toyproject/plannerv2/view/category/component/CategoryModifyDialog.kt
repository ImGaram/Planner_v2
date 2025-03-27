package com.toyproject.plannerv2.view.category.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.toyproject.plannerv2.data.CategoryData
import com.toyproject.plannerv2.util.toHexCode
import com.toyproject.plannerv2.view.component.textfield.PlannerV2TextField
import com.toyproject.plannerv2.view.ui.theme.PlannerTheme
import androidx.core.graphics.toColorInt

@Composable
fun CategoryModifyDialog(
    categoryData: CategoryData,
    onDismissRequest: () -> Unit,
    onCancelClick: () -> Unit,
    onSaveClick: (String, String) -> Unit
) {
    val categoryTitleState = remember { mutableStateOf(categoryData.categoryTitle) }
    val categoryColorState = remember { mutableStateOf(categoryData.categoryColorHex) }
    val saveVisibility = remember { mutableStateOf(false) }

    LaunchedEffect(categoryTitleState.value) {
        saveVisibility.value = categoryTitleState.value.isNotBlank()
    }

    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 1.dp, shape = RoundedCornerShape(8.dp))
                .background(PlannerTheme.colors.cardBackground)
                .clip(RoundedCornerShape(8.dp))
                .padding(10.dp),
        ) {
            PlannerV2TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp),
                value = categoryTitleState.value,
                hint = "변경할 카테고리 이름을 입력하세요.",
                singleLine = true,
                maxLines = 1,
                onValueChange = { categoryTitleState.value = it }
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 5.dp),
                color = PlannerTheme.colors.gray300
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "색상 수정",
                    color = PlannerTheme.colors.primary
                )

                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .background(Color(categoryColorState.value.toColorInt()))
                        .border(
                            width = 1.dp,
                            color = PlannerTheme.colors.gray300
                        )
                )

                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            categoryColorState.value = categoryData.categoryColorHex
                        },
                    imageVector = Icons.Default.Refresh,
                    tint = PlannerTheme.colors.blue,
                    contentDescription = "refresh color"
                )
            }

            ColorPicker(
                modifier = Modifier
                    .height(150.dp)
                    .padding(top = 10.dp),
                initialColor = Color(categoryColorState.value.toColorInt())
            ) {
                categoryColorState.value = it.toHexCode()
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                Button(
                    modifier = Modifier.width(100.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PlannerTheme.colors.green),
                    onClick = onCancelClick
                ) { Text(text = "취소") }

                Spacer(modifier = Modifier.weight(1f))

                AnimatedVisibility(
                    visible = saveVisibility.value,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it + 10 })      // dialog에 기본적으로 설정된 padding 값(10) 만큼 추가 값을 부여함.
                ) {
                    Button(
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PlannerTheme.colors.yellow),
                        onClick = { onSaveClick(categoryTitleState.value, categoryColorState.value) }
                    ) { Text(text = "수정하기") }
                }
            }
        }
    }
}