package pw.alk.spendle.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pw.alk.spendle.R
import pw.alk.spendle.ui.theme.White80

val ButtonModifier = Modifier
    .height(60.dp)
    .fillMaxWidth()

@Composable
fun AuthHome() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 40.dp, horizontal = 15.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(vertical = 30.dp),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Image(
                painter = painterResource(id = R.drawable.lg_spendle),
                contentDescription = "Spendle Logo",
                modifier = Modifier.size(120.dp)
            )
            Text("Spendle", style = MaterialTheme.typography.h2)
            Text(
                "Track your expenses effortlessly",
                style = MaterialTheme.typography.h6
            )
        }
        Spacer(modifier = Modifier.height(70.dp))

        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary
            ),
            modifier = ButtonModifier,
        ) {
            Icon(
                Icons.Outlined.Email,
                contentDescription = "Email",
                tint = White80
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                text = "Register with Email ID",
                style = MaterialTheme.typography.button
            )
        }

        Spacer(modifier = Modifier.height(13.dp))
        OutlinedButton(onClick = { /*TODO*/ }, modifier = ButtonModifier) {
            Image(
                painter = painterResource(id = R.drawable.ic_google),
                modifier = Modifier.size(26.dp),
                contentDescription = "Google Icon"
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                "Sign Up with Google",
                style = MaterialTheme.typography.button,
            )
        }

        Spacer(modifier = Modifier.height(80.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Already have an account? ", fontSize = 17.sp)
            ClickableText(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = White80,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("Sign In")
                }
            }, onClick = { /*TODO*/ })
        }
    }
}