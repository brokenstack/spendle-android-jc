package pw.alk.spendle.ui.screens.auth

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pw.alk.spendle.R
import pw.alk.spendle.ui.components.EmailInput
import pw.alk.spendle.ui.components.PasswordInput

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
        IconButton(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back button",
                    tint = MaterialTheme.colors.onSecondary,
                    modifier = Modifier.size(26.dp)
                )
                Text("Back", fontSize = 18.sp)
            }
        }
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
                    focusManager.clearFocus()
                    Log.e("HI", email.text + password.text)
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