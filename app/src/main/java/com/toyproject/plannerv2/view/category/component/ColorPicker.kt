package com.toyproject.plannerv2.view.category.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.godaddy.android.colorpicker.ClassicColorPicker
import com.godaddy.android.colorpicker.HsvColor

@Composable
fun ColorPicker(
    modifier: Modifier = Modifier,
    initialColor: Color = Color.White,
    onColorChange: (color: Color) -> Unit
) {
    ClassicColorPicker(
        modifier = modifier,
        color = HsvColor.from(initialColor),
        onColorChanged = { hsvColor: HsvColor ->
            onColorChange(hsvColor.toColor())
        }
    )
}