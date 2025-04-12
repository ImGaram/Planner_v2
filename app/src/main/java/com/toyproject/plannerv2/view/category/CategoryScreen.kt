package com.toyproject.plannerv2.view.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.toyproject.plannerv2.data.CategoryData
import com.toyproject.plannerv2.view.category.component.CategoryItem
import com.toyproject.plannerv2.view.category.component.CreateCategoryDialog
import com.toyproject.plannerv2.view.component.card.AddCard
import com.toyproject.plannerv2.view.ui.theme.PlannerTheme
import com.toyproject.plannerv2.viewmodel.CategoryViewModel

@Composable
fun CategoryScreen(categoryViewModel: CategoryViewModel = viewModel()) {
    val uid = FirebaseAuth.getInstance().uid
    val categoryList = categoryViewModel.categories.collectAsState()
    val createDialogState = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        categoryViewModel.getCategory(uid.toString())
    }

    Column(modifier = Modifier.background(PlannerTheme.colors.background)) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, top = 15.dp, bottom = 10.dp),
            text = "카테고리",
            color = PlannerTheme.colors.primary,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, bottom = 15.dp),
            text = "카테고리를 수정하거나 삭제합니다.",
            color = PlannerTheme.colors.primary
        )

        HorizontalDivider(
            modifier = Modifier
                .height(1.dp)
                .padding(horizontal = 15.dp),
            color = PlannerTheme.colors.gray300
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 5.dp),
            contentPadding = PaddingValues(horizontal = 15.dp),
        ) {
            items(categoryList.value) {
                CategoryItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(10.dp),
                    categoryData = it,
                    onModifyClick = { title, color ->
                        val modifyCategoryData = CategoryData(
                            id = it.id,
                            categoryTitle = title,
                            categoryColorHex = color,
                            createdTime = it.createdTime
                        )
                        categoryViewModel.modifyCategory(
                            uid = uid.toString(),
                            modifyValue = modifyCategoryData,
                            onSuccess = { categoryViewModel.getCategory(uid = uid.toString()) }
                        )
                    },
                    onDeleteClick = {
                        categoryViewModel.deleteCategory(
                            uid = uid.toString(),
                            deleteId = it.createdTime.toString(),
                            deletePlanTargetId = it.id,
                            onSuccess = { categoryViewModel.getCategory(uid = uid.toString()) }
                        )
                    }
                )

                HorizontalDivider(
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .height(0.5.dp),
                    color = PlannerTheme.colors.gray300
                )
            }

            item {
                AddCard(cardTitle = "클릭해서 카테고리 생성하기...") {
                    createDialogState.value = true
                }
            }
        }
    }

    if (createDialogState.value) {
        CreateCategoryDialog(
            onDismissRequest = { createDialogState.value = false },
            onSaveClick = {
                categoryViewModel.createCategory(
                    uid = uid.toString(),
                    categoryData = it
                )
                categoryViewModel.getCategory(uid = uid.toString())
                createDialogState.value = false
            },
            onCancelClick = { createDialogState.value = false }
        )
    }
}