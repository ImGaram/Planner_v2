package com.toyproject.plannerv2.view.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toyproject.plannerv2.view.setting.component.SettingItem
import com.toyproject.plannerv2.view.ui.theme.PlannerTheme

@Composable
fun SettingScreen(navigateToStatistics: () -> Unit) {
    val context = LocalContext.current
    val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = navigateToStatistics,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "exit setting"
                )
            }

            Text(
                text = "설정",
                color = PlannerTheme.colors.primary,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        SettingItem(
            onClick = {},
            title = "개인정보처리방침"
        )

        SettingItem(
            onClick = {},
            title = "앱 버전",
            subTitle = packageInfo.versionName.toString()
        )

        Button(
            onClick = {

            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PlannerTheme.colors.green)
        ) {
            Text(text = "로그아웃")
        }
    }
}

@Preview
@Composable
private fun SettingScreenPreview() {
    SettingScreen {}
}