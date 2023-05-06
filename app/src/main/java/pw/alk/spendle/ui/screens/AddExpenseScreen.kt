package pw.alk.spendle.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import pw.alk.spendle.ui.shared.components.NumPad

sealed class BottomSheetContentState {
    object ExpenseTypePicker : BottomSheetContentState()
    object PaymentMethodPicker : BottomSheetContentState()
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddExpenseScreen(navController: NavController) {

    // TODO: Get from sharedPref?
    val expenseTypes = listOf(
        "\uD83D\uDECD Grocery",
        "\uD83C\uDF55 Dining Out",
        "\uD83D\uDE97 Transportation",
        "\uD83C\uDFC1 Rent",
        "\uD83D\uDCF1 Utilities",
        "\uD83D\uDCBB Entertainment",
        "\uD83D\uDCE6 Healthcare",
        "\uD83D\uDCBC Education",
        "\uD83C\uDFE2 Accommodation",
        "\uD83C\uDFE0 Travel",
    )
    val paymentMethods = listOf(
        "\uD83D\uDCB3 Credit Card",
        "\uD83D\uDCB5 Debit Card",
        "\uD83C\uDFE6 Cash",
        "\uD83D\uDCB8 PayPal",
        "\uD83D\uDCB0 Bitcoin",
        "\uD83D\uDCB6 Bank Transfer",
        "\uD83D\uDCB4 Prepaid Card",
        "\uD83D\uDCB7 Mobile Payment",
        "\uD83D\uDCBB Gift Card",
        "\uD83D\uDCB9 Money Order"
    )

    val bottomSheetContentState = remember {
        mutableStateOf<BottomSheetContentState>(BottomSheetContentState.PaymentMethodPicker)
    }

    val expense = remember { mutableStateOf("0") }
    val callback = { text: String ->
        // Handle value based on numpad input
        if (text == "⌫") {
            expense.value = if (expense.value.length != 1) expense.value.dropLast(1) else "0"
        } else {
            expense.value = if (expense.value == "0") text else expense.value.plus(text)
        }
    }

    var title: String? by remember {
        mutableStateOf(null)
    }
    var expenseType: String by remember {
        mutableStateOf(expenseTypes[0])
    }
    var paymentMethod: String by remember {
        mutableStateOf(paymentMethods[0])
    }


    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val hideBottomSheet: () -> Unit = { coroutineScope.launch { sheetState.hide()} }

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(16.dp),
        sheetState = sheetState,
        sheetContent = {
            when (bottomSheetContentState.value) {
                is BottomSheetContentState.PaymentMethodPicker -> {
                    Text(
                        "Payment Options",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 13.dp),
                        textAlign = TextAlign.Center
                    )
                    BottomSheetPickerContent(paymentMethods, onSelect = { selectedOption ->
                        paymentMethod = selectedOption
                    }, hideBottomSheet)
                }

                is BottomSheetContentState.ExpenseTypePicker -> {
                    Text(
                        "Expense Types",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 13.dp),
                        textAlign = TextAlign.Center
                    )
                    BottomSheetPickerContent(expenseTypes, onSelect = { selectedOption ->
                        expenseType = selectedOption
                    }, hideBottomSheet)
                }
            }
        },
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                "Add Expense",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(70.dp))
            Box(
                modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                BasicTextField(
                    value = title ?: "Title (Optional)",
                    modifier = Modifier.wrapContentSize(),
                    onValueChange = { title = it },
                    singleLine = true,
                    textStyle = TextStyle(color = Color.White),
                    cursorBrush = SolidColor(Color.Gray)
                )
            }

            Text(
                expense.value,
                fontSize = 100.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f)) // push the numpad below to end, vertically
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ClickableText(text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.White, fontSize = 16.sp)) {
                        append(paymentMethod)
                    }
                }, onClick = {
                    bottomSheetContentState.value = BottomSheetContentState.PaymentMethodPicker
                    coroutineScope.launch {
                        if (sheetState.isVisible) sheetState.hide()
                        else sheetState.show()
                    }
                })

                Text("->")

                ClickableText(text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.White, fontSize = 16.sp)) {
                        append(expenseType)
                    }
                }, onClick = {
                    bottomSheetContentState.value = BottomSheetContentState.ExpenseTypePicker
                    coroutineScope.launch {
                        if (sheetState.isVisible) sheetState.hide()
                        else sheetState.show()
                    }
                })

                OutlinedButton(
                    onClick = { /*TODO*/ },
                    border = BorderStroke(1.dp, MaterialTheme.colors.primary)
                ) {
                    Text("Save")
                }
            }

            NumPad(modifier = Modifier.padding(30.dp), callback = callback)
        }
    }
}

@Composable
fun BottomSheetPickerContent(itemList: List<String>, onSelect: (String) -> Unit, hideSheet: () -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(28.dp)

    ) {
        itemList.chunked(3).forEach { rowItems ->
            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    rowItems.forEach { item ->
                        val spaceIndex = item.indexOf(" ")
                        val emoji = item.substring(0, spaceIndex)
                        val text = item.substring(spaceIndex + 1)
                        Column(
                            modifier = Modifier
                                .weight(33.33f)
                                .clickable {
                                    onSelect(item)
                                    hideSheet()
                                },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(emoji, fontSize = 28.sp)
                            Text(
                                text,
                                fontSize = 14.sp,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }
    }
}