package com.toyproject.plannerv2.view.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toyproject.plannerv2.view.ui.theme.PlannerTheme

@Composable
fun PlannerV2TextField(
    modifier: Modifier = Modifier,
    value: String,
    hint: String,
    singleLine: Boolean,
    maxLines: Int,
    textContentAlignment: Alignment = Alignment.CenterStart,
    onValueChange: (String) -> Unit
) {
    var textState by remember { mutableStateOf(value) }

    BasicTextField(
        modifier = modifier,
        value = textState,
        onValueChange = {
            textState = it
            onValueChange(textState)
        },
        singleLine = singleLine,
        maxLines = maxLines,
        textStyle = TextStyle(
            color = PlannerTheme.colors.primary,
            fontSize = 19.sp
        )
    ) { innerTextField ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp))
                .background(PlannerTheme.colors.gray200),
            contentAlignment = textContentAlignment
        ) {
            Box(modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp)) {
                if (textState.isEmpty())
                    Text(
                        text = hint,
                        color = PlannerTheme.colors.gray400,
                        style = TextStyle(fontSize = 19.sp)
                    )
                innerTextField()
            }
        }
    }
}