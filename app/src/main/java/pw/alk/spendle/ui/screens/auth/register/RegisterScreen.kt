package pw.alk.spendle.ui.screens.auth.register

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pw.alk.spendle.ui.nav.AuthScreen
import pw.alk.spendle.ui.nav.Graph
import pw.alk.spendle.ui.shared.OnEvent
import pw.alk.spendle.ui.shared.components.BackButton
import pw.alk.spendle.ui.shared.components.EmailInput
import pw.alk.spendle.ui.shared.components.PasswordInput

@Composable
fun RegisterScreen(
    navController: NavController, viewModel: RegisterViewModel
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }

    // TODO: Show circular progress indicator when request is being made
    val isLoading by viewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 40.dp, horizontal = 25.dp),
    ) {
        BackButton(onClick = {
            navController.popBackStack(
                route = AuthScreen.Home.route, inclusive = false
            )
        })
        Spacer(modifier = Modifier.height(50.dp))
        Column(
            modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text("Create an Account", style = MaterialTheme.typography.h4)

            EmailInput(modifier = Modifier.fillMaxWidth(),
                email = email,
                onEmailChange = { email = it })

            PasswordInput(modifier = Modifier.fillMaxWidth(),
                password = password,
                onPasswordChange = { password = it },
                kbActionOnDone = { focusManager.clearFocus() })

            Button(
                onClick = {
                    focusManager.clearFocus()
                    viewModel.registerUser(email.text, password.text)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            ) {
                Text("Register", style = MaterialTheme.typography.button)
            }
        }

        OnEvent(event = viewModel.event) {
            when (it) {
                RegisterEvent.RegisterSuccess ->
                    navController.navigate(Graph.MAIN) {
                        launchSingleTop = true
                    }
                is RegisterEvent.RegisterFailure ->
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}