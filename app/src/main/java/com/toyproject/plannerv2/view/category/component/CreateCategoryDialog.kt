package com.toyproject.plannerv2.view.category.component

import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.toyproject.plannerv2.view.component.textfield.PlannerV2TextField

@Composable
fun CreateCategoryDialog(
    onDismissRequest: () -> Unit,
    onSaveClick: (CategoryData) -> Unit,
    onCancelClick: () -> Unit,
) {
    val titleState = remember { mutableStateOf("") }
    val colorState = remember { mutableStateOf("#FFFFFFFF") }

    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 1.dp, shape = RoundedCornerShape(8.dp))
                .background(Color(0xFFFAFAFA))
                .clip(RoundedCornerShape(8.dp))
                .padding(10.dp),
        ) {
            Text(
                text = "카테고리 생성하기",
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
                        .background(Color(android.graphics.Color.parseColor(colorState.value))),
                )
            }

            Divider()

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
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6EC4A7)),
                    onClick = onCancelClick
                ) { Text(text = "취소") }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFDB86)),
                    onClick = {
                        onSaveClick(
                            CategoryData(
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

private fun Color.toHexCode(): String {
    val alpha = alpha * 255
    val red = red * 255
    val green = green * 255
    val blue = blue * 255

    return String.format("#%02x%02x%02x%02x", alpha.toInt(), red.toInt(), green.toInt(), blue.toInt())
}