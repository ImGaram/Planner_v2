package com.project.plannerv2.view.create

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.plannerv2.R
import com.project.plannerv2.data.PlanData
import com.project.plannerv2.view.create.component.CreatePlanCard
import com.project.plannerv2.view.create.component.PlanCard
import com.project.plannerv2.viewmodel.CreatePlanViewModel

@Composable
fun CreatePlanScreen(
    createPlanViewModel: CreatePlanViewModel = viewModel(),
    navigateToPlan: () -> Unit
) {
    val planList = createPlanViewModel.planList
    val planListState = remember { planList }
    var buttonsVisibility by remember { mutableStateOf(false) }

    LaunchedEffect(planListState.toList()) {
        buttonsVisibility = planListState.isNotEmpty() && planListState[0].title.isNotEmpty()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.End
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 15.dp, top = 15.dp, bottom = 10.dp)
        ) {
            IconButton(
                modifier = Modifier.size(36.dp),
                onClick = { navigateToPlan() }
            ) {
                Icon(
                    modifier = Modifier.padding(5.dp),
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = "back arrow"
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "일정 생성",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Text(
            modifier = Modifier.padding(end = 15.dp, bottom = 15.dp),
            text = "생성한 일정을 클릭하여 수정하세요."
        )

        Divider(
            modifier = Modifier
                .height(1.dp)
                .padding(horizontal = 15.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            itemsIndexed(planListState) { index, item ->
                PlanCard(
                    planData = item,
                    savePlanLogic = { title, description ->
                        createPlanViewModel.modifyPlan(
                            title = title,
                            description = description,
                            position = index
                        )
                    },
                    deleteLogic = { createPlanViewModel.removePlan(index) }
                )
            }

            item {
                CreatePlanCard {
                    createPlanViewModel.addPlan(
                        PlanData(
                            title = "",
                            description = ""
                        )
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = buttonsVisibility,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it })
        ) {
            Divider(
                modifier = Modifier
                    .height(1.dp)
                    .padding(horizontal = 15.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 10.dp)
            ) {
                Button(
                    modifier = Modifier.width(100.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6EC4A7)),
                    onClick = { navigateToPlan() }
                ) {
                    Text(text = "취소")
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    modifier = Modifier.width(100.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFDB86)),
                    onClick = { /*TODO :: add firebase save logic */ }
                ) {
                    Text(text = "생성")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreatePlanScreenPreview() {
    CreatePlanScreen {}
}