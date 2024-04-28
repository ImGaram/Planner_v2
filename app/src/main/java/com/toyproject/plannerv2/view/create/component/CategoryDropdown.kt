package com.toyproject.plannerv2.view.create.component

import android.graphics.Color.parseColor
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.toyproject.plannerv2.data.CategoryData

@Composable
fun CategoryDropdown(
    modifier: Modifier = Modifier,
    onMenuClick: (String) -> Unit,
) {
    val selectedCategoryState = remember { mutableStateMapOf<String, Map<String, Any>>() }
    val dropdownState = remember { mutableStateOf(false) }
    val selectState = remember { mutableStateOf("카테고리 선택...") }
    val selectColorState = remember { mutableStateOf("#FFC5C5C5") }

    Column {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = selectState.value.ifEmpty { "카테고리 선택..." }
            )

            Icon(
                modifier = Modifier
                    .padding(10.dp)
                    .clickable { dropdownState.value = true },
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "dropdown open"
            )
        }

        DropdownMenu(
            modifier = Modifier,
            expanded = dropdownState.value,
            onDismissRequest = { dropdownState.value = false }
        ) {
            // firestore category data로 교체
            val categoryList = listOf(
                CategoryData(categoryTitle = "카테고리 1", savedPlanCount = 3, categoryColorHex = "#FF0000"),
                CategoryData(categoryTitle = "카테고리 2", savedPlanCount = 6, categoryColorHex = "#FFFF00"),
            )

            categoryList.forEach {
                val menuItemCheckBoxState = remember { mutableStateOf(false) }

                DropdownMenuItem(
                    text = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = it.categoryTitle
                        )
                    },
                    onClick = {
                        onMenuClick(it.categoryTitle)
                        selectState.value = it.categoryTitle
                        selectColorState.value = it.categoryColorHex
                        dropdownState.value = false
                    },
                    leadingIcon = {
                        Checkbox(
                            checked = menuItemCheckBoxState.value,
                            onCheckedChange = { checked ->
                                menuItemCheckBoxState.value = checked
                                if (checked) selectedCategoryState[it.categoryTitle] = mapOf("title" to it.categoryTitle, "color" to it.categoryColorHex)
                                else selectedCategoryState.remove(it.categoryTitle)
                                selectState.value = selectedCategoryState.keys.joinToString(", ")
                            }
                        )
                    },
                    trailingIcon = {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .background(Color(parseColor(it.categoryColorHex)))
                        )
                    }
                )
            }
        }
    }
}