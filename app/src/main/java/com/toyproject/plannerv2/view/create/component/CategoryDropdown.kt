package com.toyproject.plannerv2.view.create.component

import android.graphics.Color.parseColor
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    dropdownItems: List<CategoryData>,
    title: String,
    checkBoxStateList: List<Boolean>,
    onDropdownCheckBoxClick: (Boolean, CategoryData) -> Unit
) {
    val dropdownExpendedState = remember { mutableStateOf(false) }

    Column {
        Row(
            modifier = modifier
                .clickable { dropdownExpendedState.value = true },
            verticalAlignment = Alignment.CenterVertically
        ) {
            // dropdown menu title
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp),
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Icon(
                modifier = Modifier.padding(10.dp),
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "dropdown open"
            )
        }

        val dropdownScrollState = rememberScrollState()
        DropdownMenu(
            modifier = Modifier.background(Color.White),
            expanded = dropdownExpendedState.value,
            onDismissRequest = { dropdownExpendedState.value = false }
        ) {
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .verticalScroll(dropdownScrollState)
            ) {
                Column {
                    dropdownItems.forEachIndexed { index, it ->
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
                            onClick = { dropdownExpendedState.value = false },
                            leadingIcon = {
                                Checkbox(
                                    checked = checkBoxStateList[index],
                                    onCheckedChange = { checked ->
                                        onDropdownCheckBoxClick(checked, it)
                                    }
                                )
                            },
                            trailingIcon = { /* 일부러 비워놔서 checkBox와 맞지 않는 공백을 맞춤. */ }
                        )
                    }
                }
            }
        }
    }
}