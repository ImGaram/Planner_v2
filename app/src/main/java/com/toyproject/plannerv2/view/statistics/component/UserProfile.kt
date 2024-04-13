package com.toyproject.plannerv2.view.statistics.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

@Composable
fun UserProfile(
    modifier: Modifier = Modifier,
    account: GoogleSignInAccount?
) {
    Row(modifier = modifier.padding(15.dp)) {
        Image(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(Color.Gray),
            painter = rememberAsyncImagePainter(model = account?.photoUrl),
            contentDescription = "google account profile image"
        )

        var textStyleState by remember {
            mutableStateOf(
                TextStyle(
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        var readyToDraw by remember { mutableStateOf(false) }
        Column(modifier = Modifier.padding(horizontal = 15.dp)) {
            val userName = "${account?.familyName}${account?.givenName}"
            Text(
                text = "환영합니다. ${userName}님!",
                modifier = Modifier.drawWithContent {
                    if (readyToDraw) drawContent()
                },
                softWrap = false,
                style = textStyleState,
                maxLines = 1,
                onTextLayout = {
                    if (it.didOverflowWidth) textStyleState = textStyleState.copy(fontSize = textStyleState.fontSize * 0.9)
                    else readyToDraw = true
                }
            )
            Text(
                text = account?.email.toString(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}