package com.toyproject.plannerv2.view.login.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toyproject.plannerv2.R
import com.toyproject.plannerv2.view.ui.theme.PlannerTheme

@Composable
fun GoogleSignInButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = CircleShape,
        border = BorderStroke(width = 1.dp, color = Color.Gray)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(PlannerTheme.colors.primaryVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_google),
                contentDescription = "google login",
                modifier = Modifier
                    .size(28.dp),
                tint = Color.Unspecified
            )
        }
    }
}

@Preview
@Composable
fun GoogleSignInButtonPreview() {
    GoogleSignInButton {}
}