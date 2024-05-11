package com.toyproject.plannerv2.view.plan

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.toyproject.plannerv2.view.component.card.AddCard
import com.toyproject.plannerv2.view.plan.component.PlanCalendar
import com.toyproject.plannerv2.view.plan.component.ScheduleHeader
import com.toyproject.plannerv2.view.plan.component.ScheduleItem
import com.toyproject.plannerv2.view.plan.component.ScreenScrollButton
import com.toyproject.plannerv2.viewmodel.CategoryViewModel
import com.toyproject.plannerv2.viewmodel.PlanViewModel
import java.time.LocalDate

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun PlanScreen(
    planViewModel: PlanViewModel = viewModel(),
    categoryViewModel: CategoryViewModel = viewModel(),
    navigateToCreatePlan: (String) -> Unit
) {
    val uid = FirebaseAuth.getInstance().uid
    val planState = planViewModel.plans.collectAsState()
    val selectedLocalDate = remember { mutableStateOf(LocalDate.now().toString()) }
    val categoryState = categoryViewModel.categories.collectAsState()

    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val scrollIsLastState = remember { mutableStateOf(false) }      // 스크롤을 더 이상 할수 없는가?(마지막 스크롤인가?)
    val cantScrollForward = !scrollState.canScrollForward       // 앞으로는 더 스크롤할 수 없음
    val cantScrollBackward = !scrollState.canScrollBackward     // 뒤로는 더 스크롤할 수 없음

    LaunchedEffect(Unit) {
        planViewModel.getPlans(uid!!, selectedLocalDate.value)
        categoryViewModel.getCategory(uid = uid.toString())
    }
    
    LaunchedEffect(cantScrollForward, cantScrollBackward) {
        if (cantScrollForward) scrollIsLastState.value = true
        if (cantScrollBackward) scrollIsLastState.value = false
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = scrollState
        ) {
            item {
                PlanCalendar(scope = scope) {
                    selectedLocalDate.value = it
                    planViewModel.getPlans(uid!!, it)
                }
            }

            stickyHeader { ScheduleHeader(date = selectedLocalDate) }

            if (planState.value.isNotEmpty() && !categoryState.value.isNullOrEmpty()) {
                itemsIndexed(planState.value) { position, it ->
                    ScheduleItem(
                        planData = it,
                        categoryData = it.categories.values.map { it },
                        categoryList = categoryState.value!!,
                        onCheckBoxClick = { isCheck ->
                            planViewModel.changePlanCompleteAtIndex(position, isCheck)
                            planViewModel.planCheck(
                                uid = uid!!,
                                documentId = it.createdTime.toString()
                            )
                        },
                        onPlanModify = { title, description ->
                            planViewModel.modifyPlan(
                                uid = uid.toString(),
                                documentId = it.createdTime.toString(),
                                title = title,
                                description = description,
                                onModifySuccess = { planViewModel.getPlans(uid.toString(), selectedLocalDate.value) }
                            )
                        },
                        onPlanDelete = {
                            planViewModel.deletePlan(
                                uid = uid!!,
                                documentId = it.createdTime.toString(),
                                onDeleteSuccess = { planViewModel.getPlans(uid.toString(), selectedLocalDate.value) }
                            )
                        },
                        onCategoryUpdate = { updateValue ->
                            categoryViewModel.updateCategory(
                                uid = uid.toString(),
                                targetPlanDocId = it.createdTime.toString(),
                                categoryValue = updateValue,
                                onUpdateSuccess = {
                                    planViewModel.getPlans(uid.toString(), selectedLocalDate.value)
                                }
                            )
                        }
                    )

                    Divider(
                        modifier = Modifier
                            .height(1.dp)
                            .padding(horizontal = 15.dp)
                    )
                }
            }

            item {
                AddCard(
                    modifier = Modifier.padding(vertical = 7.dp, horizontal = 10.dp),
                    cardTitle = "클릭해서 일정 추가하기..."
                ) {
                    navigateToCreatePlan(selectedLocalDate.value)
                }
            }
        }

        ScreenScrollButton(
            plans = planState.value,
            scrollState = scrollState,
            scrollIsLastState = scrollIsLastState,
            scope = scope
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PlanScreenPreview() {
    PlanScreen {}
}