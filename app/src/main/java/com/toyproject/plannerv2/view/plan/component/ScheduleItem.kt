package com.toyproject.plannerv2.view.plan.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toyproject.plannerv2.data.CategoryData
import com.toyproject.plannerv2.data.PlanData
import com.toyproject.plannerv2.view.ui.theme.PlannerTheme
import androidx.core.graphics.toColorInt

@Composable
fun ScheduleItem(
    planData: PlanData,
    categoryData: List<Map<String, Any>>,
    categoryList: List<CategoryData>,
    onCheckBoxClick: (Boolean) -> Unit,
    onPlanModify: (title: String, description: String) -> Unit,
    onPlanDelete: () -> Unit,
    onCategoryUpdate: (Map<String, Map<String, Any>>) -> Unit = {},
) {
    val bottomSheetState = remember { mutableStateOf(false) }
    val dialogState = remember { mutableStateOf(false) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(PlannerTheme.colors.background),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = planData.complete,
                onCheckedChange = { onCheckBoxClick(!planData.complete) },
                modifier = Modifier.padding(vertical = 7.dp, horizontal = 10.dp),
                colors = CheckboxDefaults.colors(
                    checkedColor = PlannerTheme.colors.green,
                    uncheckedColor = PlannerTheme.colors.primaryA25
                )
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 10.dp)
            ) {
                Text(
                    text = planData.title,
                    color = PlannerTheme.colors.primary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = planData.description,
                    color = PlannerTheme.colors.primary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Thin,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                LazyRow(
                    modifier = Modifier.padding(top = 5.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(categoryData.sortedBy { it["title"].toString() }) {map ->
                        CategoryBadge(title = map["title"].toString(), colorHex = map["color"].toString())
                    }
                }
            }

            IconButton(
                modifier = Modifier.padding(horizontal = 10.dp),
                onClick = { planData.isMenuOpen = !planData.isMenuOpen }
            ) {
                Icon(
                    modifier = Modifier.rotate(if (planData.isMenuOpen) 180f else 0f),
                    imageVector = Icons.Default.KeyboardArrowDown,
                    tint = PlannerTheme.colors.primaryA25,
                    contentDescription = "more icon"
                )
            }
        }

        if (planData.isMenuOpen) {
            val titleList = listOf("일정 수정하기", "카테고리 설정하기", "일정 삭제하기")
            val itemColorList = listOf(PlannerTheme.colors.yellow, PlannerTheme.colors.green, PlannerTheme.colors.red)
            val iconList = listOf(Icons.Default.DateRange, Icons.AutoMirrored.Filled.List, Icons.Default.Delete)

            titleList.forEachIndexed { index, title ->
                HorizontalDivider(
                    modifier = Modifier
                        .height(0.5.dp)
                        .padding(horizontal = 15.dp),
                    color = PlannerTheme.colors.primaryA25
                )

                PlanMenuItem(
                    titleIconImageVector = iconList[index],
                    itemColor = itemColorList[index],
                    menuTitle = title
                ) {
                    when (title) {
                        "일정 수정하기" -> dialogState.value = true
                        "카테고리 설정하기" -> bottomSheetState.value = true
                        "일정 삭제하기" -> onPlanDelete()
                    }
                }
            }
        }

        if (dialogState.value) {
            PlanModifyDialog(
                planData = planData,
                onDismissRequest = { dialogState.value = false },
                onSaveClick = { title, description ->
                    dialogState.value = false
                    onPlanModify(title, description)
                },
                onCancelClick =  { dialogState.value = false }
            )
        }

        if (bottomSheetState.value) {
            SetCategoryBottomSheet(
                categoryList = categoryList,
                selectedCategories = planData.categories.values,
                onDismissRequest = {
                    bottomSheetState.value = false
                },
                onSaveClick = {
                    onCategoryUpdate(it)
                    bottomSheetState.value = false
                }
            )
        }
    }
}

@Composable
fun CategoryBadge(title: String, colorHex: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(Color(colorHex.toColorInt()))
        )

        Text(
            modifier = Modifier.padding(start = 5.dp),
            color = PlannerTheme.colors.primary,
            text = title,
            fontSize = 9.sp,
            fontWeight = FontWeight.Thin
        )
    }
}