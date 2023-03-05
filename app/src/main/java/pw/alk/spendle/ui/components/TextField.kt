package pw.alk.spendle.ui.components

import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun PasswordInput(
    modifier: Modifier,
    password: TextFieldValue,
    onPasswordChange: (TextFieldValue) -> Unit,
    kbActionOnDone: KeyboardActionScope.() -> Unit
) {
    OutlinedTextField(
        modifier = modifier.clearFocusOnKeyboardDismiss(),
        value = password,
        onValueChange = onPasswordChange,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
        ),
        visualTransformation = PasswordVisualTransformation(),
        label = { Text(text = "Password") },
        keyboardActions = KeyboardActions(
            onDone = kbActionOnDone
        )
    )
}

@Composable
fun EmailInput(
    modifier: Modifier,
    email: TextFieldValue,
    onEmailChange: (TextFieldValue) -> Unit,
) {
    OutlinedTextField(value = email,
        onValueChange = onEmailChange,
        modifier = modifier.clearFocusOnKeyboardDismiss(),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
        ),
        maxLines = 1,
        label = { Text(text = "Email") })
}

// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
/*
        Fix for clearing focus after keyboard is dismissed
        By: https://stackoverflow.com/a/70506309/15980246
        Issue: https://issuetracker.google.com/issues/192433071?pli=1
*/
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
fun View.isKeyboardOpen(): Boolean {
    val rect = Rect()
    getWindowVisibleDisplayFrame(rect)
    val screenHeight = rootView.height
    val keypadHeight = screenHeight - rect.bottom
    return keypadHeight > screenHeight * 0.15
}

@Composable
fun rememberIsKeyboardOpen(): State<Boolean> {
    val view = LocalView.current

    return produceState(initialValue = view.isKeyboardOpen()) {
        val viewTreeObserver = view.viewTreeObserver
        val listener = ViewTreeObserver.OnGlobalLayoutListener { value = view.isKeyboardOpen() }
        viewTreeObserver.addOnGlobalLayoutListener(listener)

        awaitDispose { viewTreeObserver.removeOnGlobalLayoutListener(listener)  }
    }
}

fun Modifier.clearFocusOnKeyboardDismiss(): Modifier = composed {

    var isFocused by remember { mutableStateOf(false) }
    var keyboardAppearedSinceLastFocused by remember { mutableStateOf(false) }

    if (isFocused) {
        val isKeyboardOpen by rememberIsKeyboardOpen()

        val focusManager = LocalFocusManager.current
        LaunchedEffect(isKeyboardOpen) {
            if (isKeyboardOpen) {
                keyboardAppearedSinceLastFocused = true
            } else if (keyboardAppearedSinceLastFocused) {
                focusManager.clearFocus()
            }
        }
    }
    onFocusEvent {
        if (isFocused != it.isFocused) {
            isFocused = it.isFocused
            if (isFocused) {
                keyboardAppearedSinceLastFocused = false
            }
        }
    }
}