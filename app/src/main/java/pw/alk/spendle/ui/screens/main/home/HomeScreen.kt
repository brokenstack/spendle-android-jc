package pw.alk.spendle.ui.screens.main.home

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeViewModel>()
    val homeScreenState = viewModel.weeklyTotalState.collectAsState().value
    val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                backgroundColor = Color.White,
                contentColor = Color.Black
            ) {
                Icon(Icons.Filled.Add, "")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("This week's spending")
                when (homeScreenState) {
                    is WeeklyTotalState.Success -> {
                        Text(
                            "₹" + homeScreenState.weeklyTotal,
                            fontSize = 80.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    is WeeklyTotalState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(70.dp)
                                .padding(15.dp)
                        )
                    }
                    is WeeklyTotalState.Failure -> {
                        Toast.makeText(context, homeScreenState.message, Toast.LENGTH_LONG).show()
                        Text(
                            "₹" + "0.00",
                            fontSize = 80.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}