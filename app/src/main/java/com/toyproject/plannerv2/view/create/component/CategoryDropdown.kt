package com.toyproject.plannerv2.view.create.component

import android.graphics.Color.parseColor
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.toyproject.plannerv2.data.CategoryData

@Composable
fun CategoryDropdown(
    modifier: Modifier = Modifier,
    dropdownMenuItem: List<CategoryData>,
    onMenuClick: (String) -> Unit,
) {
    val selectedCategoryState = remember { mutableStateMapOf<String, Map<String, Any>>() }
    val menuItemCheckBoxState = remember { mutableStateOf(Array(dropdownMenuItem.size) { false }) }     // 생성할 일정의 dropdown menu의 checkbox state를 관리함.
    val dropdownState = remember { mutableStateOf(false) }
    val selectState = remember { mutableStateOf("카테고리 선택...") }
    val selectColorState = remember { mutableStateOf("#FFC5C5C5") }

    Column {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp),
                text = selectState.value.ifEmpty { "카테고리 선택..." },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
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
            modifier = Modifier.background(Color.White),
            expanded = dropdownState.value,
            onDismissRequest = { dropdownState.value = false }
        ) {
            dropdownMenuItem.forEachIndexed { index, it ->
                DropdownMenuItem(
                    text = {
                        Text(
                            modifier = Modifier
                                .padding(start = 7.dp)
                                .fillMaxWidth(),
                            text = it.categoryTitle,
                            color = Color(parseColor(it.categoryColorHex))
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
                            checked = menuItemCheckBoxState.value[index],
                            onCheckedChange = { checked ->
                                menuItemCheckBoxState.value[index] = checked

                                if (checked) selectedCategoryState[it.categoryTitle] = mapOf("title" to it.categoryTitle, "color" to it.categoryColorHex)
                                else selectedCategoryState.remove(it.categoryTitle)
                                selectState.value = selectedCategoryState.keys.joinToString(", ")
                            }
                        )
                    },
                    trailingIcon = { /* 일부러 비워놔서 checkBox와 맞지 않는 공백을 맞춤. */ }
                )
            }
        }
    }
}