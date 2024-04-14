package com.toyproject.plannerv2.view.plan

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
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
import com.toyproject.plannerv2.application.PlannerV2Application
import com.toyproject.plannerv2.view.plan.component.AddScheduleCard
import com.toyproject.plannerv2.view.plan.component.PlanCalendar
import com.toyproject.plannerv2.view.plan.component.PlanCardBackground
import com.toyproject.plannerv2.view.plan.component.ScheduleHeader
import com.toyproject.plannerv2.view.plan.component.ScheduleItem
import com.toyproject.plannerv2.view.plan.component.ScreenScrollButton
import com.toyproject.plannerv2.viewmodel.PlanViewModel

@Composable
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
fun PlanScreen(
    planViewModel: PlanViewModel = viewModel(),
    navigateToCreatePlan: () -> Unit
) {
    val uid = FirebaseAuth.getInstance().uid
    val planState = planViewModel.plans

    val dataStore = PlannerV2Application.getInstance().getDataStore()
    val dateFlow = dataStore.dateFlow.collectAsState(initial = "")
    val dateState = remember { mutableStateOf(dateFlow.value) }

    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val scrollIsLastState = remember { mutableStateOf(false) }      // 스크롤을 더 이상 할수 없는가?(마지막 스크롤인가?)
    val cantScrollForward = !scrollState.canScrollForward       // 앞으로는 더 스크롤할 수 없음
    val cantScrollBackward = !scrollState.canScrollBackward     // 뒤로는 더 스크롤할 수 없음

    LaunchedEffect(Unit) {
        if (!dateFlow.value.isNullOrEmpty()) {
            dateState.value = dateFlow.value
            planViewModel.getPlans(uid!!, dateState.value!!)
        }
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
                PlanCalendar(
                    date = dateState,
                    scope = scope,
                    dataStore = dataStore
                ) {
                    planViewModel.getPlans(uid!!, dateState.value!!)
                }
            }

            stickyHeader { ScheduleHeader(date = dateState) }

            if (planState.isNotEmpty()) {
                itemsIndexed(planState) { position, it ->
                    val dismissState = rememberDismissState(
                        confirmValueChange = { value ->
                            if (value == DismissValue.DismissedToStart) {
                                planViewModel.deletePlan(
                                    uid = uid!!,
                                    documentId = it.createdTime.toString()
                                )
                                true
                            } else false
                        }
                    )
                    SwipeToDismiss(
                        state = dismissState,
                        background = {
                            PlanCardBackground(swipeDismissState = dismissState)
                        },
                        dismissContent = {
                            ScheduleItem(
                                planData = it,
                                onCheckBoxClick = { isCheck ->
                                    planViewModel.changePlanCompleteAtIndex(position, isCheck)
                                    planViewModel.planCheck(
                                        uid = uid!!,
                                        documentId = it.createdTime.toString()
                                    )
                                }
                            )
                        },
                        directions = setOf(DismissDirection.EndToStart)     // 닫을 수 있는 방향을 왼쪽 -> 오른쪽만 가능하게 설정
                    )

                    Divider(
                        modifier = Modifier
                            .height(1.dp)
                            .padding(horizontal = 15.dp)
                    )
                }
            }

            item { AddScheduleCard { navigateToCreatePlan() } }
        }

        ScreenScrollButton(
            plans = planState,
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