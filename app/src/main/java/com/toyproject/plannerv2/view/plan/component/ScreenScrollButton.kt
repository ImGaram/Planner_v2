package com.toyproject.plannerv2.view.plan.component

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.toyproject.plannerv2.R
import com.toyproject.plannerv2.data.PlanData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun BoxScope.ScreenScrollButton(
    plans: List<PlanData>,
    scrollState: LazyListState,
    scrollIsLastState: MutableState<Boolean>,
    scope: CoroutineScope,
) {
    IconButton(
        modifier = Modifier
            .clip(CircleShape)
            .align(Alignment.BottomEnd)
            .padding(end = 20.dp, bottom = 20.dp),
        onClick = {
            scope.launch {
                if (scrollIsLastState.value) scrollState.animateScrollToItem(index = 0)
                else scrollState.animateScrollToItem(index = plans.lastIndex)
            }
        },
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Color(0xFF6EC4A7)
        )
    ) {
        Icon(
            modifier = Modifier
                .padding(10.dp)
                .rotate(if (scrollIsLastState.value) 180f else 0f),
            painter = painterResource(id = R.drawable.ic_scroll_arrow),
            tint = Color.White,
            contentDescription = "down arrow"
        )
    }
}