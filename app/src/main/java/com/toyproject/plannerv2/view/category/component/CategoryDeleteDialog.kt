package com.toyproject.plannerv2.view.category.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun CategoryDeleteDialog(
    onDismissRequest: () -> Unit,
    onDeleteClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 1.dp, shape = RoundedCornerShape(8.dp))
                .background(Color(0xFFFAFAFA))
                .clip(RoundedCornerShape(8.dp))
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(vertical = 15.dp),
                text = "카테고리를 삭제하시겠습니까?",
                fontSize = 20.sp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                Button(
                    modifier = Modifier.width(100.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6EC4A7)),
                    onClick = onCancelClick
                ) { Text(text = "취소") }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFDB86)),
                    onClick = onDeleteClick
                ) { Text(text = "확인") }
            }
        }
    }
}