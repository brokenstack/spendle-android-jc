package pw.alk.spendle.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pw.alk.spendle.ui.components.BackButton
import pw.alk.spendle.ui.components.EmailInput
import pw.alk.spendle.ui.components.PasswordInput
import pw.alk.spendle.ui.utils.Screen

@Composable
fun AuthRegister(navController: NavController) {
    val focusManager = LocalFocusManager.current
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 40.dp, horizontal = 25.dp),
    ) {
        BackButton(onClick = {
            navController.popBackStack(
                route = Screen.AuthHome.route,
                inclusive = false
            )
        })
        Spacer(modifier = Modifier.height(50.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Text("Create an Account", style = MaterialTheme.typography.h4)

            EmailInput(
                modifier = Modifier.fillMaxWidth(),
                email = email,
                onEmailChange = { email = it })

            PasswordInput(
                modifier = Modifier.fillMaxWidth(),
                password = password,
                onPasswordChange = { password = it },
                kbActionOnDone = { focusManager.clearFocus() }
            )

            Button(
                onClick = {
                    // TODO: Make request to server
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            ) {
                Text("Register", style = MaterialTheme.typography.button)
            }
        }
    }
}