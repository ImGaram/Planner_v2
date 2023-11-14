package com.project.plannerv2.view.plan

import android.widget.CalendarView
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.project.plannerv2.R
import com.project.plannerv2.view.plan.component.AddScheduleCard
import com.project.plannerv2.view.plan.component.ScheduleHeader
import com.project.plannerv2.view.plan.component.ScheduleItem
import com.project.plannerv2.viewmodel.PlanViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun PlanScreen(
    planViewModel: PlanViewModel = viewModel(),
    navigateToCreatePlan: () -> Unit
) {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
    val date = Date()
    var yearState by rememberSaveable { mutableStateOf(formatter.format(date).split("-").first()) }
    var monthState by rememberSaveable { mutableStateOf(formatter.format(date).split("-")[1]) }
    var dayState by rememberSaveable { mutableStateOf(formatter.format(date).split("-").last()) }

    val plans = planViewModel.plans.collectAsState()
    val uid = FirebaseAuth.getInstance().uid
    var selectedDate by remember { mutableStateOf("$yearState-$monthState-$dayState") }

    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var scrollIsLastState by remember { mutableStateOf(false) }     // 스크롤을 더 이상 할수 없는가?(마지막 스크롤인가?)

    val cantScrollForward = !scrollState.canScrollForward       // 앞으로는 더 스크롤할 수 없음
    val cantScrollBackward = !scrollState.canScrollBackward     // 뒤로는 더 스크롤할 수 없음

    LaunchedEffect(key1 = cantScrollForward, key2 = cantScrollBackward) {
        if (cantScrollForward) scrollIsLastState = true
        if (cantScrollBackward) scrollIsLastState = false
    }

    LaunchedEffect(yearState, monthState, dayState) {
        selectedDate = "${yearState}-${monthState}-${dayState}"
        planViewModel.getPlans(uid!!, selectedDate)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = scrollState
        ) {
            item {
                AndroidView(
                    modifier = Modifier.fillMaxWidth(),
                    factory = { CalendarView(it) }
                ) { calendarView ->
                    calendarView.date = formatter.parse(selectedDate)!!.time

                    calendarView.setOnDateChangeListener { _, year, month, day ->
                        yearState = year.toString()
                        monthState = (month + 1).toString()
                        dayState = day.toString()
                    }
                }
            }

            stickyHeader { ScheduleHeader(month = monthState, day = dayState) }

            if (plans.value != null) {
                items(plans.value!!) {
                    ScheduleItem(
                        planData = it,
                        checked = it.complete,
                        onCheckBoxClick = {

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
                AddScheduleCard { navigateToCreatePlan() }
            }
        }

        IconButton(
            modifier = Modifier
                .clip(CircleShape)
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 20.dp),
            onClick = {
                scope.launch {
                    if (scrollIsLastState) scrollState.animateScrollToItem(index = 0)
                    else scrollState.animateScrollToItem(index = plans.value!!.lastIndex)
                }
            },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color(0xFF6EC4A7)
            )
        ) {
            Icon(
                modifier = Modifier
                    .padding(10.dp)
                    .rotate(if (scrollIsLastState) 180f else 0f),
                painter = painterResource(id = R.drawable.ic_scroll_arrow),
                tint = Color.White,
                contentDescription = "down arrow"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlanScreenPreview() {
    PlanScreen {}
}