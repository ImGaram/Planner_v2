package com.toyproject.plannerv2.view.category.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toyproject.plannerv2.data.CategoryData

@Composable
fun CategoryItem(categoryData: CategoryData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(15.dp)
                .background(Color(android.graphics.Color.parseColor(categoryData.categoryColorHex)))
        )

        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = categoryData.categoryTitle,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.padding(end = 10.dp),
                    text = "일정 ${categoryData.savedPlanCount}개",
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Icon(
                    modifier = Modifier
                        .padding(5.dp)
                        .clickable {

                        },
                    imageVector = Icons.Default.Create,
                    contentDescription = "category modify",
                    tint = Color(0xFFFFDB86)
                )

                Icon(
                    modifier = Modifier
                        .padding(5.dp)
                        .clickable {

                        },
                    imageVector = Icons.Default.Delete,
                    tint = Color.Red,
                    contentDescription = "category delete"
                )
            }
        }
    }
}