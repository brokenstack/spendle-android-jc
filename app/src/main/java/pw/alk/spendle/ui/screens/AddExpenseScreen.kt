package pw.alk.spendle.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pw.alk.spendle.ui.shared.components.NumPad

@Composable
fun AddExpenseScreen(navController: NavController) {
    val expense = remember { mutableStateOf("0") }
    val callback = { text: String ->
        // Handle value based on numpad input
        if (text == "⌫") {
            expense.value = if (expense.value.length != 1) expense.value.dropLast(1) else "0"
        } else {
            expense.value = if (expense.value == "0") text else expense.value.plus(text)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            "Add Expense",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 40.sp,
            fontWeight = FontWeight.Medium
        )

        Text(
            expense.value,
            fontSize = 60.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f)) // push the column below to end vertically
        NumPad(modifier = Modifier.padding(30.dp), callback = callback)
    }
}