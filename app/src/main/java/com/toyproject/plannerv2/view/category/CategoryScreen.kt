package com.toyproject.plannerv2.view.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.toyproject.plannerv2.view.category.component.CategoryItem
import com.toyproject.plannerv2.view.category.component.CreateCategoryDialog
import com.toyproject.plannerv2.view.component.card.AddCard
import com.toyproject.plannerv2.view.component.progress.CircularProgressScreen
import com.toyproject.plannerv2.viewmodel.CategoryViewModel

@Composable
fun CategoryScreen(categoryViewModel: CategoryViewModel = viewModel()) {
    val uid = FirebaseAuth.getInstance().uid
    val categoryList = categoryViewModel.categories.collectAsState()
    val createDialogState = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        categoryViewModel.getCategory(uid.toString())
    }

    Column {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 15.dp, top = 15.dp, bottom = 10.dp),
            text = "카테고리",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.End
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 15.dp, bottom = 15.dp),
            text = "카테고리를 수정하거나 삭제합니다.",
            textAlign = TextAlign.End
        )

        Divider(
            modifier = Modifier
                .height(1.dp)
                .padding(horizontal = 15.dp)
        )

        if (categoryList.value != null) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 5.dp),
                contentPadding = PaddingValues(horizontal = 15.dp),
            ) {
                items(categoryList.value!!) {
                    CategoryItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(10.dp),
                        categoryData = it,
                        onModifyClick = { title, color ->

                        },
                        onDeleteClick = {

                        }
                    )

                    Divider(
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                            .height(0.5.dp)
                    )
                }

                item {
                    AddCard(cardTitle = "클릭해서 카테고리 생성하기...") {
                        createDialogState.value = true
                    }
                }
            }
        } else CircularProgressScreen()
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