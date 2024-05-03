package com.toyproject.plannerv2.view.category.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
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
                .background(Color(0xFFFAFAFA))
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

            Divider(modifier = Modifier.padding(vertical = 5.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "색상 수정"
                )

                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .background(Color(android.graphics.Color.parseColor(categoryColorState.value)))
                )

                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            categoryColorState.value = categoryData.categoryColorHex
                            println("카테고리 초기화: ${categoryColorState.value}")
                        },
                    imageVector = Icons.Default.Refresh,
                    tint = Color.Cyan,
                    contentDescription = "refresh color"
                )
            }

            ColorPicker(
                modifier = Modifier
                    .height(150.dp)
                    .padding(top = 10.dp),
                initialColor = Color(android.graphics.Color.parseColor(categoryColorState.value))
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
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6EC4A7)),
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
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFDB86)),
                        onClick = { onSaveClick(categoryTitleState.value, categoryColorState.value) }
                    ) { Text(text = "생성하기") }
                }
            }
        }
    }
}