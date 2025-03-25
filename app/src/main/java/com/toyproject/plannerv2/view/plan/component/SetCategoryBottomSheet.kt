package com.toyproject.plannerv2.view.plan.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.toyproject.plannerv2.data.CategoryData
import com.toyproject.plannerv2.view.ui.theme.PlannerTheme
import kotlinx.coroutines.launch
import androidx.core.graphics.toColorInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetCategoryBottomSheet(
    categoryList: List<CategoryData> = listOf(),
    selectedCategories: Collection<Map<String, Any>>,
    onDismissRequest: () -> Unit,
    onSaveClick: (Map<String, Map<String, Any>>) -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState()
    val updateCategoryMap = mutableMapOf<String, Map<String, Any>>()    // 카테고리를 업데이트할 변수.
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        sheetState = bottomSheetState,
        onDismissRequest = onDismissRequest,
        dragHandle = null,
        shape = RectangleShape
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(PlannerTheme.colors.cardBackground)
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = "카테고리 변경",
                color = PlannerTheme.colors.primary
            )

            HorizontalDivider(color = PlannerTheme.colors.gray300)

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                items(categoryList) {
                    val checkedState = remember { mutableStateOf(selectedCategories.map { map ->  map["title"] }.contains(it.categoryTitle)) }
                    if (checkedState.value) updateCategoryMap[it.id] = mapOf(
                        "title" to it.categoryTitle,
                        "color" to it.categoryColorHex,
                    )

                    CategoryBottomSheetItem(
                        categoryData = it,
                        checked = checkedState.value,
                        onCheckedChange = { checked ->
                            checkedState.value = checked
                            if (checked) {
                                updateCategoryMap[it.id] = mapOf(
                                    "title" to it.categoryTitle,
                                    "color" to it.categoryColorHex,
                                )
                            } else updateCategoryMap.remove(it.id)
                        }
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(top = 10.dp),
                color = PlannerTheme.colors.gray300
            )

            Button(
                modifier = Modifier.padding(10.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PlannerTheme.colors.green),
                onClick = {
                    onSaveClick(updateCategoryMap)
                    scope.launch {
                        bottomSheetState.hide()
                    }
                }
            ) { Text(text = "완료") }
        }
    }
}

@Composable
fun CategoryBottomSheetItem(
    categoryData: CategoryData,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = PlannerTheme.colors.green
            )
        )

        Text(
            modifier = Modifier.weight(1f),
            text = categoryData.categoryTitle,
            color = PlannerTheme.colors.primary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Box(
            modifier = Modifier
                .padding(end = 15.dp)
                .size(20.dp)
                .background(Color(categoryData.categoryColorHex.toColorInt()))
        )
    }
}