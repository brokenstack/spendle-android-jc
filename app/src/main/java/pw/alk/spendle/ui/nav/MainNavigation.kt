package pw.alk.spendle.ui.nav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pw.alk.spendle.R
import pw.alk.spendle.ui.screens.AddExpenseScreen
import pw.alk.spendle.ui.screens.main.home.HomeScreen

sealed class BottomNavScreen(
    val route: String,
    @StringRes val resourceId: Int,
    @DrawableRes val drawableId: Int
) {
    object Home : BottomNavScreen("home", R.string.home, R.drawable.house_line)
    object Stats : BottomNavScreen("stats", R.string.stats, R.drawable.chart_line)
    object Settings : BottomNavScreen("settings", R.string.settings, R.drawable.gear_line)
}

sealed class Screen(
    val route: String
) {
    object AddExpense : Screen("add_expense")
}

@Composable
fun MainNavigationGraph(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = BottomNavScreen.Home.route,
        androidx.compose.ui.Modifier.padding(innerPadding)
    ) {
        composable(BottomNavScreen.Home.route) {
            HomeScreen(navController)
        }
        composable(BottomNavScreen.Stats.route) {
            Text("Stats")
        }
        composable(BottomNavScreen.Settings.route) {
            Text("Settings")
        }

        composable(Screen.AddExpense.route) {
            AddExpenseScreen(navController)
        }
    }
}