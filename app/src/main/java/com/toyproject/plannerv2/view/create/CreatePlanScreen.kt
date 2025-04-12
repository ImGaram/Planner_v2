package com.toyproject.plannerv2.view.create

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.toyproject.plannerv2.data.PlanData
import com.toyproject.plannerv2.util.stringToUnixTimestamp
import com.toyproject.plannerv2.view.create.component.CreatePlanCard
import com.toyproject.plannerv2.view.create.component.PlanCard
import com.toyproject.plannerv2.view.ui.theme.PlannerTheme
import com.toyproject.plannerv2.viewmodel.CategoryViewModel
import com.toyproject.plannerv2.viewmodel.CreatePlanViewModel

@Composable
fun CreatePlanScreen(
    createPlanViewModel: CreatePlanViewModel = viewModel(),
    categoryViewModel: CategoryViewModel = viewModel(),
    selectedDate: String?,
    navigateToPlan: () -> Unit
) {
    val planList = createPlanViewModel.planList.collectAsState()
    val categoryList = categoryViewModel.categories.collectAsState()
    var buttonsVisibility by remember { mutableStateOf(false) }

    val uid = FirebaseAuth.getInstance().uid
    LaunchedEffect(Unit) {
        categoryViewModel.getCategory(uid = uid.toString())
    }

    LaunchedEffect(planList.value) {
        var titleIsEmpty = false
        repeat(planList.value.size) { titleIsEmpty = planList.value[it].title.isEmpty() }
        buttonsVisibility = planList.value.isNotEmpty() && !titleIsEmpty
    }

    Column(modifier = Modifier.fillMaxSize()) {
        CreatePlanTitle(navigateToPlan = navigateToPlan)

        HorizontalDivider(
            modifier = Modifier
                .height(1.dp)
                .padding(horizontal = 15.dp),
            color = PlannerTheme.colors.gray300
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            itemsIndexed(planList.value) { index, item ->
                PlanCard(
                    planData = item,
                    dropdownMenuItem = categoryList.value,
                    savePlanLogic = { title, description, categories ->
                        val modifyData = PlanData(
                            baseDate = selectedDate?.stringToUnixTimestamp(),
                            title = title,
                            description = description,
                            categories = categories
                        )
                        createPlanViewModel.modifyPlan(
                            position = index,
                            modifyData = modifyData
                        )
                    },
                    deleteLogic = { createPlanViewModel.removePlan(index) }
                )
            }

            item {
                CreatePlanCard {
                    createPlanViewModel.addPlan(PlanData())
                }
            }
        }

        AnimatedVisibility(
            visible = buttonsVisibility,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it })
        ) {
            HorizontalDivider(
                modifier = Modifier
                    .height(1.dp)
                    .padding(horizontal = 15.dp),
                color = PlannerTheme.colors.gray300
            )

            SaveCancelButtons(
                navigateToPlan = navigateToPlan,
                savePlanLogic = {
                    if (!selectedDate.isNullOrEmpty()) {
                        createPlanViewModel.savePlan(
                            plans = planList.value,
                            uid = uid.toString(),
                            baseDate = selectedDate,
                            navigateToPlan = navigateToPlan
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun CreatePlanTitle(navigateToPlan: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 15.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier.size(48.dp),
            onClick = { navigateToPlan() }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "back arrow",
                tint = PlannerTheme.colors.primary
            )
        }

        Text(
            text = "일정 생성",
            color = PlannerTheme.colors.primary,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )
    }

    Text(
        modifier = Modifier.padding(start = 15.dp, bottom = 15.dp),
        text = "생성한 일정을 클릭하여 수정하세요.",
        color = PlannerTheme.colors.primary
    )
}

@Composable
fun SaveCancelButtons(
    navigateToPlan: () -> Unit,
    savePlanLogic: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp)
    ) {
        Button(
            modifier = Modifier.width(100.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PlannerTheme.colors.green),
            onClick = { navigateToPlan() }
        ) {
            Text(text = "취소")
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier.width(100.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PlannerTheme.colors.yellow),
            onClick = { savePlanLogic() }
        ) {
            Text(text = "생성")
        }
    }
}