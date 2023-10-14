package com.project.plannerv2.view.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.plannerv2.R
import com.project.plannerv2.view.login.component.GoogleSignInButton

@Composable
fun LoginScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.bg_login),
                contentScale = ContentScale.FillBounds
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(3f))

        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = R.drawable.ic_app_logo),
            contentDescription = "app logo"
        )

        Spacer(modifier = Modifier.weight(0.5f))

        Text(
            modifier = Modifier
                .background(Color(0xCCFFF6A7))
                .padding(5.dp),
            text = "Schedule Planner",
            style = TextStyle(
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium
            )
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            modifier = Modifier
                .background(Color(0xCCFFF6A7))
                .padding(5.dp),
            text = "오늘 일정 관리",
            fontSize = 19.sp
        )

        Spacer(modifier = Modifier.weight(3f))

        GoogleSignInButton {

        }

        Spacer(modifier = Modifier.weight(2f))
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}