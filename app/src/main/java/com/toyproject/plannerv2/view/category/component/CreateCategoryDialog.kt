package com.toyproject.plannerv2.view.category.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.toyproject.plannerv2.data.CategoryData
import com.toyproject.plannerv2.util.toHexCode
import com.toyproject.plannerv2.view.component.textfield.PlannerV2TextField
import com.toyproject.plannerv2.view.ui.theme.PlannerTheme
import java.util.UUID
import androidx.core.graphics.toColorInt

@Composable
fun CreateCategoryDialog(
    onDismissRequest: () -> Unit,
    onSaveClick: (CategoryData) -> Unit,
    onCancelClick: () -> Unit,
) {
    val titleState = remember { mutableStateOf("") }
    val colorState = remember { mutableStateOf("#FFFFFFFF") }
    val saveVisibility = remember { mutableStateOf(false) }

    LaunchedEffect(titleState.value) {
        saveVisibility.value = titleState.value.isNotBlank()
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
            Text(
                text = "새 카테고리",
                color = PlannerTheme.colors.primary,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                PlannerV2TextField(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 10.dp)
                        .height(45.dp),
                    value = titleState.value,
                    hint = "카테고리 이름을 입력하세요.",
                    singleLine = true,
                    maxLines = 1
                ) { titleState.value = it }

                Box(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .size(30.dp)
                        .background(Color(colorState.value.toColorInt()))
                        .border(
                            width = 1.dp,
                            color = PlannerTheme.colors.gray300
                        )
                )
            }

            HorizontalDivider(
                color = PlannerTheme.colors.gray300
            )

            ColorPicker(
                modifier = Modifier
                    .height(150.dp)
                    .padding(vertical = 10.dp)
            ) {
                colorState.value = it.toHexCode()
            }

            Row(modifier = Modifier.fillMaxWidth()) {
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
                        onClick = {
                            onSaveClick(
                                CategoryData(
                                    id = UUID.randomUUID().toString(),
                                    categoryTitle = titleState.value,
                                    categoryColorHex = colorState.value,
                                    createdTime = System.currentTimeMillis()
                                )
                            )
                        }
                    ) { Text(text = "생성하기") }
                }
            }
        }
    }
}