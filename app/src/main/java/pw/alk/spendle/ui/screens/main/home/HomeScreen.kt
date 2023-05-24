package pw.alk.spendle.ui.screens.main.home

import android.widget.Toast
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
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
import androidx.navigation.NavController
import pw.alk.spendle.ui.nav.Screen

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val homeScreenState = viewModel.weeklyTotalState.collectAsState().value
    val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(route = Screen.AddExpense.route) {
                        launchSingleTop = true
                    }
                },
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
                            fontWeight = FontWeight.Medium,
                            maxLines = 1,
                            modifier = Modifier.horizontalScroll(rememberScrollState(0))
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