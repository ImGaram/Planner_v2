package com.toyproject.plannerv2.view.category.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toyproject.plannerv2.data.CategoryData
import com.toyproject.plannerv2.util.unixTimestampToLocalDateTime
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    categoryData: CategoryData,
    onModifyClick: (String, String) -> Unit,
    onDeleteClick: () -> Unit
) {
    val categoryModifyState = remember { mutableStateOf(false) }
    val categoryDeleteState = remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .width(10.dp)
                    .fillMaxHeight()
                    .clip(CircleShape)
                    .background(Color(android.graphics.Color.parseColor(categoryData.categoryColorHex)))
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .weight(1f)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = categoryData.categoryTitle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "생성일: ${formatLocalDateTime(categoryData.createdTime?.unixTimestampToLocalDateTime())}",
                    fontSize = 10.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Icon(
                modifier = Modifier
                    .padding(end = 5.dp)
                    .clip(CircleShape)
                    .clickable { categoryModifyState.value = true },
                imageVector = Icons.Default.Create,
                contentDescription = "category modify",
                tint = Color(0xFFFFDB86)
            )

            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { categoryDeleteState.value = true },
                imageVector = Icons.Default.Delete,
                tint = Color.Red,
                contentDescription = "category delete"
            )
        }
    }

    if (categoryModifyState.value) {
        CategoryModifyDialog(
            categoryData = categoryData,
            onDismissRequest = { categoryModifyState.value = false },
            onSaveClick = { title, color ->
                onModifyClick(title, color)
                categoryModifyState.value = false
            },
            onCancelClick = { categoryModifyState.value = false }
        )
    }

    if (categoryDeleteState.value) {
        CategoryDeleteDialog(
            onDismissRequest = { categoryDeleteState.value = false },
            onDeleteClick = {
                onDeleteClick()
                categoryDeleteState.value = false
            },
            onCancelClick = { categoryDeleteState.value = false }
        )
    }
}

private fun formatLocalDateTime(localDateTime: LocalDateTime?): String {
    val pattern = DateTimeFormatter.ofPattern("yyyy년 M월 d일 a H시 m분", Locale.KOREAN)
    return localDateTime?.format(pattern).toString()
}