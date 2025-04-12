package com.toyproject.plannerv2.view.login

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.toyproject.plannerv2.R
import com.toyproject.plannerv2.view.login.component.GoogleSignInButton
import com.toyproject.plannerv2.view.ui.theme.PlannerTheme
import com.toyproject.plannerv2.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel(),
    navigateToPlan: () -> Unit
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        loginViewModel.login(
            activityResult = it,
            onSuccess = {
                Toast.makeText(context, "로그인이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                navigateToPlan()
            },
            onFailure = {
                Toast.makeText(context, "로그인에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
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
                .background(PlannerTheme.colors.yellow)
                .padding(5.dp),
            text = "Schedule Planner",
            color = PlannerTheme.colors.primary,
            style = TextStyle(
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium
            )
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            modifier = Modifier
                .background(PlannerTheme.colors.yellow)
                .padding(5.dp),
            text = "오늘 일정 관리",
            color = PlannerTheme.colors.primary,
            fontSize = 19.sp
        )

        Spacer(modifier = Modifier.weight(3f))

        Row(
            modifier = Modifier.padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(PlannerTheme.colors.gray400)
            )

            Text(
                text = "소셜 계정으로 간편 로그인",
                modifier = Modifier.padding(horizontal = 12.dp),
                color = PlannerTheme.colors.primary
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(PlannerTheme.colors.gray400)
            )
        }

        val token = stringResource(id = R.string.default_web_client_id)
        GoogleSignInButton(
            modifier = Modifier.padding(top = 24.dp),
            onClick = {
                val googleSignInOptions = GoogleSignInOptions
                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(token)
                    .requestEmail()
                    .build()

                val googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)
                launcher.launch(googleSignInClient.signInIntent)
            }
        )

        Spacer(modifier = Modifier.weight(2f))
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(navigateToPlan = {})
}