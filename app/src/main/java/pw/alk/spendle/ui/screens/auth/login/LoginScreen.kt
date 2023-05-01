package pw.alk.spendle.ui.screens.auth.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pw.alk.spendle.ui.nav.AuthScreen
import pw.alk.spendle.ui.nav.Graph
import pw.alk.spendle.ui.shared.OnEvent
import pw.alk.spendle.ui.shared.components.BackButton
import pw.alk.spendle.ui.shared.components.EmailInput
import pw.alk.spendle.ui.shared.components.PasswordInput

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                vertical = 40.dp, horizontal = 25.dp
            )
    ) {
        BackButton(onClick = {
            navController.popBackStack(
                route = AuthScreen.Home.route,
                inclusive = false
            )
        })
        Spacer(modifier = Modifier.height(50.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Login", style = MaterialTheme.typography.h4)

            Spacer(modifier = Modifier.height(20.dp))

            EmailInput(
                modifier = Modifier.fillMaxWidth(),
                email = email,
                onEmailChange = { email = it }
            )
            Spacer(modifier = Modifier.height(20.dp))

            PasswordInput(
                modifier = Modifier.fillMaxWidth(),
                password = password,
                onPasswordChange = { password = it },
                kbActionOnDone = { focusManager.clearFocus() }
            )

            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                ClickableText(text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = MaterialTheme.colors.onSecondary)) {
                        append("Forgot password?")
                    }
                }, onClick = {
                    Toast.makeText(context, "This feature is not yet ready!", Toast.LENGTH_SHORT)
                        .show()
                })
            }

            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    focusManager.clearFocus()
                    viewModel.loginUser(email.text, password.text)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            ) {
                Text("Login", style = MaterialTheme.typography.button)
            }
        }
    }

    OnEvent(event = viewModel.event) {
        when (it) {
            LoginEvent.LoginSuccess ->
                navController.navigate(Graph.MAIN) {
                    launchSingleTop = true
                }
            is LoginEvent.LoginFailure ->
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }
    }
}
