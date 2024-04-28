package com.toyproject.plannerv2.view.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun PlannerV2TextField(
    modifier: Modifier = Modifier,
    value: String,
    hint: String,
    singleLine: Boolean,
    maxLines: Int,
    textStyle: TextStyle = TextStyle(),
    hintTextStyle: TextStyle = TextStyle(),
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
        textStyle = textStyle
    ) { innerTextField ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFFF3F3F3))
        ) {
            if (textState.isEmpty())
                Text(
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp),
                    text = hint,
                    color = Color(0xFF9B9B9B),
                    style = hintTextStyle
                )
            Box(modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp)) {
                innerTextField()
            }
        }
    }
}